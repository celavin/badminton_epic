package com.celavin.badmintonepic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.celavin.badmintonepic.model.entity.Player;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlayerMapper extends BaseMapper<Player> {
}
