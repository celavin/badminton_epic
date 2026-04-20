package com.celavin.badmintonepic.service.impl;

import com.celavin.badmintonepic.config.CalendarTemplateConfig;
import com.celavin.badmintonepic.context.GameTimeContext;
import com.celavin.badmintonepic.engine.simulator.MatchEngine;
import com.celavin.badmintonepic.engine.tournament.Tournament;
import com.celavin.badmintonepic.engine.tournament.format.KnockOutFormat;
import com.celavin.badmintonepic.engine.tournament.format.TournamentFormat;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.enums.TournamentStatus;
import com.celavin.badmintonepic.factory.TournamentFactory;
import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.dto.MatchResult;
import com.celavin.badmintonepic.model.dto.RawMatchResult;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.model.entity.TournamentEntity;
import com.celavin.badmintonepic.service.GameStateService;
import com.celavin.badmintonepic.service.MatchSettlementService;
import com.celavin.badmintonepic.service.TournamentManageService;
import com.celavin.badmintonepic.service.TournamentService;
import com.celavin.badmintonepic.util.TournamentNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TournamentManageServiceImpl implements TournamentManageService {
    @Autowired
    MatchEngine matchEngine;
    @Autowired
    MatchSettlementService matchSettlementService;
    @Autowired
    TournamentService tournamentService;
    @Autowired
    GameStateService gameStateService;
    @Autowired
    TournamentFactory tournamentFactory;
    @Autowired
    CalendarTemplateConfig calendarTemplateConfig;

    public void simulateNextStep(Tournament tournament) {
        if (tournament.getFormat().isCompleted()) return;
        List<MatchNode> playableMatches = tournament.getFormat().getPlayableMatches();
        for (MatchNode matchNode : playableMatches) {
            RawMatchResult result = matchEngine.simulate(matchNode, tournament.getBestOf());

            tournament.getFormat().submitMatchResult(matchNode, result);
            //包装成macthresult方便结算
            matchSettlementService.settleSingleMatch(new MatchResult(matchNode,tournament.getTournamentName(),tournament.getLevel()));
        }
        // 如果打完这轮比赛就结束了，执行归档
        if (tournament.getFormat().isCompleted()) {
            tournament.archive();
        }
    }

    /**
     * 一键模拟整届赛事
     * 测试用
     */
    public void simulateAll(Tournament tournament) {
        while (!(tournament.getStatus()==TournamentStatus.COMPLETED)) {
            simulateNextStep(tournament);
        }
    }

    /**
     * 原有的 runAndSaveTournament 方法稍微改动一下
     */
    public TournamentEntity runAndSaveTournament(String name, TournamentLevel level, TournamentFormat format, List<Player> players){
        Tournament tournament = new Tournament(name, level, format, players);
        TournamentEntity ongoing = TournamentEntity.snapshotFromRunningOrCompleted(tournament);
        tournamentService.save(ongoing);

        this.simulateAll(tournament);

        TournamentEntity completed = TournamentEntity.snapshotFromRunningOrCompleted(tournament);
        completed.setId(ongoing.getId());
        tournamentService.updateById(completed);
        return completed;
    }

    /**
     * 执行一个赛事但不保存,返回实体类
     * @param name
     * @param level
     * @param format
     * @param players
     * @return
     */
    public TournamentEntity runTournament(String name, TournamentLevel level, TournamentFormat format, List<Player> players){
        Tournament tournament = new Tournament(name, level, format, players);
        simulateAll(tournament);

        return TournamentEntity.snapshotFromRunningOrCompleted(tournament);
    }
    /**
     * 模拟一整年,测试用
     * */
    public void simulateWholeYear(List<Player> players){

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                runAndSaveTournament(TournamentNameGenerator.generateRandomName(),
                        TournamentLevel.CHALLENGE, new KnockOutFormat(), players);
                gameStateService.advanceOneMonth();
            }
            runAndSaveTournament(TournamentNameGenerator.generateRandomName(),
                    TournamentLevel.ELITE, new KnockOutFormat(), players);
            gameStateService.advanceOneMonth();
            runAndSaveTournament(TournamentNameGenerator.generateRandomName(),
                    TournamentLevel.MAJOR, new KnockOutFormat(), players);
            gameStateService.advanceOneMonth();
        }


    }

    @Override
    public void generateYearlyCalendar() {
        int year = GameTimeContext.getCurrentYear();
        long yearRows = tournamentService.lambdaQuery()
                .eq(TournamentEntity::getYear, year)
                .count();
        if (yearRows > 0) {
            return;
        }
        for (int month = 1; month <= 12; month++) {
            List<TournamentLevel> levels = calendarTemplateConfig.getLevelsForMonth(month);
            if (levels == null || levels.isEmpty()) {
                continue;
            }
            for (TournamentLevel level : levels) {
                long existing = tournamentService.lambdaQuery()
                        .eq(TournamentEntity::getYear, year)
                        .eq(TournamentEntity::getMonth, month)
                        .eq(TournamentEntity::getLevel, level)
                        .eq(TournamentEntity::getStatus, TournamentStatus.SCHEDULED)
                        .count();
                if (existing > 0) {
                    continue;
                }
                TournamentEntity row = new TournamentEntity();
                row.setName(TournamentNameGenerator.generateRandomName());
                row.setLevel(level);
                row.setYear(year);
                row.setMonth(month);
                row.setStatus(TournamentStatus.SCHEDULED);
                tournamentService.save(row);
            }
        }

    }

    @Override
    public Tournament rehydrateKnockOutFromEntity(TournamentEntity entity) {
        return tournamentFactory.rehydrateKnockOutFromEntity(entity);
    }

    @Override
    public void runScheduledTournamentToCompletion(TournamentEntity scheduled, List<Player> players) {
        if (scheduled == null || players == null || players.size() < 2) {
            return;
        }
        Tournament tournament = new Tournament(scheduled.getName(), scheduled.getLevel(), new KnockOutFormat(), players);
        TournamentEntity ongoing = TournamentEntity.snapshotFromRunningOrCompleted(tournament);
        ongoing.setId(scheduled.getId());
        tournamentService.updateById(ongoing);

        simulateAll(tournament);

        TournamentEntity done = TournamentEntity.snapshotFromRunningOrCompleted(tournament);
        done.setId(scheduled.getId());
        tournamentService.updateById(done);
    }


}
