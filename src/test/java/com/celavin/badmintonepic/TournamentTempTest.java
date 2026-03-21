package com.celavin.badmintonepic;

import com.celavin.badmintonepic.engine.simulator.MatchEngine;
import com.celavin.badmintonepic.engine.tournament.Tournament;
import com.celavin.badmintonepic.engine.tournament.format.KnockOutFormat;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.service.MatchSettlementService;
import com.celavin.badmintonepic.service.PlayerService;
import com.celavin.badmintonepic.util.TournamentPrinter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;
//不从数据库取数据,临时生成
@SpringBootTest
public class TournamentTempTest {
    @Autowired
    PlayerService  playerService;
    @Autowired
    MatchEngine matchEngine;
    @Autowired
    MatchSettlementService matchSettlementService;
    @Test
        //单场细节
    void singleDetailedTest(){

        List<Player> list = playerService.generatePlayerTemp(8);
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
        HashMap<String,Integer> championCounts = new HashMap<>();
        List<Player> list = playerService.generatePlayerTemp(32);
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



}
