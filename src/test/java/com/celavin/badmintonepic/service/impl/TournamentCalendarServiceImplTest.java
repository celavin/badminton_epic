package com.celavin.badmintonepic.service.impl;

import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.TournamentScheduleEntry;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TournamentCalendarServiceImplTest {

    private final TournamentCalendarServiceImpl service = new TournamentCalendarServiceImpl();

    @Test
    void getYearScheduleBuildsTwelveMonthlyEvents() {
        List<TournamentScheduleEntry> schedule = service.getYearSchedule(2026);

        assertEquals(12, schedule.size());
        assertEquals(1, schedule.get(0).getMonth());
        assertEquals(12, schedule.get(11).getMonth());
        assertEquals(TournamentLevel.CHALLENGE, schedule.get(0).getLevel());
        assertEquals(TournamentLevel.ELITE, schedule.get(4).getLevel());
        assertEquals(TournamentLevel.MAJOR, schedule.get(5).getLevel());
        assertTrue(schedule.stream().allMatch(entry -> entry.getTournamentName() != null && !entry.getTournamentName().isBlank()));
    }

    @Test
    void getMonthlyScheduleReturnsSingleConfiguredEvent() {
        List<TournamentScheduleEntry> schedule = service.getMonthlySchedule(2027, 6);

        assertEquals(1, schedule.size());
        assertEquals(2027, schedule.get(0).getYear());
        assertEquals(6, schedule.get(0).getMonth());
        assertEquals(TournamentLevel.MAJOR, schedule.get(0).getLevel());
        assertEquals(8, schedule.get(0).getDrawSize());
    }

    @Test
    void getMonthlyScheduleIsStableForSameMonth() {
        List<TournamentScheduleEntry> first = service.getMonthlySchedule(2027, 6);
        List<TournamentScheduleEntry> second = service.getMonthlySchedule(2027, 6);

        assertEquals(first.get(0).getTournamentName(), second.get(0).getTournamentName());
    }
}
