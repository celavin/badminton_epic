package com.celavin.badmintonepic.policy.impl;

import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.mapper.PlayerMapper;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.policy.PlayerAdmissionPolicy;
import com.celavin.badmintonepic.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PlayerAdmissionPolicyImpl implements PlayerAdmissionPolicy {
    @Autowired
    PlayerService playerService;
    @Override
    public  List<Player> selectPlayers(TournamentLevel level, int requiredNums) {
        //todo 实现准入具体逻辑
        return List.of();
    }
}
