package com.celavin.badmintonepic.engine.simulator;

import com.celavin.badmintonepic.model.dto.GameScore;
import com.celavin.badmintonepic.model.entity.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GameEngineTest {

    private final GameEngine gameEngine = new GameEngine();

    @Test
    void simulateGameAlwaysProducesValidBadmintonScore() {
        Player player1 = buildPlayer(12, 12, 12, 12);
        Player player2 = buildPlayer(11, 11, 11, 11);

        for (int i = 0; i < 200; i++) {
            GameScore score = gameEngine.simulateGame(player1, player2);

            int p1 = score.getP1Score();
            int p2 = score.getP2Score();
            int winner = Math.max(p1, p2);
            int loser = Math.min(p1, p2);

            assertTrue(winner >= 21, "winner should reach at least 21 points");
            assertTrue(winner <= 30, "winner should never exceed 30 points");
            assertTrue(loser >= 0, "loser score should never be negative");
            assertTrue(
                    winner == 30 || winner - loser >= 2,
                    "games should end with a 2-point margin unless capped at 30");
        }
    }

    private Player buildPlayer(int power, int skill, int stamina, int speed) {
        Player player = new Player();
        player.setPower(power);
        player.setSkill(skill);
        player.setStamina(stamina);
        player.setSpeed(speed);
        return player;
    }
}
