package com.celavin.badmintonepic.engine.tournament;

import com.celavin.badmintonepic.engine.simulator.MatchEngine;
import com.celavin.badmintonepic.engine.tournament.format.TournamentFormat;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.dto.MatchResult;
import com.celavin.badmintonepic.model.dto.RawMatchResult;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.service.MatchSettlementService;

import java.util.ArrayList;
import java.util.List;

public class Tournament {
    private String tournamentName;
    private TournamentLevel tournamentLevel ;
    private TournamentFormat tournamentFormat;
    private int playerNums;
    private List<Player> playerList;
    private int bestOf;
    private boolean isFinished;
    private Player champion;


    //初始化
    public void TournamentFormat(String name, TournamentLevel level, TournamentFormat format, List<Player> playerList){
        tournamentName = name;
        tournamentLevel = level;
        tournamentFormat = format;
        this.playerList = playerList;
        tournamentFormat.initBracket(playerList);

    }

    //核心方法,模拟一轮,至于一轮是包含什么,在format类里的方法规定
    public void simulateNextStep (MatchEngine matchEngine, MatchSettlementService matchSettlementService){
        if(tournamentFormat.hasWinner()){
            return;
        }
        //获取今日赛程
        List<MatchNode> pendingMatches = tournamentFormat.getPendingMatches();

        for (MatchNode node : pendingMatches) {
            RawMatchResult result = matchEngine.simulate(node.getP1(),node.getP2(),this.bestOf);
            node.setMatchResult(result);


            /*// 3. 包装结果，调用第三层进行世界影响结算（积分、士气等）
            MatchResult = new MatchResult(
                    result.getWinner(), result.getLoser(), result.getScores(),
                    this.tournamentLevel, this.tournamentName, tournamentFormat.getCurrentRoundName(), this.bestOf
            );
            settlementService.processMatchSettlement(matchResult);*/
        }

        tournamentFormat.processResultsAndAdvance(pendingMatches);

        if (tournamentFormat.hasWinner()) {
            this.isFinished = true;
            this.champion = tournamentFormat.getChampion();
        }




    }




}
