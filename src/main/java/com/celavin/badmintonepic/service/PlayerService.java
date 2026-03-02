package com.celavin.badmintonepic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.celavin.badmintonepic.model.entity.Player;

public interface PlayerService extends IService<Player> {
    void initWorld();
}
