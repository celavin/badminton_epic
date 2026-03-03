package com.celavin.badmintonepic.engine.simulator;

import com.celavin.badmintonepic.model.dto.GameScore;
import com.celavin.badmintonepic.model.dto.MatchResult;
import com.celavin.badmintonepic.model.dto.RawMatchResult;
import com.celavin.badmintonepic.model.entity.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//只负责系列赛,传入比赛选手和最多场次,其他不管
@Service
public class MatchEngine {
    @Autowired
    private GameEngine gameEngine;
    public RawMatchResult simulate(Player p1, Player p2, int bestOf) {
        RawMatchResult result = new RawMatchResult(p1,p2,bestOf);
        System.out.println("=== 比赛开始：" + p1.getName()+ " VS " + p2.getName() + " ===");
        while (!result.isMatchOver()) {
            GameScore score = gameEngine.simulateGame(p1, p2);
            result.addGame(score);
        }
        result.finish();
        result.show();
        return result;
    }
}
