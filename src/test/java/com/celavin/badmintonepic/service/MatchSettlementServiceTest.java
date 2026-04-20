package com.celavin.badmintonepic.service;

import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.MatchResult;
import com.celavin.badmintonepic.model.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoInteractions;

class MatchSettlementServiceTest {

    MatchSettlementService service;
    PlayerService playerService;
    MatchRecordService matchRecordService;

    @BeforeEach
    void setUp() {
        service = new MatchSettlementService();
        playerService = mock(PlayerService.class);
        service.playerService = playerService;
        matchRecordService = mock(MatchRecordService.class);
        service.matchRecordService = matchRecordService;
    }

    @Test
    void settleSingleMatchSkipsByeMatches() {
        Player winner = player("Winner", 1200, 1200, 5);
        MatchResult byeResult = new MatchResult(winner, null, java.util.List.of(), TournamentLevel.RANKED, "Test", null, 0);

        service.settleSingleMatch(byeResult);

        assertEquals(1200, winner.getPoints());
        assertEquals(1200, winner.getHighestPoints());
        assertEquals(5, winner.getMorale());
        verifyNoInteractions(playerService);
        verifyNoInteractions(matchRecordService);
    }

    @Test
    void settleSingleMatchUsesStandardEloDeltaForEvenMatchup() {
        Player winner = player("Winner", 1200, 1200, 5);
        Player loser = player("Loser", 1200, 1200, 5);
        MatchResult result = new MatchResult(winner, loser, java.util.List.of(), TournamentLevel.RANKED, "Test", null, 3);

        service.settleSingleMatch(result);

        assertEquals(1210, winner.getPoints());
        assertEquals(1190, loser.getPoints());
        assertEquals(1210, winner.getHighestPoints());
        assertEquals(6, winner.getMorale());
        assertEquals(4, loser.getMorale());
        verify(playerService, times(1)).updateById(winner);
        verify(playerService, times(1)).updateById(loser);
        verify(matchRecordService, times(1)).recordFromMatchResult(any(), eq(MatchRecordService.KIND_TOURNAMENT));
    }

    @Test
    void settleSingleMatchAppliesTournamentAndRoundMultipliers() {
        Player winner = player("Winner", 1200, 1200, 9);
        Player loser = player("Loser", 1200, 1200, 1);
        MatchResult result = new MatchResult(winner, loser, java.util.List.of(), TournamentLevel.ELITE, "Elite Open", "决赛", 3);

        service.settleSingleMatch(result);

        assertEquals(1260, winner.getPoints());
        assertEquals(1140, loser.getPoints());
        assertEquals(1260, winner.getHighestPoints());
        assertEquals(10, winner.getMorale());
        assertEquals(0, loser.getMorale());
        verify(matchRecordService, times(1)).recordFromMatchResult(any(), eq(MatchRecordService.KIND_TOURNAMENT));
    }

    private Player player(String name, int points, int highestPoints, int morale) {
        Player player = new Player();
        player.setName(name);
        player.setPoints(points);
        player.setHighestPoints(highestPoints);
        player.setMorale(morale);
        return player;
    }
}
