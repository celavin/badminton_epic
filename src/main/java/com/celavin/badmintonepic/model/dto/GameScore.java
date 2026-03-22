package com.celavin.badmintonepic.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//基本完备
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameScore {
    private int p1Score;
    private int p2Score;
    public GameScore(){}

    public GameScore(int p1Score, int p2Score) {
        this.p1Score = p1Score;
        this.p2Score = p2Score;
    }

    @Override
    public String toString() {
        return p1Score+"-"+p2Score;
    }

    // 提供一些实用的辅助方法，以后结算时直接调
    @JsonIgnore
    public boolean isP1Win() { return p1Score > p2Score; }
    public int getPointDiff() { return Math.abs(p1Score - p2Score); }

    public int getP1Score() {
        return p1Score;
    }

    public void setP1Score(int p1Score) {
        this.p1Score = p1Score;
    }

    public int getP2Score() {
        return p2Score;
    }

    public void setP2Score(int p2Score) {
        this.p2Score = p2Score;
    }
}
