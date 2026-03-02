package com.celavin.badmintonepic.model.dto;

import com.celavin.badmintonepic.model.entity.Player;

import java.util.List;

public class RawMatchResult {
    private Player winner;
    private Player loser;
    private List<String> scores;
    private int bestOf;



//-----------------------------
    public RawMatchResult(Player winner, Player loser, List<String> scores, int bestOf) {
        this.winner = winner;
        this.loser = loser;
        this.scores = scores;
        this.bestOf = bestOf;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public int getBestOf() {
        return bestOf;
    }

    public void setBestOf(int bestOf) {
        this.bestOf = bestOf;
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
}
