package com.celavin.badmintonepic.service.impl;

import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.TournamentScheduleEntry;
import com.celavin.badmintonepic.model.entity.Player;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TournamentRegistrationServiceImplTest {

    private final TournamentRegistrationServiceImpl service =
            new TournamentRegistrationServiceImpl(new Random(7));

    @Test
    void majorEventsAlwaysIncludeTopSeedsAndRespectDrawSize() {
        TournamentScheduleEntry event = new TournamentScheduleEntry(2026, 6, "Major", TournamentLevel.MAJOR, 8);

        List<Player> participants = service.selectParticipants(event, players(20));

        assertEquals(8, participants.size());
        assertTrue(participants.stream().anyMatch(player -> "Player-1".equals(player.getName())));
        assertTrue(participants.stream().anyMatch(player -> "Player-2".equals(player.getName())));
        assertTrue(participants.stream().anyMatch(player -> "Player-3".equals(player.getName())));
        assertTrue(participants.stream().anyMatch(player -> "Player-4".equals(player.getName())));
    }

    @Test
    void challengeEventsCanFillWithLowerRankedPlayersWhenDrawIsLargerThanGuaranteedSpots() {
        TournamentScheduleEntry event = new TournamentScheduleEntry(2026, 1, "Challenge", TournamentLevel.CHALLENGE, 16);

        List<Player> participants = service.selectParticipants(event, players(24));

        assertEquals(16, participants.size());
        assertTrue(participants.stream().anyMatch(player -> "Player-1".equals(player.getName())));
        assertTrue(participants.stream().map(Player::getName).distinct().count() == participants.size());
    }

    @Test
    void selectParticipantsFallsBackToAvailablePlayersWhenPoolIsSmall() {
        TournamentScheduleEntry event = new TournamentScheduleEntry(2026, 11, "Elite", TournamentLevel.ELITE, 16);

        List<Player> participants = service.selectParticipants(event, players(6));

        assertEquals(6, participants.size());
    }

    private List<Player> players(int count) {
        return IntStream.rangeClosed(1, count)
                .mapToObj(index -> {
                    Player player = new Player();
                    player.setId((long) index);
                    player.setName("Player-" + index);
                    player.setPoints(2000 - (index * 10));
                    return player;
                })
                .toList();
    }
}
