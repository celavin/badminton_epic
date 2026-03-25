package com.celavin.badmintonepic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.celavin.badmintonepic.model.entity.GameState;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface GameStateMapper extends BaseMapper<GameState> {

}

