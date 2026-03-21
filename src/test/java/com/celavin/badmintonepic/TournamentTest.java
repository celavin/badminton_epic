package com.celavin.badmintonepic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.celavin.badmintonepic.engine.simulator.MatchEngine;
import com.celavin.badmintonepic.engine.tournament.Tournament;
import com.celavin.badmintonepic.engine.tournament.format.KnockOutFormat;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.model.entity.TournamentEntity;
import com.celavin.badmintonepic.service.MatchSettlementService;
import com.celavin.badmintonepic.service.PlayerService;
import com.celavin.badmintonepic.service.TournamentManageService;
import com.celavin.badmintonepic.service.TournamentService;
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
    @Test
    //单场细节
    void singleDetailedTest(){
       /* int limitCount = 8;

        QueryWrapper<Player> queryWrapper = new QueryWrapper<>();
        // PostgreSQL 专属的随机排序并限制数量
        queryWrapper.last("ORDER BY RANDOM() LIMIT " + limitCount);

        List<Player> list = playerService.list(queryWrapper);*/

        List<Player> list = playerService.list();

        Tournament t = new Tournament("Beijing", TournamentLevel.RANKED,new KnockOutFormat(),list);
        t.simulateAll(matchEngine,matchSettlementService);
        MatchNode finalNode = t.getFormat().getFinalNode();
        List<MatchNode> allMatches = t.getFormat().getAllMatches();
        Collections.reverse(allMatches);
        /*for (MatchNode match : allMatches) {
            match.show();
        }*/
        TournamentPrinter.show(finalNode);

    }
    @Test
    //多场看实力
    void manyTimestest(){
        HashMap<String,Integer> championCounts = new HashMap<>();//todo 后续可优化,储存更多成绩
        /*int limitCount = 8;

        QueryWrapper<Player> queryWrapper = new QueryWrapper<>();
        // PostgreSQL 专属的随机排序并限制数量
        queryWrapper.last("ORDER BY RANDOM() LIMIT " + limitCount);

        List<Player> list = playerService.list(queryWrapper);*/

        List<Player> list = playerService.list();
        for (int i = 0; i < 10000; i++) {
            Tournament t = new Tournament("Beijing", TournamentLevel.RANKED,new KnockOutFormat(),list);
            t.simulateAll(matchEngine,matchSettlementService);
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
    void showPlayers(){
        List<Player> list = playerService.list();

        for (Player player : list) {
            System.out.println(player);
        }
    }
    @Test
    //用service执行赛事并保存
    void test(){
        List<Player> players = playerService.list();
        for (int i = 0; i < 10; i++) {
            TournamentEntity tournamentEntity =
                    tournamentManageService.runAndSaveTournament(TournamentNameGenerator.generateRandomName(), TournamentLevel.RANKED, new KnockOutFormat(), players);

        }
        //MatchNode finalNode = tournamentEntity.getFinalNode();
        //TournamentPrinter.show(finalNode);


    }



}
