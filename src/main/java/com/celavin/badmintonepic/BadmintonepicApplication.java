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



	public static void main(String[] args) {
		SpringApplication.run(BadmintonepicApplication.class, args);
	}
	

}
