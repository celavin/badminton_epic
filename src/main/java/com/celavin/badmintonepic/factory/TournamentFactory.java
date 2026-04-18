package com.celavin.badmintonepic.factory;

import com.celavin.badmintonepic.context.GameTimeContext;
import com.celavin.badmintonepic.engine.tournament.Tournament;
import com.celavin.badmintonepic.engine.tournament.format.TournamentFormat;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.util.TournamentNameGenerator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class TournamentFactory {

    Tournament TournamentFactory(TournamentLevel level, TournamentFormat format,int requiredPlayerNums) {
        Tournament t = new Tournament();
        List<Player> playerList = new ArrayList<>();
        t.setTournamentName(TournamentNameGenerator.generateRandomName());
        t.setLevel(level);
        t.setFormat(format);
        t.setPlayerNums(requiredPlayerNums);
        t.setYear(GameTimeContext.getCurrentYear());
        t.setMonth(GameTimeContext.getCurrentMonth());
        return t;
    }
    Tournament TournamentFactory(TournamentLevel level, TournamentFormat format,int requiredPlayerNums,int year,int month) {
        Tournament t = new Tournament();
        List<Player> playerList = new ArrayList<>();
        t.setTournamentName(TournamentNameGenerator.generateRandomName());
        t.setLevel(level);
        t.setFormat(format);
        t.setPlayerNums(requiredPlayerNums);
        t.setYear(year);
        t.setMonth(month);
        return t;
    }
}
