package com.celavin.badmintonepic.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.MatchNode;

import java.time.LocalDate;

@TableName(value = "tournaments", autoResultMap = true)
public class TournamentEntity {

    // 对应 PostgreSQL 的 BIGSERIAL
    @TableId(type = IdType.AUTO)
    private Long id;
    // 赛事名称
    private String name;
    // 赛事级别（MyBatis 默认会将 Enum 映射为 String 存入数据库）
    private TournamentLevel level;
    // 复杂的对阵图树节点，交由 JacksonTypeHandler 序列化为 JSON 字符串存入 JSONB 字段
    @TableField(typeHandler = JacksonTypeHandler.class)
    private MatchNode finalNode;
    // 只存储外键 ID，不嵌套庞大的 Player 对象
    private Long championId;
    // 游戏内举办日期
    private LocalDate date;

    // ---------- Getters and Setters ----------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TournamentLevel getLevel() {
        return level;
    }

    public void setLevel(TournamentLevel level) {
        this.level = level;
    }

    public MatchNode getFinalNode() {
        return finalNode;
    }

    public void setFinalNode(MatchNode finalNode) {
        this.finalNode = finalNode;
    }

    public Long getChampionId() {
        return championId;
    }

    public void setChampionId(Long championId) {
        this.championId = championId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}