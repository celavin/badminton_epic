package com.celavin.badmintonepic.service;

import com.celavin.badmintonepic.engine.simulator.MatchEngine;
import com.celavin.badmintonepic.engine.tournament.Tournament;
import com.celavin.badmintonepic.engine.tournament.format.KnockOutFormat;
import com.celavin.badmintonepic.engine.tournament.format.TournamentFormat;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.dto.MatchResult;
import com.celavin.badmintonepic.model.dto.RawMatchResult;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.model.entity.TournamentEntity;
import com.celavin.badmintonepic.util.TournamentNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface TournamentManageService {


    void simulateNextStep(Tournament tournament);



    /**
     * 一键模拟整届赛事
     * 测试用
     */
    void simulateAll(Tournament tournament) ;

    /**
     * 原有的 runAndSaveTournament 方法稍微改动一下
     */
    TournamentEntity runAndSaveTournament(String name, TournamentLevel level, TournamentFormat format, List<Player> players);


    /**
     * 执行一个赛事但不保存,返回实体类
     * @param name
     * @param level
     * @param format
     * @param players
     * @return
     */
    TournamentEntity runTournament(String name, TournamentLevel level, TournamentFormat format, List<Player> players);
    /**
     * 模拟一整年,测试用
     * */
    void simulateWholeYear(List<Player> players);
}
