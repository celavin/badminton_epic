package com.celavin.badmintonepic.util;

import com.celavin.badmintonepic.model.entity.Player;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PlayerGenerator {
    private final Random random = new Random();

    // 使用 Map 替代 switch-case，更易拓展
    private final Map<String, Faker> fakerMap = new HashMap<>();
    // 默认支持随机生成的国家池
    private final List<String> defaultCountries = Arrays.asList("中国", "印度尼西亚", "丹麦", "日本", "马来西亚", "中国台北", "泰国");

    public PlayerGenerator() {
        // 初始化各个国家的 Faker 实例
        fakerMap.put("中国", new Faker(new Locale("zh", "CN")));
        fakerMap.put("中国台北", new Faker(new Locale("zh", "TW")));
        fakerMap.put("印度尼西亚", new Faker(new Locale("in", "ID")));
        fakerMap.put("丹麦", new Faker(new Locale("da", "DK")));
        fakerMap.put("日本", new Faker(new Locale("ja", "JP")));
        // 兜底的英文 Faker
        fakerMap.put("default", new Faker(Locale.ENGLISH));
    }

    /**
     * 核心生成逻辑
     * @param count 生成数量
     * @param nationality 指定国籍（若传入 null 或空字符串，则随机生成国籍）
     * @return 球员列表
     */
    public List<Player> generate(int count, String nationality) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            // 决定当前球员的国籍
            String targetCountry = (nationality != null && !nationality.trim().isEmpty())
                    ? nationality
                    : defaultCountries.get(random.nextInt(defaultCountries.size()));

            players.add(buildPlayer(targetCountry));
        }
        return players;
    }

    private Player buildPlayer(String country) {
        Player p = new Player();
        p.setNationality(country);

        // 匹配对应国家的 Faker 生成地道姓名，匹配不到则用 default
        Faker faker = fakerMap.getOrDefault(country, fakerMap.get("default"));
        p.setName(faker.name().fullName());

        p.setAge(18 + random.nextInt(15)); // 18-32 岁

        // 属性生成
        p.setPower(generateAttribute());
        p.setTactics(generateAttribute());
        p.setSkill(generateAttribute());
        p.setSpeed(generateAttribute());
        p.setStamina(generateAttribute());
        p.setMental(generateAttribute());

        p.setMorale(4 + random.nextInt(5)); // 初始士气 4-8
        return p;
    }

    private int generateAttribute() {
        // 模拟正态分布：平均值 11，标准差 3
        int val = (int) (random.nextGaussian() * 3 + 11);
        return Math.max(1, Math.min(20, val));
    }
}