package com.celavin.badmintonepic.util;

import com.celavin.badmintonepic.model.entity.MatchRecord;

import java.util.List;

public final class PlayerRecordStats {

    private PlayerRecordStats() {}

    /** 近 limit 场胜负描述，如 7胜3负 */
    public static String lastNWinLoss(List<MatchRecord> newestFirst, long playerId, int limit) {
        if (newestFirst == null || newestFirst.isEmpty()) {
            return "0胜0负";
        }
        int w = 0;
        int l = 0;
        int n = Math.min(limit, newestFirst.size());
        for (int i = 0; i < n; i++) {
            MatchRecord r = newestFirst.get(i);
            if (playerId == r.getWinnerId()) {
                w++;
            } else {
                l++;
            }
        }
        return w + "胜" + l + "负";
    }

    /**
     * 从最近一场往早统计连胜（正）或连败（负）；无比赛返回 0。
     */
    public static int currentStreak(List<MatchRecord> newestFirst, long playerId) {
        if (newestFirst == null || newestFirst.isEmpty()) {
            return 0;
        }
        int streak = 0;
        for (MatchRecord r : newestFirst) {
            boolean won = playerId == r.getWinnerId();
            if (streak == 0) {
                streak = won ? 1 : -1;
            } else if (streak > 0 && won) {
                streak++;
            } else if (streak < 0 && !won) {
                streak--;
            } else {
                break;
            }
        }
        return streak;
    }
}
