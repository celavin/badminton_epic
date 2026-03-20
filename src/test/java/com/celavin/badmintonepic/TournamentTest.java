package com.celavin.badmintonepic;

import com.celavin.badmintonepic.engine.simulator.MatchEngine;
import com.celavin.badmintonepic.engine.tournament.Tournament;
import com.celavin.badmintonepic.engine.tournament.format.KnockOutFormat;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.GameScore;
import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.dto.RawMatchResult;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.service.MatchSettlementService;
import com.celavin.badmintonepic.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TournamentTest {
    @Autowired
    PlayerService  playerService;
    @Autowired
    MatchEngine matchEngine;
    @Autowired
    MatchSettlementService matchSettlementService;
    @Test
    void test(){
        playerService.initWorld(16);
        List<Player> list = playerService.list();
        Tournament t = new Tournament("london", TournamentLevel.RANKED,new KnockOutFormat(),list);
        t.simulateAll(matchEngine,matchSettlementService);
        MatchNode finalNode = t.getFormat().getFinalNode();
        List<MatchNode> allMatches = t.getFormat().getAllMatches();
        for (MatchNode match : allMatches) {
            match.show();
        }
        // todo 渲染对阵图

    }



}
