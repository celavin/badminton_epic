package com.celavin.badmintonepic.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.celavin.badmintonepic.mapper.TournamentMapper;
import com.celavin.badmintonepic.model.dto.ChampionStatsDTO;
import com.celavin.badmintonepic.model.entity.TournamentEntity;
import com.celavin.badmintonepic.service.TournamentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentServiceImpl extends ServiceImpl<TournamentMapper,TournamentEntity> implements TournamentService  {
    @Override
    public List<ChampionStatsDTO> getChampionTitleStats() {
        return baseMapper.getChampionTitleStats();
    }

    @Override
    public void clearAllTournaments() {
        baseMapper.clearAll();
    }

    @Override
    public List<TournamentEntity> getLast12Tournaments() {
        return baseMapper.getLast12Tournaments();
    }

    @Override
    public List<TournamentEntity> getTournamentsByYearAndMonth(int year, int month) {
        return baseMapper.getTournamentsByYearAndMonth(year, month);
    }

    @Override
    public TournamentEntity getLatestTournamentWithBracket() {
        return baseMapper.getLatestTournamentWithBracket();
    }
}
