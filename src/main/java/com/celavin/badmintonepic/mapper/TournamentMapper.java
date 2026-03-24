package com.celavin.badmintonepic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.celavin.badmintonepic.model.dto.ChampionStatsDTO;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.model.entity.TournamentEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TournamentMapper  extends BaseMapper<TournamentEntity> {
    @Select("select champion_name name,\n" +
            "count(*)filter(where level='MAJOR') major,\n" +
            "count(*)filter(where level='ELITE') elite,\n" +
            "count(*)filter(where level='CHALLENGE') challenge\n" +
            "from tournaments\n" +
            "Group by champion_name\n" +
            "order by major desc,elite desc,challenge desc;")
    List<ChampionStatsDTO> getChampionTitleStats();
    @Delete("truncate table tournaments restart identity;")
    void clearAll();

    @Select("select*\n"+
            "from tournaments\n"+
            "order by id desc\n"+
            "limit 12")
    List<TournamentEntity> getLast12Tournaments();
}
