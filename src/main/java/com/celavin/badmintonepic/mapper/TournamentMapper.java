package com.celavin.badmintonepic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.model.entity.TournamentEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TournamentMapper  extends BaseMapper<TournamentEntity> {
}
