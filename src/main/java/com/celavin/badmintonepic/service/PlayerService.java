package com.celavin.badmintonepic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.celavin.badmintonepic.model.entity.Player;

import java.util.List;
//todo 待解耦
public interface PlayerService extends IService<Player> {
    //重置数据库?
    List<Player> initWorld(int num);
    //临时生成一批球员
    List<Player> generatePlayerTemp(int num);
}
