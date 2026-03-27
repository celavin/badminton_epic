package com.celavin.badmintonepic.service;

import com.celavin.badmintonepic.model.dto.TournamentScheduleEntry;

import java.util.List;

public interface TournamentCalendarService {
    List<TournamentScheduleEntry> getYearSchedule(int year);

    List<TournamentScheduleEntry> getMonthlySchedule(int year, int month);
}
