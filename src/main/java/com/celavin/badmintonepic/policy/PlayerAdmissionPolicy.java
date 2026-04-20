package com.celavin.badmintonepic.policy;

import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.entity.Player;

import java.util.List;

public interface PlayerAdmissionPolicy {

    List<Player> selectPlayers(TournamentLevel level, int requiredNums);

    /**
     * 全积分排序列表中切分高/低赛道（互不重叠）：高赛道取前 fieldSize 名，低赛道取接下来 fieldSize 名。
     */
    List<Player> pickTrack(List<Player> rankedOrder, boolean highTrack, int fieldSize);
}

