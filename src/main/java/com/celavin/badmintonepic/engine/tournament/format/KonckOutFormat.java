package com.celavin.badmintonepic.engine.tournament.format;

import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.dto.RawMatchResult;
import com.celavin.badmintonepic.model.entity.Player;

import java.util.List;

public class KonckOutFormat implements TournamentFormat {
    List<MatchNode> currentRoundMatches;//当前轮次正在进行的对阵列表。

    int totalPlayers;//初始总人数（用于推算当前是几强赛）。

    int currentRemaining;//当前存活人数（如 16 -> 8 -> 4）。

    //TODO 实现方法,然后就可以单元测试了;


    @Override
    public void initBracket(List<Player> playerList) {

    }

    @Override
    public List<MatchNode> getPendingMatches() {
        return List.of();
    }

    @Override
    public void processResultsAndAdvance(List<MatchNode> playedMatches) {

    }

    @Override
    public boolean hasWinner() {
        return false;
    }

    @Override
    public Player getChampion() {
        return null;
    }
}
