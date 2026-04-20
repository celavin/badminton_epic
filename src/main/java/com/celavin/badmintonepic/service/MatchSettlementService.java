package com.celavin.badmintonepic.service;

import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.MatchResult;
import com.celavin.badmintonepic.model.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//todo采用elo公式结算,后续待优化
@Service
public class MatchSettlementService {
    @Autowired
    PlayerService playerService;
    @Autowired
    MatchRecordService matchRecordService;

    // 基础 K 值，决定了积分变动的基本基数
    private static final int BASE_K = 20;

    /** 锦标赛场次结算（默认） */
    public void settleSingleMatch(MatchResult matchResult) {
        settleSingleMatch(matchResult, MatchRecordService.KIND_TOURNAMENT);
    }

    /** @param matchKind {@link MatchRecordService#KIND_RANKED} 或 {@link MatchRecordService#KIND_TOURNAMENT} */
    public void settleSingleMatch(MatchResult matchResult, String matchKind) {
        // 如果是轮空晋级，不产生积分变动（实际比赛没打）
        if (matchResult.getLoser() == null) {
            return;
        }

        Player winner = matchResult.getWinner();
        Player loser = matchResult.getLoser();
        TournamentLevel level = matchResult.getLevel();
        String roundName = matchResult.getRoundName();

        // 1. 获取双方赛前积分
        int winnerPoints = winner.getPoints();
        int loserPoints = loser.getPoints();

        // 2. 计算动态 K 值 (基础K值 * 赛事级别权重 * 轮次权重)
        double kFactor = calculateKFactor(level, roundName);

        // 3. 计算预期胜率 (Elo 核心公式，得出 0.0 ~ 1.0 的概率)
        double expectedWinner = 1.0 / (1.0 + Math.pow(10, (loserPoints - winnerPoints) / 400.0));
        double expectedLoser = 1.0 / (1.0 + Math.pow(10, (winnerPoints - loserPoints) / 400.0));

        // 4. 计算实际积分变动量
        // 胜者得分：K * (1 - 预期胜率)
        int winnerGain = (int) Math.round(kFactor * (1.0 - expectedWinner));
        // 败者扣分：K * (0 - 预期胜率) => 负数
        int loserDrop = (int) Math.round(kFactor * (0.0 - expectedLoser));

        // 5. 应用积分变动并防止跌破 0 分
        winner.setPoints(winnerPoints + winnerGain);
        loser.setPoints(Math.max(0, loserPoints + loserDrop));

        // 6. 更新最高积分记录
        if (winner.getPoints() > winner.getHighestPoints()) {
            winner.setHighestPoints(winner.getPoints());
        }

        // 7. 更新双方士气 (Morale)
        updateMorale(winner, loser, winnerGain);

        //8.存回表
        playerService.updateById(winner);
        playerService.updateById(loser);

        matchRecordService.recordFromMatchResult(matchResult, matchKind);
    }

    /**
     * 计算动态 K 值：融合了你 PRD 要求的 Tournament_Weight
     */
    private double calculateKFactor(TournamentLevel level, String roundName) {
        double levelMultiplier = (level != null) ? level.getMultiplier() : 1.0;
        double roundMultiplier = getRoundMultiplier(roundName);

        return BASE_K * levelMultiplier * roundMultiplier;
    }

    /**
     * 轮次权重映射 (可以根据需要增删)
     */
    private double getRoundMultiplier(String roundName) {
        if (roundName == null) return 1.0;
        return switch (roundName) {
            case "决赛" -> 2.0;
            case "半决赛" -> 1.5;
            case "1/4决赛" -> 1.2;
            case "1/8决赛" -> 1.1;
            default -> 1.0; // 小组赛、首轮等基础权重
        };
    }

    /**
     * 更新士气：根据比赛的含金量/爆冷程度来决定士气波动
     */
    private void updateMorale(Player winner, Player loser, int winnerGain) {
        // 如果加分非常多 (>40分)，说明这是一场以弱胜强的惊天大爆冷，或者是赢下了 Major 决赛
        int moraleBoost = (winnerGain >= 40) ? 2 : 1;
        int moraleDrop = (winnerGain >= 40) ? -2 : -1;

        // 士气范围限制在 0 - 10 之间
        winner.setMorale(Math.min(10, winner.getMorale() + moraleBoost));
        loser.setMorale(Math.max(0, loser.getMorale() + moraleDrop));
    }
}