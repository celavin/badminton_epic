package com.celavin.badmintonepic.engine.tournament.format;

import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.dto.RawMatchResult;
import com.celavin.badmintonepic.model.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KnockOutFormat implements TournamentFormat {

    private MatchNode rootMatch; // 总决赛节点 (树根)
    private List<MatchNode> allMatches = new ArrayList<>(); // 方便扁平化查询所有比赛


    @Override
    public void initBracket(List<Player> playerList) {
        if (playerList == null || playerList.isEmpty()) return;

        // 1. 计算签位大小（找大于等于人数的最小 2的幂，例如 13人 -> 16签位）
        int bracketSize = 1;
        int totalRounds = 1; // 1轮是决赛
        while (bracketSize < playerList.size()) {
            bracketSize *= 2;
            totalRounds++;
        }
        totalRounds--; // 如果 bracketSize 是 16，就是 4 轮 (16进8, 8进4, 半决赛, 决赛)

        // 2. 自顶向下递归建空树
        this.rootMatch = buildEmptyTree(totalRounds, totalRounds, null);

        // 3. 收集所有的最底层叶子节点 (也就是第一轮的所有比赛)
        List<MatchNode> leafNodes = new ArrayList<>();
        collectLeafNodes(rootMatch, leafNodes);

        // 4. 将选手填入叶子节点，并处理轮空(Bye)
        fillPlayersAndHandleByes(playerList, leafNodes);
    }

    /**
     * 步骤 2 核心：递归建树
     * @param totalRounds 总轮次
     * @param remainingRounds 距离最底层还有几轮
     * @param nextMatch 下一场比赛（父节点）
     */
    private MatchNode buildEmptyTree(int totalRounds, int remainingRounds, MatchNode nextMatch) {
        if (remainingRounds == 0) return null;

        // 计算当前轮次的实际位置（从1开始，1是决赛）
        int currentRound = totalRounds - remainingRounds + 1;
        String roundName = getRoundName(1 << currentRound); // 1<<1是2强(决赛)，1<<2是4强
        MatchNode currentMatch = new MatchNode(roundName);
        currentMatch.setNextMatch(nextMatch);

        allMatches.add(currentMatch); // 加入大集合方便日后查询

        // 如果还没到底层，继续往下建前置比赛
        if (remainingRounds > 1) {
            currentMatch.setPrevMatch1(buildEmptyTree(totalRounds, remainingRounds - 1, currentMatch));
            currentMatch.setPrevMatch2(buildEmptyTree(totalRounds, remainingRounds - 1, currentMatch));
        }

        return currentMatch;
    }

    /**
     * 步骤 3 核心：通过 DFS（深度优先）找到所有第一轮的空位比赛
     */
    private void collectLeafNodes(MatchNode node, List<MatchNode> leafNodes) {
        if (node == null) return;
        if (node.getPrevMatch1() == null && node.getPrevMatch2() == null) {
            leafNodes.add(node);
        } else {
            collectLeafNodes(node.getPrevMatch1(), leafNodes);
            collectLeafNodes(node.getPrevMatch2(), leafNodes);
        }
    }

    /**
     * 步骤 4 核心：填入玩家，绝妙的“轮空”自动推演逻辑
     */
    private void fillPlayersAndHandleByes(List<Player> players, List<MatchNode> leafNodes) {
        Collections.shuffle(players);
        int playerIndex = 0;

        for (MatchNode leaf : leafNodes) {
            // 填入 P1
            if (playerIndex < players.size()) {
                leaf.setP1(players.get(playerIndex++));
            }
            // 填入 P2
            if (playerIndex < players.size()) {
                leaf.setP2(players.get(playerIndex++));
            }

            // === 处理轮空 (Bye) 的高光时刻 ===
            // 如果 P1 有人，但 P2 没人（轮空），P1 自动获胜进入下一轮！
            if (leaf.getP1() != null && leaf.getP2() == null) {

                submitMatchResult(leaf,RawMatchResult.createByeResult(leaf.getP1()));
            }
            // 极端情况：P1 和 P2 都没人（报名人数太少导致大量空节点），直接跳过
        }
    }

    @Override
    public List<MatchNode> getPlayableMatches() {
        return allMatches.stream()
                .filter(MatchNode::isPlayable)
                .toList();
    }

    @Override
    public void submitMatchResult(MatchNode node, RawMatchResult result) {
        node.setResult(result);
        node.pushWinnerToNext();
    }

    @Override
    public boolean isCompleted() {
        return rootMatch.getWinner()!=null;
    }

    @Override
    public Player getChampion() {
        return rootMatch.getWinner();
    }

    @Override
    public Player getRunnerUp() {
        return rootMatch.getLoser();
    }


    @Override
    public String generateBracket() {
        return "";
    }

    @Override
    public MatchNode getFinalNode() {
        return getRootMatch();
    }

    /**
     * 从持久化恢复的签表根节点重建内存状态（尤其是 {@code allMatches} 扁平列表）。
     */
    public void restoreFromRoot(MatchNode root) {
        this.rootMatch = root;
        this.allMatches = new ArrayList<>();
        if (root != null) {
            collectAllNodes(root, allMatches);
        }
    }

    private void collectAllNodes(MatchNode node, List<MatchNode> out) {
        if (node == null) {
            return;
        }
        out.add(node);
        collectAllNodes(node.getPrevMatch1(), out);
        collectAllNodes(node.getPrevMatch2(), out);
    }

    /**
     * 辅助方法：根据当前轮次的节点总坑位数，生成易读的轮次名称
     * @param slots 坑位数量（必须是2的幂，如 2, 4, 8, 16）
     */
    private String getRoundName(int slots) {
        if (slots == 2) {
            return "决赛";
        } else if (slots == 4) {
            return "半决赛";
        } else if (slots == 8) {
            return "1/4决赛";
        } else if (slots == 16) {
            return "1/8决赛";
        } else {
            // 比如 slots 是 32，就是 "32强赛"
            return slots + "强赛";
        }
    }


    public MatchNode getRootMatch() {
        return rootMatch;
    }

    public void setRootMatch(MatchNode rootMatch) {
        this.rootMatch = rootMatch;
    }

    public List<MatchNode> getAllMatches() {
        return allMatches;
    }

    public void setAllMatches(List<MatchNode> allMatches) {
        this.allMatches = allMatches;
    }
}
