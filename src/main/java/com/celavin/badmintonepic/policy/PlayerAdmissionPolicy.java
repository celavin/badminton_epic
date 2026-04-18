package com.celavin.badmintonepic.policy;

import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.entity.Player;

import java.util.List;

//todo此处定义赛事球员准入规范
public interface PlayerAdmissionPolicy {
    List<Player> selectPlayers(TournamentLevel level, int requiredNums);
}

