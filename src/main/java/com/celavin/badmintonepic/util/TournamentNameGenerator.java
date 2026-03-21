package com.celavin.badmintonepic.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
//todo 检查优化赛事名称生成器

/**
 * 赛事名称随机生成工具
 */
public class TournamentNameGenerator {

    private static final Random RANDOM = new Random();

    // 城市/国家名列表 (挑选了羽毛球氛围浓厚的地区)
    private static final List<String> LOCATIONS = Arrays.asList(
            "All England", "China", "Indonesia", "Malaysia", "Japan",
            "Korea", "Denmark", "French", "India", "Singapore",
            "Taipei", "Hong Kong", "Thailand", "Macau", "Swiss",
            "German", "Spain", "Australia", "New Zealand", "USA",
            "Beijing", "Tokyo", "Jakarta", "Kuala Lumpur", "Paris",
            "Copenhagen", "Guangzhou", "Birmingham", "Bangkok"
    );

    // 赛事后缀列表 (对应现实中的公开赛、大师赛等)
    private static final List<String> SUFFIXES = Arrays.asList(
            "Open",            // 公开赛
            "Masters",         // 大师赛
            "Championships",   // 锦标赛
            "Classic",         // 经典赛
            "Invitational",    // 邀请赛
            "Cup",             // 杯赛
            "Grand Prix",      // 大奖赛
            "Challenge",       // 挑战赛
            "International"    // 国际赛
    );

    /**
     * 随机生成一个常规的赛事名称
     * 例如: "Tokyo Open", "Jakarta Masters"
     */
    public static String generateRandomName() {
        String location = LOCATIONS.get(RANDOM.nextInt(LOCATIONS.size()));
        String suffix = SUFFIXES.get(RANDOM.nextInt(SUFFIXES.size()));
        return location + " " + suffix;
    }

    /**
     * 如果以后你想根据赛事级别(Level)来固定后缀，也可以加一个重载方法
     * 例如：S1000 必定叫 Open，总决赛必定叫 Finals
     */
    public static String generateNameByLevel(String levelName) {
        String location = LOCATIONS.get(RANDOM.nextInt(LOCATIONS.size()));
        if ("WTF".equals(levelName)) { // World Tour Finals
            return location + " World Tour Finals";
        }
        return location + " " + levelName;
    }
}