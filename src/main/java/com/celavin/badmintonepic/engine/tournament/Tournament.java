package com.celavin.badmintonepic.engine.tournament;

import com.celavin.badmintonepic.engine.simulator.MatchEngine;
import com.celavin.badmintonepic.engine.tournament.format.TournamentFormat;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.dto.RawMatchResult;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.service.MatchSettlementService;

import java.util.List;

public class Tournament {
    private String tournamentName;
    private TournamentLevel level;
    private TournamentFormat format;
    private int playerNums;
    private List<Player> playerList;
    private int bestOf;
    private boolean isFinished;
    private Player champion;


    //初始化
    public void TournamentFormat(String name, TournamentLevel level, TournamentFormat format, List<Player> playerList){
        tournamentName = name;
        this.level = level;
        this.format = format;
        this.playerList = playerList;
        this.format.initBracket(playerList);

    }

    //核心方法,模拟一轮,至于一轮是包含什么,在format类里的方法规定
    public void simulateNextStep (MatchEngine matchEngine, MatchSettlementService matchSettlementService){
        if(format.isCompleted()) return;
        List<MatchNode> playableMatches = format.getPlayableMatches();
        if (playableMatches.isEmpty()) return;

        for (MatchNode matchNode : playableMatches) {
            RawMatchResult result = matchEngine.simulate(matchNode, bestOf);
            //todo 这里找个地方变成matchresult类
            format.submitMatchResult(matchNode,result);
        }

        //完结后调整boolean并生成dto
        if(format.isCompleted()) {
            isFinished=true;
            //todo 赛事归档待完善

        }
    }

    public Player getChampion() {
        return champion;
    }
}
