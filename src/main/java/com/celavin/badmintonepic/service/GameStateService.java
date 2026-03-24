package com.celavin.badmintonepic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.celavin.badmintonepic.mapper.GameStateMapper;
import com.celavin.badmintonepic.model.entity.GameState;

public interface GameStateService extends IService<GameState> {
    //推进一天
    void advanceOneDay();
}
