package com.celavin.badmintonepic.calendar;

/**
 * 第一版后端可调参数（后续可收到 yaml）。
 */
public final class GameConstants {

    /** 单败淘汰赛人数（需 ≤ 玩家池；不足时取现有全部） */
    public static final int KNOCKOUT_FIELD_SIZE = 16;

    /** 高排名赛道：取全榜前 N 名中的前 field 人（从第 1 名起） */
    public static final int HIGH_TRACK_OFFSET = 0;

    /** 低排名赛道：从全榜第 (HIGH_TRACK_OFFSET + KNOCKOUT_FIELD_SIZE + 1) 名开始取 */
    public static final int LOW_TRACK_OFFSET = KNOCKOUT_FIELD_SIZE;

    /** 每个「模拟一天」里快速模拟的排位赛场次（每场两人） */
    public static final int RANKED_MATCHES_PER_DAY = 24;

    /** 积分榜默认条数 */
    public static final int LEADERBOARD_LIMIT = 200;

    private GameConstants() {}
}
