package com.celavin.badmintonepic;

import com.celavin.badmintonepic.model.entity.GameState;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.service.GameStateService;
import com.celavin.badmintonepic.service.PlayerService;
import com.celavin.badmintonepic.service.TournamentManageService;
import com.celavin.badmintonepic.service.TournamentService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BadmintonepicApplicationTests {

	@Autowired
	private PlayerService playerService;
	@Autowired
	private TournamentManageService tournamentManageService;
	@Autowired
	private TournamentService tournamentService;
	@Autowired
	GameStateService gameStateService;

	@Test
	void test() {
		tournamentService.remove(null);
		playerService.remove(null);
		playerService.generateAndSavePlayers(8,"中国");
		playerService.generateAndSavePlayers(8,"中国台北");
		playerService.generateAndSavePlayers(8,"日本");
		playerService.generateAndSavePlayers(8,"英格兰");

	}
	@Test
	//清空赛事数据,重置所有人积分, 重置时间 后续考虑集成成方法
	void test2() {
		tournamentService.clearAllTournaments();
		playerService.resetAllPointsToDefault();
		gameStateService.resetTime();
		//todo 重置时间

	}


}
