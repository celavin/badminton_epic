package com.celavin.badmintonepic.service;

import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.dto.MatchResult;
import com.celavin.badmintonepic.model.entity.Player;
import org.springframework.stereotype.Service;

@Service
public class MatchSettlementService {
    //结算单场比赛
    public void settleSingleMatch(MatchResult matchResult) {
        //**todo 参数已经调好,待实现具体结算机制
        //需要一个公式
        //根据胜负,level,round来调整
        //1.积分,2.士气3.战绩(待player类完善)
        Player winner = matchResult.getWinner();
        Player loser = matchResult.getLoser();
        winner.setPoints(winner.getPoints() + calculateWinner(matchResult.getLevel(),matchResult.getRoundName()));
        loser.setPoints(loser.getPoints() + calculateLoser(matchResult.getLevel(),matchResult.getRoundName()));

    }

    private int calculateWinner(TournamentLevel level,String roundName) {
        return 0;
    }
    private int calculateLoser(TournamentLevel level,String roundName) {
        return 0;
    }


}
