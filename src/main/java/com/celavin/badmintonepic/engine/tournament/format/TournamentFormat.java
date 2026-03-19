package com.celavin.badmintonepic.engine.tournament.format;

import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.dto.RawMatchResult;
import com.celavin.badmintonepic.model.entity.Player;

import java.util.List;

public interface TournamentFormat {
    //1.初始化对阵图,完成建树和第一次落位
    void initBracket(List<Player> playerList);
    // 2. 调度查询：只返回当前状态下 isPlayable() 为 true 的比赛
    List<MatchNode> getPlayableMatches();
    // 3. 结果提报与内部推演：外部打完比赛把结果塞回来，赛制内部自行把胜者推入 nextMatch
    void submitMatchResult(MatchNode node, RawMatchResult result);

    // 4. 赛事状态
    boolean isCompleted();
    Player getChampion();

    // (UI渲染相关的方法可以先保留，或者后续移到专门的 Printer 类中)
    String generateBracket();
    MatchNode getFinalNode();

}
