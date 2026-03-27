package com.celavin.badmintonepic.engine.tournament.format;

import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.entity.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KnockOutFormatTest {

    @Test
    void initBracketCreatesOnePlayableMatchPerLeafForPowerOfTwoField() {
        KnockOutFormat format = new KnockOutFormat();

        format.initBracket(players(4));

        assertEquals(3, format.getAllMatches().size());
        assertEquals(2, format.getPlayableMatches().size());
        assertTrue(format.getPlayableMatches().stream().allMatch(MatchNode::isPlayable));
    }

    @Test
    void initBracketAutoAdvancesByePlayers() {
        KnockOutFormat format = new KnockOutFormat();

        format.initBracket(players(3));

        List<MatchNode> playableMatches = format.getPlayableMatches();

        assertEquals(1, playableMatches.size());
        assertNotNull(format.getFinalNode());
        assertTrue(
                format.getFinalNode().getP1() != null || format.getFinalNode().getP2() != null,
                "the final should already contain the player who advanced with a bye");
    }

    private List<Player> players(int count) {
        return java.util.stream.IntStream.range(0, count)
                .mapToObj(index -> {
                    Player player = new Player();
                    player.setId((long) index + 1);
                    player.setName("Player-" + (index + 1));
                    return player;
                })
                .collect(java.util.stream.Collectors.toCollection(java.util.ArrayList::new));
    }
}
