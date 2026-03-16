package com.celavin.badmintonepic.model.dto;

import com.celavin.badmintonepic.model.entity.Player;

import java.util.List;

public class RawMatchResult {
    private Player p1;
    private Player p2;
    private int bestOf;
    private List<GameScore> scores;
    private int p1Games;
    private int p2Games;
    private Player winner;
    private Player loser;
    private boolean isBye; // 可选：加个标记表示这是否是轮空
    private String remark;


    public RawMatchResult(){}

    // 从Match对象构造结果
    public RawMatchResult(Match match) {
        this.p1 = match.getP1();
        this.p2 = match.getP2();
        this.bestOf = match.getBestOf();
        this.scores = match.getScores();
        this.p1Games = match.getP1Games();
        this.p2Games = match.getP2Games();
        this.winner = match.getWinner();
        this.loser = match.getLoser();
    }
    //轮空构造器
    public static RawMatchResult createByeResult(Player winner) {
        RawMatchResult result = new RawMatchResult();
        // 如果你的内部没有 setter，就用对应的构造器
        result.winner = winner;
        result.isBye = true;
        result.remark = "轮空晋级 (Bye)";
        return result;
    }

    // 显示结果
    public void show(){
        System.out.println("最终大比分 [" + p1Games + ":" + p2Games + "]");
        System.out.println("小分明细：" + scores);
        System.out.println("======================================");
    }

    // Getter方法
    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public int getBestOf() {
        return bestOf;
    }

    public List<GameScore> getScores() {
        return scores;
    }

    public int getP1Games() {
        return p1Games;
    }

    public int getP2Games() {
        return p2Games;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getLoser() {
        return loser;
    }
}
