package com.celavin.badmintonepic.model.dto;


import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.entity.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class MatchResult {
    // 1. 核心结果
    private Player winner;
    private Player loser;
    private List<GameScore> scores; // ["21-15", "18-21", "21-19"]

    // 2. 比赛上下文 (Context)
    private TournamentLevel level;   // 定义的枚举
    private String tournamentName;   // 比赛名称，如 "2026伦敦公开赛"
    private String roundName;        // 轮次，如 "1/4决赛"
    private int bestOf;              // 赛制：3 或 5

    // 构造函数：只接收必要的上下文基础数据
    public MatchResult(MatchNode matchNode, String tournamentName, TournamentLevel level) {
        // 1. 从 Node 中提取结算必须的核心数据
        this.winner = matchNode.getWinner();
        this.loser = matchNode.getLoser();
        this.roundName = matchNode.getRoundName();
        this.level = level;

        // 2. 外部传入的上下文
        this.tournamentName = tournamentName;

        // 3. 防御性提取 RawMatchResult 中的数据
        if (matchNode.getResult() != null) {
            this.scores = matchNode.getResult().getScores();
            this.bestOf = matchNode.getResult().getBestOf();
        } else {
            // 处理轮空等特殊情况的兜底
            this.scores = new ArrayList<>();
            this.bestOf = 0;
        }
    }

    public MatchResult(Player winner, Player loser, List<GameScore> scores,
                       TournamentLevel level, String tournamentName,
                       String roundName, int bestOf) {
        this.winner = winner;
        this.loser = loser;
        this.scores = scores;
        this.level = (level == null) ? TournamentLevel.RANKED : level; // 默认值处理
        this.tournamentName = tournamentName;
        this.roundName = roundName;
        this.bestOf = bestOf;

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

    public List<GameScore> getScores() {
        return scores;
    }

    public void setScores(List<GameScore> scores) {
        this.scores = scores;
    }
}