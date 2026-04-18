package com.celavin.badmintonepic.engine.tournament;

import com.celavin.badmintonepic.context.GameTimeContext;
import com.celavin.badmintonepic.engine.simulator.MatchEngine;
import com.celavin.badmintonepic.engine.tournament.format.TournamentFormat;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.enums.TournamentStatus;
import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.dto.RawMatchResult;
import com.celavin.badmintonepic.model.dto.TournamentVO;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.model.entity.TournamentEntity;
import com.celavin.badmintonepic.service.MatchSettlementService;

import javax.security.auth.callback.TextOutputCallback;
import java.util.List;

public class Tournament {

    private String tournamentName;
    private TournamentLevel level;
    private TournamentFormat format;
    private int playerNums;
    private List<Player> playerList;
    private int bestOf=5;//默认为5 可优化

    private TournamentStatus status;
    private Player champion;
    private Player runnerUp;

    private int year;
    private int month;


    //初始化
    public Tournament(String name, TournamentLevel level, TournamentFormat format, List<Player> playerList){
        tournamentName = name;
        this.level = level;
        this.format = format;
        this.playerList = playerList;
        this.format.initBracket(playerList);
        year= GameTimeContext.getCurrentYear();
        month= GameTimeContext.getCurrentMonth();

        status = TournamentStatus.SCHEDULED;

    }

    public Tournament() {

    }

    public Player getChampion() {return champion;}
    public Player getRunnerUp() {return runnerUp;}
    /**
     * 调整status,设置冠军和亚军
     * */
    public void archive(){
        status= TournamentStatus.COMPLETED;
        champion=format.getChampion();
        runnerUp=format.getRunnerUp();

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



    public void setChampion(Player champion) {
        this.champion = champion;
    }

    public void setRunnerUp(Player runnerUp) {
        this.runnerUp = runnerUp;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public TournamentStatus getStatus() {
        return status;
    }

    public void setStatus(TournamentStatus status) {
        this.status = status;
    }
}
