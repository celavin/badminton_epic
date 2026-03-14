package com.celavin.badmintonepic.engine.tournament.format;

import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.entity.Player;

import java.util.List;

public interface TournamentFormat {
    // 1. 拿名单，排对阵
    void initBracket(List<Player> playerList);

    // 2. 赛事来找你要今天该打的比赛（返回的是未赛的 MatchNode）
    List<MatchNode> getPendingMatches();

    // 3. 赛事打完后，把有了结果的 MatchNode 还给你，你自己去排下一轮
    void processResultsAndAdvance(List<MatchNode> playedMatches);

    // 4. (取代 isFinished) 对阵表推演完了吗？
    boolean hasWinner();

    // 5. 拿最终冠军
    Player getChampion();

}
