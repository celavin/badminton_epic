package com.celavin.badmintonepic.service;

import com.celavin.badmintonepic.context.GameTimeContext;
import com.celavin.badmintonepic.engine.simulator.MatchEngine;
import com.celavin.badmintonepic.engine.tournament.Tournament;
import com.celavin.badmintonepic.engine.tournament.format.KnockOutFormat;
import com.celavin.badmintonepic.engine.tournament.format.TournamentFormat;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.dto.MatchResult;
import com.celavin.badmintonepic.model.dto.RawMatchResult;
import com.celavin.badmintonepic.model.dto.TournamentScheduleEntry;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.model.entity.TournamentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TournamentManageService {
    @Autowired
    MatchEngine matchEngine;
    @Autowired
    MatchSettlementService matchSettlementService;
    @Autowired
    TournamentService tournamentService;
    @Autowired
    GameStateService gameStateService;
    @Autowired
    TournamentCalendarService tournamentCalendarService;
    @Autowired
    TournamentRegistrationService tournamentRegistrationService;

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
     */
    public void simulateAll(Tournament tournament) {
        while (!tournament.isFinished()) {
            simulateNextStep(tournament);
        }
    }

    /**
     * 原有的 runAndSaveTournament 方法稍微改动一下
     */
    public TournamentEntity runAndSaveTournament(String name, TournamentLevel level, TournamentFormat format, List<Player> players){
        Tournament tournament = new Tournament(name, level, format, players);
        // 调度权交给自己
        this.simulateAll(tournament);

        TournamentEntity tournamentEntity = new TournamentEntity(tournament);
        tournamentService.save(tournamentEntity);
        return tournamentEntity;
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

        return new TournamentEntity(tournament);
    }
    public void simulateWholeYear(List<Player> players){
        gameStateService.loadGame();
        int currentYear = GameTimeContext.getCurrentYear();
        int currentMonth = GameTimeContext.getCurrentMonth();

        for (int offset = 0; offset < 12; offset++) {
            int absoluteMonth = currentMonth + offset;
            int scheduleYear = currentYear + ((absoluteMonth - 1) / 12);
            int scheduleMonth = ((absoluteMonth - 1) % 12) + 1;
            List<TournamentScheduleEntry> monthlySchedule = tournamentCalendarService.getMonthlySchedule(scheduleYear, scheduleMonth);

            for (TournamentScheduleEntry scheduleEntry : monthlySchedule) {
            List<Player> participants = tournamentRegistrationService.selectParticipants(scheduleEntry, players);
            if (participants.size() >= 2) {
                runAndSaveTournament(
                        scheduleEntry.getTournamentName(),
                        scheduleEntry.getLevel(),
                        new KnockOutFormat(),
                        participants);
                }
            }
            gameStateService.advanceOneMonth();
        }
    }


}
