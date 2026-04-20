package com.celavin.badmintonepic.util;

import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.entity.Player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 从签表树收集参赛选手（用于持久化恢复后填充 {@code Tournament#playerList}）。
 */
public final class BracketPlayerCollector {

    private BracketPlayerCollector() {}

    public static List<Player> collectFromRoot(MatchNode root) {
        if (root == null) {
            return List.of();
        }
        Map<Long, Player> byId = new LinkedHashMap<>();
        dfs(root, byId);
        return new ArrayList<>(byId.values());
    }

    private static void dfs(MatchNode node, Map<Long, Player> byId) {
        if (node == null) {
            return;
        }
        putIfPresent(byId, node.getP1());
        putIfPresent(byId, node.getP2());
        putIfPresent(byId, node.getWinner());
        dfs(node.getPrevMatch1(), byId);
        dfs(node.getPrevMatch2(), byId);
    }

    private static void putIfPresent(Map<Long, Player> byId, Player p) {
        if (p == null || p.getId() == null) {
            return;
        }
        byId.putIfAbsent(p.getId(), p);
    }
}
