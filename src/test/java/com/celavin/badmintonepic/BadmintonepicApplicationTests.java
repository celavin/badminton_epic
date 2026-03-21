package com.celavin.badmintonepic;

import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BadmintonepicApplicationTests {

	@Autowired
	private PlayerService playerService;

	@Test
	void test() {
		playerService.initWorld(32);
	}


}
