package com.celavin.badmintonepic.web;

import com.celavin.badmintonepic.calendar.GameConstants;
import com.celavin.badmintonepic.model.entity.MatchRecord;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.service.MatchRecordService;
import com.celavin.badmintonepic.service.PlayerService;
import com.celavin.badmintonepic.util.PlayerRecordStats;
import com.celavin.badmintonepic.web.dto.PlayerDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "*")
public class PlayerApiController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private MatchRecordService matchRecordService;

    @GetMapping("/leaderboard")
    public List<Player> leaderboard(@RequestParam(defaultValue = "200") int limit) {
        int cap = Math.min(Math.max(limit, 1), GameConstants.LEADERBOARD_LIMIT);
        return playerService.getLeaderboard(cap);
    }

    @GetMapping("/{id}")
    public PlayerDetailDTO detail(@PathVariable Long id) {
        Player p = playerService.getById(id);
        if (p == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "球员不存在");
        }
        List<Player> ranked = playerService.getRankedPlayers();
        Integer rank = null;
        for (int i = 0; i < ranked.size(); i++) {
            if (id.equals(ranked.get(i).getId())) {
                rank = i + 1;
                break;
            }
        }
        List<MatchRecord> recent = matchRecordService.findRecentForPlayer(id, 50);
        int streak = PlayerRecordStats.currentStreak(recent, id);

        PlayerDetailDTO dto = new PlayerDetailDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setAge(p.getAge());
        dto.setNationality(p.getNationality());
        dto.setRank(rank);
        dto.setPoints(p.getPoints());
        dto.setHighestPoints(p.getHighestPoints());
        dto.setBestRank(p.getBestRank());
        dto.setMorale(p.getMorale());
        dto.setPower(p.getPower());
        dto.setSpeed(p.getSpeed());
        dto.setSkill(p.getSkill());
        dto.setTactics(p.getTactics());
        dto.setStamina(p.getStamina());
        dto.setMental(p.getMental());
        dto.setLastTenRecord(PlayerRecordStats.lastNWinLoss(recent, id, 10));
        dto.setStreak(streak);
        dto.setStreakDescription(streakDescription(streak));
        return dto;
    }

    private static String streakDescription(int streak) {
        if (streak == 0) {
            return "无连胜/连败记录";
        }
        if (streak > 0) {
            return streak + "连胜";
        }
        return (-streak) + "连败";
    }
}
