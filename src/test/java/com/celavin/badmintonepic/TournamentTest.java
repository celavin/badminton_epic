package com.celavin.badmintonepic;

import ch.qos.logback.classic.spi.ConfiguratorRank;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.celavin.badmintonepic.context.GameTimeContext;
import com.celavin.badmintonepic.engine.simulator.MatchEngine;
import com.celavin.badmintonepic.engine.tournament.Tournament;
import com.celavin.badmintonepic.engine.tournament.format.KnockOutFormat;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.ChampionStatsDTO;
import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.entity.GameState;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.model.entity.TournamentEntity;
import com.celavin.badmintonepic.service.*;
import com.celavin.badmintonepic.util.TournamentNameGenerator;
import com.celavin.badmintonepic.util.TournamentPrinter;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class TournamentTest {
    @Autowired
    PlayerService  playerService;
    @Autowired
    MatchEngine matchEngine;
    @Autowired
    MatchSettlementService matchSettlementService;
    @Autowired
    TournamentService tournamentService;
    @Autowired
    TournamentManageService tournamentManageService;
    @Autowired
    GameStateService gameStateService;
    @Test
    //单场细节
    void singleDetailedTest(){

        List<Player> list = playerService.list();

        Tournament t = new Tournament("Beijing", TournamentLevel.RANKED,new KnockOutFormat(),list);
        tournamentManageService.simulateAll(t);
        MatchNode finalNode = t.getFormat().getFinalNode();
        List<MatchNode> allMatches = t.getFormat().getAllMatches();
        Collections.reverse(allMatches);
        TournamentPrinter.show(finalNode);

    }
    @Test
    //多场看实力
    void manyTimestest(){
        HashMap<String,Integer> championCounts = new HashMap<>();//
        /*int limitCount = 8;

        QueryWrapper<Player> queryWrapper = new QueryWrapper<>();
        // PostgreSQL 专属的随机排序并限制数量
        queryWrapper.last("ORDER BY RANDOM() LIMIT " + limitCount);

        List<Player> list = playerService.list(queryWrapper);*/

        List<Player> list = playerService.list();
        for (int i = 0; i < 10000; i++) {
            Tournament t = new Tournament("Beijing", TournamentLevel.RANKED,new KnockOutFormat(),list);
           tournamentManageService.simulateAll(t);
            championCounts.put(t.getChampion().getName(),championCounts.getOrDefault(t.getChampion().getName(),0)+1);
        }
        HashMap<String,Integer> sortedMap =
                championCounts.entrySet()
                        .stream()
                                .sorted(Map.Entry.<String,Integer>comparingByValue().reversed())
                                        .collect(Collectors.toMap(
                                                Map.Entry::getKey,
                                                Map.Entry::getValue,
                                                (e1,e2)->e1, LinkedHashMap::new
                                                ));


        System.out.println(sortedMap);
    }

    @Test
    void simulateYear(){
        gameStateService.loadGame();
        List<Player> players = playerService.list();
        tournamentManageService.simulateWholeYear(players);

    }
    @Test
    //查冠军数
    void watchchampion(){
        List<ChampionStatsDTO> championTitleStats = tournamentService.getChampionTitleStats();
        for (ChampionStatsDTO championTitleStat : championTitleStats) {
            System.out.println(championTitleStat);
        }
    }
    @Test
    void watchAllTournaments(){
        List<TournamentEntity> list = tournamentService.list();
        for (TournamentEntity tournamentEntity : list) {
            System.out.println(tournamentEntity);
        }
    }
    @Test
    void watchNewTournaments(){
        List<TournamentEntity> list = tournamentService.getLast12Tournaments();
        for (TournamentEntity tournamentEntity : list) {
            System.out.println(tournamentEntity);
        }
    }

    @Test
    //从表里取出finalnode
    void finalnodetest(){
        List<TournamentEntity> list = tournamentService.list();
        TournamentEntity t = list.get(0);
        System.out.println(t.getName()+" "+t.getLevel());
        MatchNode finalNode = t.getFinalNode();
        TournamentPrinter.show(finalNode);

    }

    @Test
    void watchRank(){
        List<Player> rankedPlayers = playerService.getRankedPlayers();
        for (Player rankedPlayer : rankedPlayers) {
            System.out.println(rankedPlayer.getRank()+":"+rankedPlayer.getName()+" "+ rankedPlayer.getPoints());
        }
    }

    @Test
    void singleTournament(){
        tournamentManageService.runAndSaveTournament("测试公开赛",TournamentLevel.ELITE,new KnockOutFormat(),playerService.list());
        watchRank();
    }



}
