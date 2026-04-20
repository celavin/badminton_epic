package com.celavin.badmintonepic.context;

import com.celavin.badmintonepic.model.entity.GameState;

public class GameTimeContext {
    // 缓存当前的游戏状态
    private static GameState currentState;

    public static void setCurrentState(GameState state) {
        currentState = state;
    }

    public static GameState getCurrentState() {
        return currentState;
    }

    public static int getCurrentYear() {
        return currentState != null ? currentState.getYear() : 0;
    }

    public static int getCurrentMonth() {
        return currentState != null ? currentState.getMonth() : 0;
    }

    /** 本月第几周 1–4 */
    public static int getCurrentWeek() {
        return currentState != null ? currentState.getWeek() : 0;
    }

    /** 本周第几天 1–7（周一到周日） */
    public static int getCurrentDayOfWeek() {
        return currentState != null ? currentState.getDay() : 0;
    }
}