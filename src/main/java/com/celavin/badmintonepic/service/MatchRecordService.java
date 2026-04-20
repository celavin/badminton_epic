package com.celavin.badmintonepic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.celavin.badmintonepic.model.dto.MatchResult;
import com.celavin.badmintonepic.model.entity.MatchRecord;

import java.util.List;

public interface MatchRecordService extends IService<MatchRecord> {

    String KIND_RANKED = "RANKED";
    String KIND_TOURNAMENT = "TOURNAMENT";

    void recordFromMatchResult(MatchResult result, String matchKind);

    List<MatchRecord> findRecentForPlayer(Long playerId, int limit);
}
