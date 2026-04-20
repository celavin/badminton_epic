package com.celavin.badmintonepic.service;

import com.celavin.badmintonepic.model.dto.SimulateDayResultDTO;

/**
 * 按游戏内日期调度：排位周 / 赛事周、「模拟一天」入口。
 */
public interface GameDirectorService {

    /**
     * 处理当前游戏日（排位或赛事），然后推进一天。
     */
    SimulateDayResultDTO simulateOneDay();
}
