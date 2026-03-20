package com.celavin.badmintonepic;

import com.celavin.badmintonepic.engine.simulator.MatchEngine;
import com.celavin.badmintonepic.engine.tournament.Tournament;
import com.celavin.badmintonepic.engine.tournament.format.KnockOutFormat;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.service.MatchSettlementService;
import com.celavin.badmintonepic.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
//批量测试,记录冠军数
//似乎压力不大,10000次也轻松拿捏
@SpringBootTest
public class ManyTournamentTest {
    @Autowired
    PlayerService playerService;
    @Autowired
    MatchEngine matchEngine;
    @Autowired
    MatchSettlementService matchSettlementService;
    @Test
    void test(){
        HashMap<String,Integer> championCounts = new HashMap<>();
        List<Player> list = playerService.list();
        for (int i = 0; i < 10000; i++) {
            Tournament t = new Tournament("Beijing", TournamentLevel.RANKED,new KnockOutFormat(),list);
            t.simulateAll(matchEngine,matchSettlementService);
            championCounts.put(t.getChampion().getName(),championCounts.getOrDefault(t.getChampion().getName(),0)+1);
        }

        System.out.println(championCounts);


    }
}
