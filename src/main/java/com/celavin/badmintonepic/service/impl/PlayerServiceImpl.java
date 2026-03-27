package com.celavin.badmintonepic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.celavin.badmintonepic.mapper.PlayerMapper;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.service.PlayerService;
import com.celavin.badmintonepic.util.PlayerGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl extends ServiceImpl<PlayerMapper, Player> implements PlayerService {

    @Autowired
    private PlayerGenerator playerGenerator;

    @Override
    public List<Player> initWorld(int num) {
        this.remove(null); // 清空旧数据
        // 传入 null 代表国籍全随机
        List<Player> players = playerGenerator.generate(num, null);
        this.saveBatch(players);
        System.out.println("成功初始化 " + num + " 名随机球员！");
        return players;
    }

    @Override
    public List<Player> generatePlayerTemp(int num, String nationality) {
        List<Player> players = playerGenerator.generate(num, nationality);
        String type = (nationality == null) ? "随机国籍" : nationality;
        System.out.println("成功临时生成 " + num + " 名 " + type + " 球员！");
        return players;
    }

    @Override
    public List<Player> generateAndSavePlayers(int num, String nationality) {
        List<Player> players = playerGenerator.generate(num, nationality);
        this.saveBatch(players);
        System.out.println("成功生成并保存 " + num + " 名 " + nationality + " 球员！");
        return players;
    }

    @Override
    public List<Player> getRankedPlayers() {
        return baseMapper.getRankedPlayers();
    }
}