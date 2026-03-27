package com.celavin.badmintonepic.service;

import com.celavin.badmintonepic.model.dto.TournamentScheduleEntry;
import com.celavin.badmintonepic.model.entity.Player;

import java.util.List;

public interface TournamentRegistrationService {
    List<Player> selectParticipants(TournamentScheduleEntry scheduleEntry, List<Player> allPlayers);
}
