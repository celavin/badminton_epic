package com.celavin.badmintonepic.service;

import com.celavin.badmintonepic.model.dto.MatchResult;
import com.celavin.badmintonepic.model.entity.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchSettlementService {
    /**
     * 核心结算方法
     */
    @Transactional // 保证所有更新要么全部成功，要么全部失败
    public void processMatchSettlement(MatchResult result) {
        Player winner = result.getWinner();
        Player loser = result.getLoser();

        // 1. 更新近10场战绩 (Recent Form)
        updatePlayerForm(winner, true);
        updatePlayerForm(loser, false);

        // 2. 计算并更新积分 (Ranking Points)
        calculatePoints(result);

        // 3. 动态状态更新 (士气、体力损耗等)
        updateDynamicStatus(winner, loser);

        // 4. 历史记录存档 (预留：往数据库存这场比赛的详细记录)
        saveMatchHistory(result);

        // 5. 【预留位置】以后想加的功能直接往这里写...
        // handleInjuryCheck(winner, loser); // 伤病检查
        // handleAchievement(winner);       // 成就判定
    }

    private void updatePlayerForm(Player player, boolean isWin) {
        // 调用你之前在 Player 类里写的 addResult 方法
        //player.addResult(isWin);
        // 这里可以加上更新数据库的操作
    }

    private void calculatePoints(MatchResult result) {
        // 简易逻辑：赢了加分，输了不扣（或者根据 Elo 等级分扣）
        System.out.println(">>> 正在为 " + result.getWinner().getName() + " 计算积分...");
        // TODO: 对接你的积分系统公式
    }

    private void updateDynamicStatus(Player winner, Player loser) {
        // 比如：赢家士气 +1，输家士气 -1
        winner.setMorale(Math.min(10, winner.getMorale() + 1));
        loser.setMorale(Math.max(1, loser.getMorale() - 1));
    }

    private void saveMatchHistory(MatchResult result) {
        // TODO: 插入 match_history 表
    }
}
