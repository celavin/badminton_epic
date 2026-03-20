package com.celavin.badmintonepic.util;

import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.dto.RawMatchResult;

public class TournamentPrinter {

    public static void show(MatchNode finalNode) {
        System.out.println("\n=================================== 赛事对阵图 ===================================");
        printNode(finalNode, "", true, true);
        System.out.println("==================================================================================\n");

        // 提取并展示最终冠军
        if (finalNode != null && finalNode.getWinner() != null) {
            System.out.println("🏆 最终冠军：" + finalNode.getWinner().getName());
        }
    }

    /**
     * 核心递归打印方法：(右 -> 根 -> 左) 的变形
     * 这样左半区会在上方，右半区会在下方，决赛在最左侧
     */
    private static void printNode(MatchNode node, String prefix, boolean isTail, boolean isRoot) {
        if (node == null) return;

        // 1. 优先递归打印上半区 (提供 P1 的前置比赛)
        if (node.getPrevMatch1() != null) {
            printNode(node.getPrevMatch1(), prefix + (isTail ? "    " : "│   "), false, false);
        }

        // 2. 打印当前比赛节点
        String branch = isRoot ? "" : (isTail ? "└── " : "┌── ");
        System.out.println(prefix + branch + formatMatchInfo(node));

        // 3. 递归打印下半区 (提供 P2 的前置比赛)
        if (node.getPrevMatch2() != null) {
            printNode(node.getPrevMatch2(), prefix + (isTail ? "    " : "│   "), true, false);
        }
    }

    /**
     * 格式化单场比赛的信息：[轮次] 选手A (大比分) 选手B {小分}
     */
    public static String formatMatchInfo(MatchNode node) {
        String round = "[" + node.getRoundName() + "]";
        String p1Name = (node.getP1() != null) ? node.getP1().getName() : "空缺/Bye";
        String p2Name = (node.getP2() != null) ? node.getP2().getName() : "空缺/Bye";

        RawMatchResult result = node.getResult();

        // 还没打的比赛
        if (result == null) {
            return String.format("%s %s vs %s (未赛)", round, p1Name, p2Name);
        }

        // 处理轮空情况 (对手为空，或者生成了专门的轮空 Result 没有小分记录)
        if (node.getP2() == null || (result.getScores() != null && result.getScores().isEmpty())) {
            return String.format("%s 🌟 %s 轮空直接晋级", round, p1Name);
        }

        // 正常完赛的记录
        int p1Games = result.getP1Games();
        int p2Games = result.getP2Games();
        String scoresDetail = result.getScores() != null ? result.getScores().toString() : "[]";

        // 给获胜者加个小标记增加可读性
        String p1Display = p1Games > p2Games ? p1Name + " 胜" : p1Name;
        String p2Display = p2Games > p1Games ? p2Name + " 胜" : p2Name;

        return String.format("%s %s (%d:%d) %s %s", round, p1Display, p1Games, p2Games, p2Display, scoresDetail);
    }
}