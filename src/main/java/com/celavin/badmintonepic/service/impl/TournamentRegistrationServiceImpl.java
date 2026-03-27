package com.celavin.badmintonepic.service.impl;

import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.TournamentScheduleEntry;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.service.TournamentRegistrationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Service
public class TournamentRegistrationServiceImpl implements TournamentRegistrationService {
    private final Random random;

    public TournamentRegistrationServiceImpl() {
        this(new Random());
    }

    TournamentRegistrationServiceImpl(Random random) {
        this.random = random;
    }

    @Override
    public List<Player> selectParticipants(TournamentScheduleEntry scheduleEntry, List<Player> allPlayers) {
        if (allPlayers == null || allPlayers.isEmpty()) {
            return List.of();
        }

        List<Player> rankedPlayers = allPlayers.stream()
                .sorted(Comparator.comparingInt(Player::getPoints).reversed()
                        .thenComparing(Player::getName))
                .toList();

        int drawSize = Math.min(scheduleEntry.getDrawSize(), rankedPlayers.size());
        if (drawSize <= 0) {
            return List.of();
        }

        RegistrationRule rule = registrationRuleFor(scheduleEntry.getLevel(), drawSize);
        List<Player> selected = new ArrayList<>();

        addTopPlayers(selected, rankedPlayers, rule.directAcceptances());

        List<Player> candidatePool = new ArrayList<>(rankedPlayers.subList(
                Math.min(rule.directAcceptances(), rankedPlayers.size()),
                Math.min(rule.directAcceptances() + rule.candidatePoolSize(), rankedPlayers.size())));

        while (selected.size() < drawSize && !candidatePool.isEmpty()) {
            selected.add(candidatePool.remove(random.nextInt(candidatePool.size())));
        }

        int fallbackIndex = rule.directAcceptances() + rule.candidatePoolSize();
        while (selected.size() < drawSize && fallbackIndex < rankedPlayers.size()) {
            Player fallbackPlayer = rankedPlayers.get(fallbackIndex++);
            if (!selected.contains(fallbackPlayer)) {
                selected.add(fallbackPlayer);
            }
        }

        return selected;
    }

    private void addTopPlayers(List<Player> selected, List<Player> rankedPlayers, int count) {
        for (int index = 0; index < Math.min(count, rankedPlayers.size()); index++) {
            selected.add(rankedPlayers.get(index));
        }
    }

    private RegistrationRule registrationRuleFor(TournamentLevel level, int drawSize) {
        return switch (level) {
            case MAJOR -> new RegistrationRule(Math.min(4, drawSize), Math.max(0, drawSize));
            case ELITE -> new RegistrationRule(Math.min(8, drawSize), Math.max(0, drawSize));
            default -> new RegistrationRule(Math.min(4, drawSize / 2), Math.max(drawSize, drawSize / 2));
        };
    }

    private record RegistrationRule(int directAcceptances, int candidatePoolSize) {
    }
}
