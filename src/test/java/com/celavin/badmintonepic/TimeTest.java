package com.celavin.badmintonepic;

import com.celavin.badmintonepic.model.entity.GameState;
import com.celavin.badmintonepic.service.GameStateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TimeTest {
    @Autowired
    GameStateService gameStateService;
    @Test
    void test(){
        GameState gameState = gameStateService.getById(1);
        gameState.advanceOneDay();
        gameStateService.saveOrUpdate(gameState);
        System.out.println(gameStateService.getById(1));
    }
    @Test
    void test2(){

    }
}
