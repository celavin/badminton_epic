package com.celavin.badmintonepic.engine.simulator;

import com.celavin.badmintonepic.model.dto.GameScore;
import com.celavin.badmintonepic.model.entity.Player;
import org.springframework.stereotype.Component;

import java.util.Random;
//只负责模拟单场比赛,其他不管
@Component
public class GameEngine {
    private final Random random = new Random();

    /**
     * 模拟单局 21 分比赛
     * @return 返回比分字符串，如 "21-18"
     */
    public GameScore simulateGame(Player p1, Player p2) {
        int score1 = 0;
        int score2 = 0;

        // 每一局开始，生成一个全局手感波动 (-3 到 +3)
        int form1 = random.nextInt(7) - 3;
        int form2 = random.nextInt(7) - 3;

        // 计算双方的“即时综合战力”
        double power1 = calculatePower(p1, form1);
        double power2 = calculatePower(p2, form2);

        // 开始分对分模拟
        while (!isGameFinished(score1, score2)) {
            // 计算当前分球权的获胜概率
            // 谁战力高，拿这一分的概率就大
            double winProb1 = power1 / (power1 + power2);

            if (random.nextDouble() < winProb1) {
                score1++;
            } else {
                score2++;
            }
        }

        return new GameScore(score1,score2);
    }

    private double calculatePower(Player p, int form) {
        double offense = Math.max(1, p.getOffense() + form);
        double skill = Math.max(1, p.getSkill() + form);
        double defense = Math.max(1, p.getDefense() + form);
        double speed = Math.max(1, p.getSpeed() + form);

        // FM 风格公式：进攻和技术是核心
        return (offense * 0.35) + (skill * 0.35) + (defense * 0.2) + (speed * 0.1);
    }

    private boolean isGameFinished(int s1, int s2) {
        // 基础 21 分制，至少领先 2 分，最高 30 分
        if ((s1 >= 21 || s2 >= 21) && Math.abs(s1 - s2) >= 2) return true;
        return s1 == 30 || s2 == 30;
    }

}
