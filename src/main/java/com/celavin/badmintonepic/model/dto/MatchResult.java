package com.celavin.badmintonepic.model.dto;


import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.entity.Player;

import java.time.LocalDateTime;
import java.util.List;


public class MatchResult {
    // 1. 核心结果
    private Player winner;
    private Player loser;
    private List<String> scores; // ["21-15", "18-21", "21-19"]

    // 2. 比赛上下文 (Context)
    private TournamentLevel level;   // 定义的枚举
    private String tournamentName;   // 比赛名称，如 "2026伦敦公开赛"
    private String roundName;        // 轮次，如 "1/4决赛"
    private int bestOf;              // 赛制：3 或 5

    // 3. 统计数据 (用于结算或展示)
    private java.time.LocalDateTime matchTime;

    // --- 构造函数 (替代 Builder) ---
    public MatchResult(Player winner, Player loser, List<String> scores,
                       TournamentLevel level, String tournamentName,
                       String roundName, int bestOf) {
        this.winner = winner;
        this.loser = loser;
        this.scores = scores;
        this.level = (level == null) ? TournamentLevel.RANKED : level; // 默认值处理
        this.tournamentName = tournamentName;
        this.roundName = roundName;
        this.bestOf = bestOf;
        this.matchTime = java.time.LocalDateTime.now();
    }





//    ---------


    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Player getLoser() {
        return loser;
    }

    public void setLoser(Player loser) {
        this.loser = loser;
    }

    public List<String> getScores() {
        return scores;
    }

    public void setScores(List<String> scores) {
        this.scores = scores;
    }

    public TournamentLevel getLevel() {
        return level;
    }

    public void setLevel(TournamentLevel level) {
        this.level = level;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public int getBestOf() {
        return bestOf;
    }

    public void setBestOf(int bestOf) {
        this.bestOf = bestOf;
    }

    public LocalDateTime getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(LocalDateTime matchTime) {
        this.matchTime = matchTime;
    }
}