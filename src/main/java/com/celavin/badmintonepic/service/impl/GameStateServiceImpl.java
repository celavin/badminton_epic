package com.celavin.badmintonepic.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.celavin.badmintonepic.context.GameTimeContext;
import com.celavin.badmintonepic.mapper.GameStateMapper;
import com.celavin.badmintonepic.model.entity.GameState;
import com.celavin.badmintonepic.service.GameStateService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class GameStateServiceImpl extends ServiceImpl<GameStateMapper, GameState> implements GameStateService {
    @Override
    public void resetTime() {
        baseMapper.resetTime();
    }

    @Override
    public void advanceOneDay() {
        // 2. 从内存中极速获取对象引用
        GameState gameState = GameTimeContext.getCurrentState();
        // 兜底保护（虽然有 @PostConstruct，但为了极致健壮性可以保留）
        if (gameState == null) {
            loadGame();
            gameState = GameTimeContext.getCurrentState();
        }
        // 修改内存中的数据
        gameState.advanceOneDay();
        // 3. 落盘持久化：将当天的变化写入数据库
        saveOrUpdate(gameState);
        // 因为 gameState 是对象引用，修改它其实已经同步改变了 Context 中的值，
        // 再 set 一次主要为了代码语义的清晰
        GameTimeContext.setCurrentState(gameState);
    }

    @Override
    public void advanceOneMonth() {
        GameState gameState = GameTimeContext.getCurrentState();
        if (gameState == null) {
            loadGame();
            gameState = GameTimeContext.getCurrentState();
        }
        gameState.advanceOneMonth();
        saveOrUpdate(gameState);
        GameTimeContext.setCurrentState(gameState);
    }

    public void loadGame(){
        // 全局唯一的一处高频读库操作放在这里
        GameState state = getById(1);
        if (state == null) {
            // 如果数据库空了（比如被清库），自动创建一个初始存档
            state = new GameState();
            save(state);
        }
        GameTimeContext.setCurrentState(state);
    }
    @PostConstruct
    @Override
    public void init() {
        loadGame();
    }
}
