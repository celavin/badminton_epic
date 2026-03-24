package com.celavin.badmintonepic.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.celavin.badmintonepic.mapper.GameStateMapper;
import com.celavin.badmintonepic.model.entity.GameState;
import com.celavin.badmintonepic.service.GameStateService;
import org.springframework.stereotype.Service;

@Service
public class GameStateServiceImpl extends ServiceImpl<GameStateMapper, GameState> implements GameStateService {
    @Override
    public void advanceOneDay() {
        GameState gameState = getById(1);
        gameState.advanceOneDay();
        saveOrUpdate(gameState);
    }
}
