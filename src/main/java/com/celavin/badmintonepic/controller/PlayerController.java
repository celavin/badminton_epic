package com.celavin.badmintonepic.controller;

import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/ranking")
    public List<Player> ranking(@RequestParam(defaultValue = "200") int limit) {
        int safeLimit = Math.max(1, Math.min(limit, 500));
        List<Player> ranked = playerService.getRankedPlayers();
        if (ranked.size() <= safeLimit) {
            return ranked;
        }
        return ranked.subList(0, safeLimit);
    }

    @GetMapping("/{id}")
    public Player getById(@PathVariable Long id) {
        Player player = playerService.getById(id);
        if (player == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
        }
        return player;
    }
}

