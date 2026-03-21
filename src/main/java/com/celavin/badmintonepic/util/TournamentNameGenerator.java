package com.celavin.badmintonepic.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 赛事名称随机生成工具
 */
public class TournamentNameGenerator {

    private static final Random RANDOM = new Random();

    // 赛事前缀：包含各大洲著名城市、羽毛球重镇以及特殊前缀
    private static final List<String> PREFIXES = Arrays.asList(
            // 中国大陆及港澳台
            "北京", "上海", "广州", "深圳", "南京", "杭州", "常州", "武汉", "成都", "重庆", "青岛", "厦门",
            "香港", "澳门", "台北", "高雄",
            // 亚洲其他地区
            "东京", "大阪", "横滨", "首尔", "仁川", "雅加达", "泗水", "吉隆坡", "槟城",
            "曼谷", "清迈", "新加坡", "马尼拉", "新德里", "孟买", "迪拜", "多哈",
            // 欧洲
            "伦敦", "伯明翰", "巴黎", "奥尔良", "哥本哈根", "奥胡斯", "马德里", "巴塞罗那",
            "柏林", "慕尼黑", "罗马", "米兰", "日内瓦", "巴塞尔", "阿姆斯特丹", "维也纳",
            // 美洲与大洋洲
            "纽约", "洛杉矶", "多伦多", "悉尼", "墨尔本", "奥克兰", "里约热内卢",
            // 特殊/非城市前缀
            "全英", "亚洲", "欧洲", "泛美", "大洋洲", "太平洋", "环球", "远东", "世界", "国际", "丝路"
    );

    // 赛事后缀：涵盖了各种常见的羽毛球赛制名称
    private static final List<String> SUFFIXES = Arrays.asList(
            "公开赛",       // Open
            "大师赛",       // Masters
            "锦标赛",       // Championships
            "挑战赛",       // Challenge
            "邀请赛",       // Invitational
            "大奖赛",       // Grand Prix
            "精英赛",       // Elite
            "经典赛",       // Classic
            "冠军赛",       // Champions
            "巡回赛",       // Tour
            "未来赛",       // Futures
            "卫星赛",       // Satellite
            "杯",           // Cup
            "系列赛",       // Series
            "对抗赛"        // Showdown/Clash
    );

    /**
     * 随机生成一个常规的赛事名称
     * 例如: "东京公开赛", "全英锦标赛", "常州大师赛"
     */
    public static String generateRandomName() {
        String prefix = PREFIXES.get(RANDOM.nextInt(PREFIXES.size()));
        String suffix = SUFFIXES.get(RANDOM.nextInt(SUFFIXES.size()));
        // 中文名称拼接不需要空格
        return prefix + suffix;
    }

    /**
     * 预留方法：如果以后你想根据赛事级别(Level)来固定后缀，可以在这里加逻辑
     * 目前保持不变，仅用于演示
     */
    public static String generateNameByLevel(String levelName) {
        String prefix = PREFIXES.get(RANDOM.nextInt(PREFIXES.size()));
        if ("WTF".equals(levelName)) { // World Tour Finals
            return prefix + "年终总决赛";
        }
        // 如果没有特定逻辑，暂且直接拼接传入的 levelName
        return prefix + levelName;
    }
}