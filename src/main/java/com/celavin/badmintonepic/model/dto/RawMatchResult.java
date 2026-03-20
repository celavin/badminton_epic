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
        // 防止空指针异常
        result.winner = winner;
        result.loser = null;
        result.p1 = winner;
        result.p2=null;
        result.isBye = true;
        result.remark = "轮空晋级 (Bye)";
        result.scores = new java.util.ArrayList<>(); // 补充：初始化为空列表，防止前端或转换 MatchResult 时遍历报错
        result.bestOf = 0;   // 轮空不需要打
        result.p1Games = 0;//todo 大比分成绩待封装成类
        result.p2Games = 0;
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
