package com.celavin.badmintonepic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.celavin.badmintonepic.model.entity.Player;

import java.util.List;

public interface PlayerService extends IService<Player> {
    List<Player> initWorld(int num);
    List<Player> generatePlayerTemp(int num);
}
