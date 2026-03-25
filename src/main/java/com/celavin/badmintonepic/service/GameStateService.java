package com.celavin.badmintonepic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.celavin.badmintonepic.mapper.GameStateMapper;
import com.celavin.badmintonepic.model.entity.GameState;

public interface GameStateService extends IService<GameState> {
    //推进一天
    void advanceOneDay();
    //推进一个月
    void advanceOneMonth();
    //从表里加载当前时间到静态类
    //重要,每次开始前都得用这个,不然没有时间
    void loadGame();
}
