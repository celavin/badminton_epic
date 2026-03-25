package com.celavin.badmintonepic.service;

import com.celavin.badmintonepic.engine.simulator.GameEngine;
import com.celavin.badmintonepic.engine.simulator.MatchEngine;
import com.celavin.badmintonepic.engine.tournament.Tournament;
import com.celavin.badmintonepic.engine.tournament.format.KnockOutFormat;
import com.celavin.badmintonepic.engine.tournament.format.TournamentFormat;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.model.entity.TournamentEntity;
import com.celavin.badmintonepic.util.TournamentNameGenerator;
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
    @Autowired
    GameStateService gameStateService;

    /**
     * 执行一个赛事并保存
     * @param name
     * @param level
     * @param format
     * @param players
     */
     public TournamentEntity runAndSaveTournament(String name, TournamentLevel level, TournamentFormat format, List<Player> players){
        Tournament tournament = new Tournament(name, level, format, players);
        tournament.simulateAll(matchEngine,matchSettlementService);
        TournamentEntity tournamentEntity = new TournamentEntity(tournament);
        tournamentService.save(tournamentEntity);
        return tournamentEntity;
    }

    /**
     * 执行一个赛事但不保存
     * @param name
     * @param level
     * @param format
     * @param players
     * @return
     */
    public TournamentEntity runTournament(String name, TournamentLevel level, TournamentFormat format, List<Player> players){
        Tournament tournament = new Tournament(name, level, format, players);
        tournament.simulateAll(matchEngine,matchSettlementService);

        return new TournamentEntity(tournament);
    }
    public void simulateWholeYear(List<Player> players){
        //todo 需要一个推进一个月的方法
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                runAndSaveTournament(TournamentNameGenerator.generateRandomName(),
                        TournamentLevel.CHALLENGE, new KnockOutFormat(), players);
                gameStateService.advanceOneMonth();
            }
            runAndSaveTournament(TournamentNameGenerator.generateRandomName(),
                    TournamentLevel.ELITE, new KnockOutFormat(), players);
            gameStateService.advanceOneMonth();
            runAndSaveTournament(TournamentNameGenerator.generateRandomName(),
                    TournamentLevel.MAJOR, new KnockOutFormat(), players);
            gameStateService.advanceOneMonth();
        }


    }


}
