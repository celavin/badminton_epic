package com.celavin.badmintonepic.model.dto;

import com.celavin.badmintonepic.model.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RawMatchResult {
    private Player p1;
    private Player p2;
    private int bestOf;
    private List<GameScore> scores;
    private int p1Games=0;
    private int p2Games=0;
    private Player winner;
    private Player loser;

    //初始化
    public RawMatchResult(Player p1, Player p2, int bestOf) {
        this.p1 = p1;
        this.p2 = p2;
        this.bestOf = bestOf;
        this.scores = new ArrayList<>();
    }
    //每一个小局结束后更新大比分
    public void addGame(GameScore score) {
        scores.add(score);
        if (score.isP1Win()) p1Games++; else p2Games++;
    }
    //检查是否结束
    public boolean isMatchOver() {
        int target = (bestOf / 2) + 1;
        return p1Games >= target || p2Games >= target;
    }

    // 核心逻辑：获取赢家
    public Player getWinner() {
        if (!isMatchOver()) return null;
        return p1Games > p2Games ? p1 : p2;
    }

    // 最终确认谁是赢家
    public void finish() {
        if (p1Games > p2Games) {
            this.winner = p1;
            this.loser = p2;
        } else {
            this.winner = p2;
            this.loser = p1;
        }
    }
    public void show(){
        System.out.println("最终大比分 [" + p1Games + ":" + p2Games + "]");
        System.out.println("小分明细：" + scores);
        System.out.println("======================================");
    }
//----------------------

    public Player getP1() {
        return p1;
    }

    public void setP1(Player p1) {
        this.p1 = p1;
    }

    public Player getP2() {
        return p2;
    }

    public void setP2(Player p2) {
        this.p2 = p2;
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

    public int getP1Games() {
        return p1Games;
    }

    public void setP1Games(int p1Games) {
        this.p1Games = p1Games;
    }

    public int getP2Games() {
        return p2Games;
    }

    public void setP2Games(int p2Games) {
        this.p2Games = p2Games;
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
}
