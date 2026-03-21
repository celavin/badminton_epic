package com.celavin.badmintonepic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.celavin.badmintonepic.model.entity.Player;

import java.util.List;
//todo 待解耦
public interface PlayerService extends IService<Player> {
    // 1. 初始化世界：清空数据库并随机生成一批球员
    List<Player> initWorld(int num);

    // 2. 临时生成球员（不入库），支持指定国籍（nationality 为 null 则随机）
    List<Player> generatePlayerTemp(int num, String nationality);

    // 3. 按指定国籍生成一批球员并直接存入数据库（用于后续补充特定国家的青训库）
    List<Player> generateAndSavePlayers(int num, String nationality);
}
