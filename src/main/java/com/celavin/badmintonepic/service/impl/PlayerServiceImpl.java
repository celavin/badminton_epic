package com.celavin.badmintonepic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.celavin.badmintonepic.mapper.PlayerMapper;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.service.PlayerService;
import com.celavin.badmintonepic.util.PlayerGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl extends ServiceImpl<PlayerMapper, Player> implements PlayerService {
    @Override
    public void initWorld() {
        this.remove(null);

        // 生成 200 个球员
        List<Player> players = PlayerGenerator.generateBatch(200);

        // 批量插入
        this.saveBatch(players);
        System.out.println("成功初始化 200 名球员！");
    }
}
