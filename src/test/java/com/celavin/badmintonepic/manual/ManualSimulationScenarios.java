package com.celavin.badmintonepic.manual;

import com.celavin.badmintonepic.engine.tournament.format.KnockOutFormat;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.ChampionStatsDTO;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.model.entity.TournamentEntity;
import com.celavin.badmintonepic.service.GameStateService;
import com.celavin.badmintonepic.service.PlayerService;
import com.celavin.badmintonepic.service.impl.TournamentManageServiceImpl;
import com.celavin.badmintonepic.service.TournamentService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


@SpringBootTest
@ActiveProfiles("test")
class ManualSimulationScenarios {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private TournamentManageServiceImpl tournamentManageService;

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private GameStateService gameStateService;

    @Test
    /**清空赛事表
     * 生成一批球员存进数据库*/
    void initTestWorld(){
        gameStateService.resetTime();
        tournamentService.clearAllTournaments();
        playerService.initPlayers();
    }

    @Test
    void simulateWholeYear() {
        gameStateService.loadGame();
        tournamentManageService.simulateWholeYear(playerService.list());
    }

    @Test
    void printChampionStats() {
        List<ChampionStatsDTO> stats = tournamentService.getChampionTitleStats();
        stats.forEach(System.out::println);
    }

    @Test
    void printRecentTournaments() {
        List<TournamentEntity> tournaments = tournamentService.getLast12Tournaments();
        tournaments.forEach(System.out::println);
    }

    @Test
    void printRanking() {
        List<Player> rankedPlayers = playerService.getRankedPlayers();
        rankedPlayers.forEach(player ->
                System.out.println(player.getRank() + ":" + player.getName() + " " + player.getPoints()));
    }

    @Test
    void simulateSingleTournament() {
        tournamentManageService.runAndSaveTournament(
                "Test Open",
                TournamentLevel.ELITE,
                new KnockOutFormat(),
                playerService.list());
        printRanking();
    }
}
