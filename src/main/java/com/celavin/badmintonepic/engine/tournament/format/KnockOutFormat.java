package com.celavin.badmintonepic.engine.tournament.format;

import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.dto.RawMatchResult;
import com.celavin.badmintonepic.model.entity.Player;

import java.util.List;

public class KnockOutFormat implements TournamentFormat {

    @Override
    public void initBracket(List<Player> playerList) {

    }

    @Override
    public List<MatchNode> getPlayableMatches() {
        return List.of();
    }

    @Override
    public void submitMatchResult(MatchNode node, RawMatchResult result) {
        node.setWinner(result.getWinner());
    }

    @Override
    public boolean isCompleted() {
        return false;
    }

    @Override
    public Player getChampion() {
        return null;
    }

    @Override
    public String generateBracket() {
        return "";
    }
}
