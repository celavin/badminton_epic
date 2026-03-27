package com.celavin.badmintonepic.service.impl;

import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.TournamentScheduleEntry;
import com.celavin.badmintonepic.service.TournamentCalendarService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TournamentCalendarServiceImpl implements TournamentCalendarService {
    private static final TournamentLevel[] SEASON_TEMPLATE = {
            TournamentLevel.CHALLENGE,
            TournamentLevel.CHALLENGE,
            TournamentLevel.CHALLENGE,
            TournamentLevel.CHALLENGE,
            TournamentLevel.ELITE,
            TournamentLevel.MAJOR,
            TournamentLevel.CHALLENGE,
            TournamentLevel.CHALLENGE,
            TournamentLevel.CHALLENGE,
            TournamentLevel.CHALLENGE,
            TournamentLevel.ELITE,
            TournamentLevel.MAJOR
    };

    @Override
    public List<TournamentScheduleEntry> getYearSchedule(int year) {
        List<TournamentScheduleEntry> schedule = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            schedule.add(createEntry(year, month, SEASON_TEMPLATE[month - 1]));
        }
        return schedule;
    }

    @Override
    public List<TournamentScheduleEntry> getMonthlySchedule(int year, int month) {
        if (month < 1 || month > 12) {
            return List.of();
        }
        return List.of(createEntry(year, month, SEASON_TEMPLATE[month - 1]));
    }

    private TournamentScheduleEntry createEntry(int year, int month, TournamentLevel level) {
        return new TournamentScheduleEntry(
                year,
                month,
                generateStableName(year, month, level),
                level,
                drawSizeFor(level));
    }

    private String generateStableName(int year, int month, TournamentLevel level) {
        long seed = (year * 10_000L) + (month * 100L) + level.ordinal();
        Random random = new Random(seed);
        String[] prefixes = {
                "Beijing", "Shanghai", "Guangzhou", "Shenzhen", "Nanjing", "Hangzhou",
                "Tokyo", "Seoul", "Singapore", "Bangkok", "Paris", "London",
                "Berlin", "Sydney", "NewYork", "Dubai", "World", "Asia"
        };
        String suffix = switch (level) {
            case MAJOR -> " Masters";
            case ELITE -> " Open";
            case CHALLENGE -> " Challenge";
            default -> " Series";
        };
        return prefixes[random.nextInt(prefixes.length)] + suffix;
    }

    private int drawSizeFor(TournamentLevel level) {
        return switch (level) {
            case MAJOR -> 8;
            case ELITE -> 16;
            default -> 16;
        };
    }
}
