package com.celavin.badmintonepic.util;

import com.celavin.badmintonepic.model.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerGenerator {
    private static final Random random = new Random();

    // 名字池（简易版，你可以以后扩充）
    private static final String[] SURNAMES = {"林", "李", "王", "张", "Axelsen", "Ginting", "桃田", "Antonsen", "石", "周"};
    private static final String[] GIVEN_NAMES = {"丹", "宗伟", "宇奇", "Viktor", "Anthony", "贤斗", "Anders", "天成"};

    // 国籍池与权重（中国、印尼、丹麦、日本、马来西亚等）
    private static final String[] COUNTRIES = {"中国", "印度尼西亚", "丹麦", "日本", "马来西亚", "中国台北", "泰国"};

    /**
     * 一键生成指定数量的球员
     */
    public static List<Player> generateBatch(int count) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            players.add(generateSinglePlayer());
        }
        return players;
    }

    private static Player generateSinglePlayer() {
        Player p = new Player();

        // 1. 基础信息
        p.setName(SURNAMES[random.nextInt(SURNAMES.length)] + GIVEN_NAMES[random.nextInt(GIVEN_NAMES.length)]);
        p.setAge(18 + random.nextInt(15)); // 18-33 岁
        p.setNationality(COUNTRIES[random.nextInt(COUNTRIES.length)]);

        // 2. 属性生成 (1-20)
        // 使用高斯分布逻辑：大部分人在 10-14 之间，极少数天才 18+
        p.setOffense(generateAttribute());
        p.setDefense(generateAttribute());
        p.setSkill(generateAttribute());
        p.setSpeed(generateAttribute());
        p.setStamina(generateAttribute());
        p.setMental(generateAttribute());

        // 3. 士气 (1-10)
        p.setMorale(4 + random.nextInt(5)); // 初始 4-8

        return p;
    }

    private static int generateAttribute() {
        // 简单模拟 FM 的数值分布：平均值 11，标准差 3
        int val = (int) (random.nextGaussian() * 3 + 11);
        return Math.max(1, Math.min(20, val));
    }
}