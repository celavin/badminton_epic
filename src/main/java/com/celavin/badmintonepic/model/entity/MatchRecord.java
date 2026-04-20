package com.celavin.badmintonepic.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 单场对决记录（排位或锦标赛），用于近10场、连胜等统计。
 */
@TableName("match_records")
public class MatchRecord {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long winnerId;
    private Long loserId;
    /** 赛事级别枚举名，如 RANKED、MAJOR */
    private String level;
    private String tournamentName;
    private int gameYear;
    private int gameMonth;
    private int gameWeek;
    private int gameDay;
    /** RANKED 或 TOURNAMENT */
    private String matchKind;

    public MatchRecord() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long winnerId) {
        this.winnerId = winnerId;
    }

    public Long getLoserId() {
        return loserId;
    }

    public void setLoserId(Long loserId) {
        this.loserId = loserId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public int getGameYear() {
        return gameYear;
    }

    public void setGameYear(int gameYear) {
        this.gameYear = gameYear;
    }

    public int getGameMonth() {
        return gameMonth;
    }

    public void setGameMonth(int gameMonth) {
        this.gameMonth = gameMonth;
    }

    public int getGameWeek() {
        return gameWeek;
    }

    public void setGameWeek(int gameWeek) {
        this.gameWeek = gameWeek;
    }

    public int getGameDay() {
        return gameDay;
    }

    public void setGameDay(int gameDay) {
        this.gameDay = gameDay;
    }

    public String getMatchKind() {
        return matchKind;
    }

    public void setMatchKind(String matchKind) {
        this.matchKind = matchKind;
    }
}
