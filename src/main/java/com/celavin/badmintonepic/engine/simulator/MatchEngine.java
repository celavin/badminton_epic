package com.celavin.badmintonepic.engine.simulator;

import com.celavin.badmintonepic.model.dto.Match;
import com.celavin.badmintonepic.model.dto.RawMatchResult;
import com.celavin.badmintonepic.model.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//只负责系列赛,传入比赛选手和最多场次,其他不管
@Service
public class MatchEngine {
    @Autowired
    private GameEngine gameEngine;

    public RawMatchResult simulate(Player p1, Player p2, int bestOf) {
        Match match = new Match(p1, p2, bestOf, gameEngine);
        return match.play();
    }
}
