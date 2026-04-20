package com.celavin.badmintonepic.calendar;

/**
 * 每月 4 周：第 1–2 周为排位周，第 3–4 周为赛事周（与需求文档一致）。
 */
public final class ScheduleCalendar {

    public static final int WEEKS_PER_MONTH = 4;
    public static final int DAYS_PER_WEEK = 7;

    private ScheduleCalendar() {}

    /** 本月第几周：1–4（与 {@link com.celavin.badmintonepic.model.entity.GameState#getWeek()} 一致） */
    public static boolean isRankedWeek(int weekOfMonth) {
        return weekOfMonth >= 1 && weekOfMonth <= 2;
    }

    public static boolean isTournamentWeek(int weekOfMonth) {
        return weekOfMonth >= 3 && weekOfMonth <= 4;
    }
}
