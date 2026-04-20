package com.celavin.badmintonepic.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.celavin.badmintonepic.engine.tournament.Tournament;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.enums.TournamentStatus;
import com.celavin.badmintonepic.handler.PgJsonbTypeHandler;
import com.celavin.badmintonepic.model.dto.MatchNode;

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

    /**
     * 从内存中的 {@link Tournament} 生成可持久化快照（支持 SCHEDULED / ONGOING / COMPLETED）。
     */
    public static TournamentEntity snapshotFromRunningOrCompleted(Tournament t) {
        TournamentEntity e = new TournamentEntity();
        e.setName(t.getTournamentName());
        e.setLevel(t.getLevel());
        e.setYear(t.getYear());
        e.setMonth(t.getMonth());
        MatchNode fn = t.getFormat() != null ? t.getFormat().getFinalNode() : null;
        e.setFinalNode(fn);
        TournamentStatus st = t.getStatus();
        if (st == TournamentStatus.COMPLETED) {
            e.setStatus(TournamentStatus.COMPLETED);
            Player c = t.getChampion();
            if (c != null) {
                e.setChampionName(c.getName());
                e.setChampionId(c.getId());
            }
            Player r = t.getRunnerUp();
            if (r != null) {
                e.setRunnerUpName(r.getName());
                e.setRunnerUpId(r.getId());
            }
        } else if (fn != null) {
            e.setStatus(TournamentStatus.ONGOING);
        } else {
            e.setStatus(TournamentStatus.SCHEDULED);
        }
        return e;
    }

    public TournamentEntity(Tournament t) {
        TournamentEntity snap = snapshotFromRunningOrCompleted(t);
        this.name = snap.getName();
        this.level = snap.getLevel();
        this.finalNode = snap.getFinalNode();
        this.championName = snap.getChampionName();
        this.championId = snap.getChampionId();
        this.runnerUpName = snap.getRunnerUpName();
        this.runnerUpId = snap.getRunnerUpId();
        this.year = snap.getYear();
        this.month = snap.getMonth();
        this.status = snap.getStatus();
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

    public TournamentStatus getStatus() {
        return status;
    }

    public void setStatus(TournamentStatus status) {
        this.status = status;
    }
}