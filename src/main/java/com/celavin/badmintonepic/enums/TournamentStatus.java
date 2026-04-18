package com.celavin.badmintonepic.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum TournamentStatus {
    SCHEDULED(0, "筹备中"),   // 只有基本信息，还没人报名
    ONGOING(1, "进行中"),     // 名单已定，签表已出，正在打
    COMPLETED(2, "已完结");   // 冠军诞生，数据归档

    // @EnumValue 告诉 MyBatis-Plus：把这个字段的值存进数据库
    @EnumValue
    private final int code;

    // @JsonValue 告诉 Jackson：返回给前端的时候，显示这个中文描述（可选，看你前端需求）
    // @JsonValue
    private final String description;

    TournamentStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
