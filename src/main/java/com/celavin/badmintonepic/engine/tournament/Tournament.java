package com.celavin.badmintonepic.engine.tournament;

import com.celavin.badmintonepic.engine.simulator.MatchEngine;
import com.celavin.badmintonepic.engine.tournament.format.TournamentFormat;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.dto.RawMatchResult;
import com.celavin.badmintonepic.model.dto.TournamentVO;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.service.MatchSettlementService;

import java.util.List;

public class Tournament {

    private String tournamentName;
    private TournamentLevel level;
    private TournamentFormat format;
    private int playerNums;
    private List<Player> playerList;
    private int bestOf=5;//todo 默认值找个地方
    private boolean isFinished;
    private Player champion;


    //初始化
    public Tournament(String name, TournamentLevel level, TournamentFormat format, List<Player> playerList){
        tournamentName = name;
        this.level = level;
        this.format = format;
        this.playerList = playerList;
        this.format.initBracket(playerList);

    }

    //核心方法,模拟一轮,至于一轮是包含什么,在format类里的方法规定
    //字段有点多余
    public void simulateNextStep (MatchEngine matchEngine, MatchSettlementService matchSettlementService){
        if(format.isCompleted()) return;
        List<MatchNode> playableMatches = format.getPlayableMatches();
        if (playableMatches.isEmpty()) return;

        for (MatchNode matchNode : playableMatches) {
            RawMatchResult result = matchEngine.simulate(matchNode, bestOf);
            //todo 这里找个地方变成matchresult类 传进第三层,等第三层完成先
            format.submitMatchResult(matchNode,result);
        }
        //完结后调整boolean并生成dto
        if(format.isCompleted()) {
            archive();
        }
    }
    //一次性模拟完
    public void simulateAll(MatchEngine matchEngine, MatchSettlementService matchSettlementService) {
        while(!isFinished){
            simulateNextStep(matchEngine,matchSettlementService);
        }
    }
    public Player getChampion() {
        return champion;
    }
    public void archive(){
        isFinished=true;
        champion=format.getChampion();
        //new TournamentVO(this);//todo 这个地方实际上并没有用上

        //todo 存入数据库,改用entity类


    }


    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public TournamentLevel getLevel() {
        return level;
    }

    public void setLevel(TournamentLevel level) {
        this.level = level;
    }

    public TournamentFormat getFormat() {
        return format;
    }

    public void setFormat(TournamentFormat format) {
        this.format = format;
    }

    public int getPlayerNums() {
        return playerNums;
    }

    public void setPlayerNums(int playerNums) {
        this.playerNums = playerNums;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public int getBestOf() {
        return bestOf;
    }

    public void setBestOf(int bestOf) {
        this.bestOf = bestOf;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public void setChampion(Player champion) {
        this.champion = champion;
    }
}
