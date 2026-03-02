package com.celavin.badmintonepic.engine.simulator;

import com.celavin.badmintonepic.model.dto.GameScore;
import com.celavin.badmintonepic.model.dto.MatchResult;
import com.celavin.badmintonepic.model.dto.RawMatchResult;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.service.MatchSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//只负责系列赛,传入比赛选手和最多场次,其他不管
@Service
public class MatchEngine {
    @Autowired
    private GameEngine gameEngine;
    @Autowired
    MatchSettlementService settlementService;

    public void simulate(Player p1, Player p2, int bestOf) {
        int wins1 = 0;
        int wins2 = 0;
        List<GameScore> gameResults = new ArrayList<>();
        //Player winner,loser;
        //int winsGames,losesGames;
        int targetPoint = bestOf/2+1;

        System.out.println("=== 比赛开始：" + p1.getName() + " VS " + p2.getName() + " ===");

        while (wins1 < targetPoint && wins2 < targetPoint) {
            GameScore score = gameEngine.simulateGame(p1, p2);
            gameResults.add(score);


            if(score.isP1Win()) wins1++;
            else wins2++;
        }

        System.out.println("最终大比分 [" + wins1 + ":" + wins2 + "]");
        System.out.println("小分明细：" + gameResults);
        System.out.println("======================================");

        //MatchResult result = new MatchResult(winner,loser,winsGames,losesGames,gameResults,1);

        //settlementService.processMatchSettlement(result);

        //RawMatchResult result = new RawMatchResult()
    }
}
