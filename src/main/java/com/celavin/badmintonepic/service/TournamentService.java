package com.celavin.badmintonepic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.celavin.badmintonepic.model.dto.ChampionStatsDTO;
import com.celavin.badmintonepic.model.entity.TournamentEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TournamentService extends IService<TournamentEntity> {
    //查询每个人各级别冠军数量 todo 待增加亚军数量
    List<ChampionStatsDTO> getChampionTitleStats();

    //清空所有赛事并重置计数器
    void clearAllTournaments();
    //查看最新十二场赛事
    List<TournamentEntity> getLast12Tournaments();
}
