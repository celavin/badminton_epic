package com.celavin.badmintonepic.enums;

public enum TournamentLevel {
    RANKED("排位赛", 1.0),      // 默认/基础
    SATELLITE("卫星赛", 1.2),     // L5
    FUTURES("希望赛", 1.5),       // L4
    CHALLENGE("挑战赛", 2.0),     // L3
    ELITE("精英赛", 5.0),         // L2
    MAJOR("大师赛", 10.0);      // L1

    private final String description;
    private final double multiplier;

    TournamentLevel(String description, double multiplier) {
        this.description = description;
        this.multiplier = multiplier;
    }

    public double getMultiplier() { return multiplier; }
    public String getDescription() { return description; }
}
