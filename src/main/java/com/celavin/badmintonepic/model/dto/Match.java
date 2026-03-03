package com.celavin.badmintonepic.model.dto;

import com.celavin.badmintonepic.engine.simulator.GameEngine;
import com.celavin.badmintonepic.model.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Match {
    private Player p1;
    private Player p2;
    private int bestOf;
    private List<GameScore> scores;
    private int p1Games=0;
    private int p2Games=0;
    private Player winner;
    private Player loser;

    private GameEngine gameEngine;

    //初始化
    public Match(Player p1, Player p2, int bestOf, GameEngine gameEngine) {
        this.p1 = p1;
        this.p2 = p2;
        this.bestOf = bestOf;
        this.scores = new ArrayList<>();
        this.gameEngine = gameEngine;
    }

    // 执行比赛
    public RawMatchResult play() {
        System.out.println("=== 比赛开始：" + p1.getName()+ " VS " + p2.getName() + " ===");
        while (!isMatchOver()) {
            GameScore score = gameEngine.simulateGame(p1, p2);
            addGame(score);
        }
        finish();
        show();
        return new RawMatchResult(this);
    }

    //每一个小局结束后更新大比分
    private void addGame(GameScore score) {
        scores.add(score);
        if (score.isP1Win()) p1Games++; else p2Games++;
    }

    //检查是否结束
    private boolean isMatchOver() {
        int target = (bestOf / 2) + 1;
        return p1Games >= target || p2Games >= target;
    }

    // 最终确认谁是赢家
    private void finish() {
        if (p1Games > p2Games) {
            this.winner = p1;
            this.loser = p2;
        } else {
            this.winner = p2;
            this.loser = p1;
        }
    }

    private void show() {
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
