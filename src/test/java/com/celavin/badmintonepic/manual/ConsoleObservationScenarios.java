package com.celavin.badmintonepic.manual;

import com.celavin.badmintonepic.engine.tournament.format.KnockOutFormat;
import com.celavin.badmintonepic.model.dto.ChampionStatsDTO;
import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.dto.TournamentScheduleEntry;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.model.entity.TournamentEntity;
import com.celavin.badmintonepic.service.GameStateService;
import com.celavin.badmintonepic.service.PlayerService;
import com.celavin.badmintonepic.service.TournamentCalendarService;
import com.celavin.badmintonepic.service.TournamentManageService;
import com.celavin.badmintonepic.service.TournamentRegistrationService;
import com.celavin.badmintonepic.service.TournamentService;
import com.celavin.badmintonepic.util.TournamentPrinter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
@SpringBootTest
class ConsoleObservationScenarios {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private TournamentManageService tournamentManageService;

    @Autowired
    private GameStateService gameStateService;

    @Autowired
    private TournamentCalendarService tournamentCalendarService;

    @Autowired
    private TournamentRegistrationService tournamentRegistrationService;

    @Test
    void watchRank() {
        playerService.getRankedPlayers().forEach(player ->
                System.out.println(player.getRank() + ":" + player.getName() + " " + player.getPoints()));
    }

    @Test
    void watchRecentTournaments() {
        tournamentService.getLast12Tournaments().forEach(System.out::println);
    }

    @Test
    void watchLatestBracket() {
        TournamentEntity latestTournament = tournamentService.getLatestTournamentWithBracket();
        if (latestTournament == null) {
            System.out.println("No stored bracket found in tournaments table.");
            return;
        }

        System.out.println(latestTournament.getName() + " " + latestTournament.getLevel());

        MatchNode finalNode = latestTournament.getFinalNode();
        TournamentPrinter.show(finalNode);
    }

    @Test
    void watchChampionStats() {
        List<ChampionStatsDTO> stats = tournamentService.getChampionTitleStats();
        stats.forEach(System.out::println);
    }

    @Test
    void watchCurrentMonthSchedule() {
        int year = currentYear();
        int month = currentMonth();
        List<TournamentEntity> existingTournaments = tournamentService.getTournamentsByYearAndMonth(year, month);

        if (!existingTournaments.isEmpty()) {
            System.out.println("Current month already has saved tournaments:");
            existingTournaments.forEach(tournament -> System.out.println(
                    tournament.getYear() + "-" + tournament.getMonth()
                            + " | " + tournament.getLevel()
                            + " | " + tournament.getName()
                            + " | champion=" + tournament.getChampionName()
                            + " | runnerUp=" + tournament.getRunnerUpName()));
            return;
        }

        plannedSchedule(year, month).forEach(entry -> System.out.println(
                entry.getYear() + "-" + entry.getMonth()
                        + " | " + entry.getLevel()
                        + " | drawSize=" + entry.getDrawSize()
                        + " | " + entry.getTournamentName()));
    }

    @Test
    void watchCurrentMonthRegistrations() {
        int year = currentYear();
        int month = currentMonth();
        List<TournamentScheduleEntry> schedule = plannedSchedule(year, month);
        List<Player> players = playerService.list();

        for (TournamentScheduleEntry entry : schedule) {
            System.out.println("=== " + entry.getTournamentName() + " / " + entry.getLevel() + " ===");
            tournamentRegistrationService.selectParticipants(entry, players)
                    .forEach(player -> System.out.println(player.getName() + " " + player.getPoints()));
        }
    }

    @Test
    void simulateCurrentMonthTournament() {
        int year = currentYear();
        int month = currentMonth();
        List<TournamentEntity> existingTournaments = tournamentService.getTournamentsByYearAndMonth(year, month);
        if (!existingTournaments.isEmpty()) {
            System.out.println("Current month tournament already exists in database:");
            existingTournaments.forEach(System.out::println);
            return;
        }

        List<TournamentScheduleEntry> schedule = plannedSchedule(year, month);
        List<Player> players = playerService.list();

        for (TournamentScheduleEntry entry : schedule) {
            List<Player> participants = tournamentRegistrationService.selectParticipants(entry, players);
            System.out.println("=== Simulating " + entry.getTournamentName() + " / " + entry.getLevel() + " ===");
            participants.forEach(player -> System.out.println(player.getName() + " " + player.getPoints()));
            TournamentEntity tournament = tournamentManageService.runAndSaveTournament(
                    entry.getTournamentName(),
                    entry.getLevel(),
                    new KnockOutFormat(),
                    participants);
            System.out.println(tournament);
        }
    }

    @Test
    void simulateWholeYearAndWatchRecentResults() {
        gameStateService.loadGame();
        tournamentManageService.simulateWholeYear(playerService.list());
        watchRecentTournaments();
        watchRank();
    }

    private int currentYear() {
        gameStateService.loadGame();
        return gameStateService.getById(1).getYear();
    }

    private int currentMonth() {
        gameStateService.loadGame();
        return gameStateService.getById(1).getMonth();
    }

    private List<TournamentScheduleEntry> plannedSchedule(int year, int month) {
        return tournamentCalendarService.getMonthlySchedule(year, month);
    }
}
