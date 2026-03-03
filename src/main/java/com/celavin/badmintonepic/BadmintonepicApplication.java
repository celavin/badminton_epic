package com.celavin.badmintonepic;

import com.celavin.badmintonepic.engine.simulator.MatchEngine;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.service.PlayerService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
// 请确保括号内的路径与你 PlayerMapper 所在的包名完全一致
@MapperScan("com.celavin.badmintonepic.mapper")
public class BadmintonepicApplication {
	//@Autowired
	//private static PlayerService playerService;


	public static void main(String[] args) {
		//playerService.initWorld();
		SpringApplication.run(BadmintonepicApplication.class, args);
	}
	/**
	 * 启动后自动运行
	 */
	@Bean
	public CommandLineRunner runMatch(PlayerService playerService, MatchEngine matchEngine) {
		return args -> {
			// 从数据库随机选两个球员
			List<Player> allPlayers = playerService.list();
			if (allPlayers.size() >= 2) {

				// 模拟 5 场对决，看看会不会爆冷
				for(int i=0; i<5; i++) {
					Player p1 = allPlayers.get(0);
					Player p2 = allPlayers.get(i+1);
					matchEngine.simulate(p1, p2,3);
				}
			}
		};
	}

}
