package com.celavin.badmintonepic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.celavin.badmintonepic.context.GameTimeContext;
import com.celavin.badmintonepic.mapper.MatchRecordMapper;
import com.celavin.badmintonepic.model.dto.MatchResult;
import com.celavin.badmintonepic.model.entity.MatchRecord;
import com.celavin.badmintonepic.service.MatchRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchRecordServiceImpl extends ServiceImpl<MatchRecordMapper, MatchRecord> implements MatchRecordService {

    @Override
    public void recordFromMatchResult(MatchResult result, String matchKind) {
        if (result.getLoser() == null) {
            return;
        }
        MatchRecord row = new MatchRecord();
        row.setWinnerId(result.getWinner().getId());
        row.setLoserId(result.getLoser().getId());
        row.setLevel(result.getLevel() != null ? result.getLevel().name() : "UNKNOWN");
        row.setTournamentName(result.getTournamentName());
        row.setGameYear(GameTimeContext.getCurrentYear());
        row.setGameMonth(GameTimeContext.getCurrentMonth());
        row.setGameWeek(GameTimeContext.getCurrentWeek());
        row.setGameDay(GameTimeContext.getCurrentDayOfWeek());
        row.setMatchKind(matchKind);
        save(row);
    }

    @Override
    public List<MatchRecord> findRecentForPlayer(Long playerId, int limit) {
        return baseMapper.findRecentForPlayer(playerId, limit);
    }
}
