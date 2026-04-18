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
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
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
        //需要检验一下当前时间是否为一月,否则会报错
        if(GameTimeContext.getCurrentMonth()!=1)return;
        int year = GameTimeContext.getCurrentYear();
        for (int i = 0; i < 12; i++) {
            //todo这里应该创建tournament还是直接entity?不知道啊
            //按理来说这个时候还没打比赛,只是预存进表里,要打的时候再拿出来但我害怕tournament和entity互相转换,entity序列化和反序列化过程中会出现问题
        }

    }


}
