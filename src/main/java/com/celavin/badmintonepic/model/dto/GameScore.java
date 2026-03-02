package com.celavin.badmintonepic.model.dto;

public class GameScore {
    private int p1Score;
    private int p2Score;

    public GameScore(int p1Score, int p2Score) {
        this.p1Score = p1Score;
        this.p2Score = p2Score;
    }

    @Override
    public String toString() {
        return p1Score+"-"+p2Score;
    }

    // 提供一些实用的辅助方法，以后结算时直接调
    public boolean isP1Win() { return p1Score > p2Score; }
    public int getPointDiff() { return Math.abs(p1Score - p2Score); }

    // Getter 省略...
}
