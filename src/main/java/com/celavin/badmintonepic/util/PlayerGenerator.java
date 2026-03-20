package com.celavin.badmintonepic.util;

import com.celavin.badmintonepic.model.entity.Player;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
//todo 待解耦
public class PlayerGenerator {
    private static final Random random = new Random();

    // 预定义不同国家的 Faker 实例，用于生成极具真实感的跨国姓名
    private static final Faker zhFaker = new Faker(new Locale("zh", "CN")); // 中国
    private static final Faker idFaker = new Faker(new Locale("in", "ID")); // 印尼
    private static final Faker daFaker = new Faker(new Locale("da", "DK")); // 丹麦
    private static final Faker jaFaker = new Faker(new Locale("ja", "JP")); // 日本
    private static final Faker enFaker = new Faker(Locale.ENGLISH);         // 通用英文

    private static final String[] COUNTRIES = {"中国", "印度尼西亚", "丹麦", "日本", "马来西亚", "中国台北", "泰国"};

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
        String country = COUNTRIES[random.nextInt(COUNTRIES.length)];
        p.setNationality(country);

        // 2. 根据国籍生成地道的姓名！
        p.setName(generateNameByCountry(country));

        p.setAge(18 + random.nextInt(15)); // 18-33 岁

        // 3. 属性生成 (1-20)
        p.setPower(generateAttribute());
        p.setTactics(generateAttribute());
        p.setSkill(generateAttribute());
        p.setSpeed(generateAttribute());
        p.setStamina(generateAttribute());
        p.setMental(generateAttribute());

        // 4. 士气 (1-10)
        p.setMorale(4 + random.nextInt(5)); // 初始 4-8

        return p;
    }

    // 路由分发姓名生成逻辑
    private static String generateNameByCountry(String country) {
        switch (country) {
            case "中国":
            case "中国台北":
                return zhFaker.name().fullName(); // 例如：张伟, 王建国
            case "印度尼西亚":
                return idFaker.name().fullName(); // 例如：Budi Santoso
            case "丹麦":
                return daFaker.name().fullName(); // 例如：Lars Nielsen
            case "日本":
                return jaFaker.name().fullName(); // 例如：佐藤 健
            default:
                return enFaker.name().fullName(); // 马来西亚/泰国等暂时使用通用英文名，后续可继续扩展 Locale
        }
    }

    private static int generateAttribute() {
        // 模拟 FM 的数值分布：平均值 11，标准差 3
        int val = (int) (random.nextGaussian() * 3 + 11);
        return Math.max(1, Math.min(20, val));
    }
}