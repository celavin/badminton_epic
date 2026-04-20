package com.celavin.badmintonepic.web;

import com.celavin.badmintonepic.service.GameDirectorService;
import com.celavin.badmintonepic.model.dto.SimulateDayResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")
public class GameApiController {

    @Autowired
    private GameDirectorService gameDirectorService;

    /**
     * 主玩法：模拟当前游戏日（排位/赛事逻辑），然后推进一天。
     */
    @PostMapping("/simulate-day")
    public SimulateDayResultDTO simulateDay() {
        return gameDirectorService.simulateOneDay();
    }
}
