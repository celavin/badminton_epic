package com.celavin.badmintonepic.policy.impl;

import com.celavin.badmintonepic.calendar.GameConstants;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.policy.PlayerAdmissionPolicy;
import com.celavin.badmintonepic.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerAdmissionPolicyImpl implements PlayerAdmissionPolicy {
    @Autowired
    PlayerService playerService;

    @Override
    public List<Player> selectPlayers(TournamentLevel level, int requiredNums) {
        return pickTrack(playerService.getRankedPlayers(), true, requiredNums);
    }

    @Override
    public List<Player> pickTrack(List<Player> rankedOrder, boolean highTrack, int fieldSize) {
        if (rankedOrder == null || rankedOrder.isEmpty()) {
            return List.of();
        }
        int from = highTrack ? GameConstants.HIGH_TRACK_OFFSET : GameConstants.LOW_TRACK_OFFSET;
        int to = Math.min(from + fieldSize, rankedOrder.size());
        if (from >= rankedOrder.size() || from >= to) {
            return List.of();
        }
        return new ArrayList<>(rankedOrder.subList(from, to));
    }
}
