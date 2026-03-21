package com.celavin.badmintonepic;

import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
@SpringBootTest
public class generateTest {
    @Autowired
    PlayerService playerService;
    @Test
    void test(){

        List<Player> players = playerService.list();
        int i= 1;
        for (Player player : players) {
            System.out.println(i+":"+player.getId());
            i++;
        }
    }
    @Test
    void generateByCountry(){
        List<Player> players = playerService.initWorld(32);
        for (Player player : players) {
            System.out.println(player.getName());
        }
    }
}
