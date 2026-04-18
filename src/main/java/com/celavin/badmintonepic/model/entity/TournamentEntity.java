package com.celavin.badmintonepic.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.celavin.badmintonepic.engine.tournament.Tournament;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.enums.TournamentStatus;
import com.celavin.badmintonepic.handler.PgJsonbTypeHandler;
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

    @TableField(typeHandler = PgJsonbTypeHandler.class)
    private MatchNode finalNode;
    // 只存储外键 ID，不嵌套庞大的 Player 对象
    private String championName;
    private Long championId;
    private String runnerUpName;
    private Long runnerUpId;
    // 游戏内举办日期
    private int year;
    private int month;


    private TournamentStatus status;


    public TournamentEntity() {}

    public TournamentEntity(Tournament t){
        name=t.getTournamentName();
        level=t.getLevel();
        finalNode=t.getFormat().getFinalNode();
        championName=t.getChampion().getName();
        championId=t.getChampion().getId();
        runnerUpName=t.getRunnerUp().getName();
        runnerUpId=t.getRunnerUp().getId();
        year=t.getYear();
        month=t.getMonth();
        status=t.getStatus();


    }

    @Override
    public String toString() {
        return String.format("名称: %-15s | 等级: %-5s | 冠军: %-15s | 亚军: %-15s",
                name, level, championName, runnerUpName);
    }
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

    public String getRunnerUpName() {
        return runnerUpName;
    }

    public void setRunnerUpName(String runnerUpName) {
        this.runnerUpName = runnerUpName;
    }

    public Long getRunnerUpId() {
        return runnerUpId;
    }

    public void setRunnerUpId(Long runnerUpId) {
        this.runnerUpId = runnerUpId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }
}