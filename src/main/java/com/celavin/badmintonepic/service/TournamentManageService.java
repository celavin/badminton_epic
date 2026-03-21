package com.celavin.badmintonepic.service;

import com.celavin.badmintonepic.engine.simulator.GameEngine;
import com.celavin.badmintonepic.engine.simulator.MatchEngine;
import com.celavin.badmintonepic.engine.tournament.Tournament;
import com.celavin.badmintonepic.engine.tournament.format.TournamentFormat;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.entity.Player;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//todo 完善赛事管理servcie
@Service
public class TournamentManageService {
    @Autowired
    GameEngine gameEngine;
    @Autowired
    MatchEngine matchEngine;
    @Autowired
    MatchSettlementService matchSettlementService;
    @Autowired
    TournamentService tournamentService;

    /**
     * 执行一个赛事并保存
     * @param name
     * @param level
     * @param format
     * @param players
     */
    void runTournament(String name, TournamentLevel level, TournamentFormat format, List<Player> players){
        Tournament tournament = new Tournament(name, level, format, players);
        tournament.simulateAll(matchEngine,matchSettlementService);

    }


}
