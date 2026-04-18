package com.celavin.badmintonepic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.celavin.badmintonepic.model.entity.Player;

import java.util.List;

public interface PlayerService extends IService<Player> {
    // 1. 初始化世界：清空数据库并随机生成一批球员
    void initPlayers();

    // 2. 临时生成球员（不入库），支持指定国籍（nationality 为 null 则随机）
    List<Player> generatePlayerTemp(int num, String nationality);

    // 3. 按指定国籍生成一批球员并直接存入数据库（用于后续补充特定国家的青训库）
    List<Player> generateAndSavePlayers(int num, String nationality);

    //4.按照排名返回球员列表
    //其他方法返回的player的rank是null
    List<Player> getRankedPlayers();
    //5.重置所有人积分到1200
    void resetAllPointsToDefault();

    //6.通过level和nums,获取特定人数的球员列表 干啥用的我也忘了
    List<Player> getPlayerListByLevelAndNums(int nums);
}
