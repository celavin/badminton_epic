package com.celavin.badmintonepic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.celavin.badmintonepic.model.entity.GameState;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface GameStateMapper extends BaseMapper<GameState> {
    @Update("update game_state set year=2026,month=1,week=1,day=1")
    void resetTime();
}

