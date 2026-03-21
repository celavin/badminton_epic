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
    //从库里面取出
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
        List<Player> players = playerService.generateAndSavePlayers(32,"中国");
        for (Player player : players) {
            System.out.println(player.getName());
        }

    }
    @Test
    void generateByHand(){
        Player p = new Player();
        p.setId(999L);
        p.setAge(18);
        p.setNationality("中国");
        p.setName("张三");
        playerService.save(p);

    }
}
