package com.celavin.badmintonepic.model.dto;

import com.celavin.badmintonepic.model.entity.Player;

public class MatchNode {
    // === 1. 赛前基础信息 (赛制引擎生成时确定) ===
    private Player p1;
    private Player p2;
    private String roundName; // 例如 "1/8决赛", "决赛"

    // === 2. 赛后结果信息 (打完比赛后回填) ===
    private Player winner;
    private RawMatchResult result;

    // 构造函数：生成对阵时只需要双方球员和轮次
    public MatchNode(Player p1, Player p2, String roundName) {
        this.p1 = p1;
        this.p2 = p2;
        this.roundName = roundName;
    }

    // 实用方法：判断这场比赛打过了没有
    public boolean isPlayed() {
        return this.winner != null && this.result != null;
    }

    // 实用方法：供赛制引擎在打完后记录结果
    public void setMatchResult(RawMatchResult result) {
        this.result = result;
        this.winner = result.getWinner();//这里同时设置了俩
    }

    // ---------- 以下为常规 Getter ----------
    public Player getP1() { return p1; }
    public Player getP2() { return p2; }
    public String getRoundName() { return roundName; }
    public Player getWinner() { return winner; }
    public RawMatchResult getResult() { return result; }
}
