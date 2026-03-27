package com.celavin.badmintonepic.model.dto;

import com.celavin.badmintonepic.enums.TournamentLevel;

public class TournamentScheduleEntry {
    private final int year;
    private final int month;
    private final String tournamentName;
    private final TournamentLevel level;
    private final int drawSize;

    public TournamentScheduleEntry(int year, int month, String tournamentName, TournamentLevel level, int drawSize) {
        this.year = year;
        this.month = month;
        this.tournamentName = tournamentName;
        this.level = level;
        this.drawSize = drawSize;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public TournamentLevel getLevel() {
        return level;
    }

    public int getDrawSize() {
        return drawSize;
    }
}
