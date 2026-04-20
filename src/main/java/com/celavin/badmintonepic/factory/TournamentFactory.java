package com.celavin.badmintonepic.factory;

import com.celavin.badmintonepic.context.GameTimeContext;
import com.celavin.badmintonepic.engine.tournament.Tournament;
import com.celavin.badmintonepic.engine.tournament.format.KnockOutFormat;
import com.celavin.badmintonepic.engine.tournament.format.TournamentFormat;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.enums.TournamentStatus;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.model.entity.TournamentEntity;
import com.celavin.badmintonepic.util.BracketPlayerCollector;
import com.celavin.badmintonepic.util.TournamentNameGenerator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class TournamentFactory {

    /**
     * 从库里读出的 {@code final_node}（整棵签表根）恢复可继续打的单败淘汰赛实例。
     */
    public Tournament rehydrateKnockOutFromEntity(TournamentEntity e) {
        if (e == null || e.getFinalNode() == null) {
            throw new IllegalArgumentException("需要 final_node（签表根）才能恢复赛事");
        }
        KnockOutFormat format = new KnockOutFormat();
        format.restoreFromRoot(e.getFinalNode());
        Tournament t = new Tournament();
        t.setTournamentName(e.getName());
        t.setLevel(e.getLevel());
        t.setFormat(format);
        t.setPlayerList(BracketPlayerCollector.collectFromRoot(e.getFinalNode()));
        t.setYear(e.getYear());
        t.setMonth(e.getMonth());
        t.setStatus(e.getStatus() != null ? e.getStatus() : TournamentStatus.ONGOING);
        if (e.getStatus() == TournamentStatus.COMPLETED) {
            t.setChampion(format.getChampion());
            t.setRunnerUp(format.getRunnerUp());
        }
        return t;
    }

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
