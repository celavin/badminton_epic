package com.celavin.badmintonepic.service;

import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.MatchNode;
import com.celavin.badmintonepic.model.dto.MatchResult;
import com.celavin.badmintonepic.model.entity.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchSettlementService {
    //结算单场比赛
    public void settleSingleMatch(MatchResult matchResult) {
        //todo 这里需要 胜者败者 level,roundname

    }


}
