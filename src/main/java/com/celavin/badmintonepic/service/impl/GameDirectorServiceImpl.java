package com.celavin.badmintonepic.service.impl;

import com.celavin.badmintonepic.calendar.GameConstants;
import com.celavin.badmintonepic.calendar.ScheduleCalendar;
import com.celavin.badmintonepic.config.CalendarTemplateConfig;
import com.celavin.badmintonepic.context.GameTimeContext;
import com.celavin.badmintonepic.engine.simulator.MatchEngine;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.enums.TournamentStatus;
import com.celavin.badmintonepic.model.dto.MatchResult;
import com.celavin.badmintonepic.model.dto.RawMatchResult;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.model.entity.TournamentEntity;
import com.celavin.badmintonepic.policy.PlayerAdmissionPolicy;
import com.celavin.badmintonepic.service.GameDirectorService;
import com.celavin.badmintonepic.service.GameStateService;
import com.celavin.badmintonepic.service.MatchRecordService;
import com.celavin.badmintonepic.service.MatchSettlementService;
import com.celavin.badmintonepic.service.PlayerService;
import com.celavin.badmintonepic.service.TournamentManageService;
import com.celavin.badmintonepic.service.TournamentService;
import com.celavin.badmintonepic.model.dto.SimulateDayResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class GameDirectorServiceImpl implements GameDirectorService {

    @Autowired
    private GameStateService gameStateService;
    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private TournamentManageService tournamentManageService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerAdmissionPolicy playerAdmissionPolicy;
    @Autowired
    private CalendarTemplateConfig calendarTemplateConfig;
    @Autowired
    private MatchEngine matchEngine;
    @Autowired
    private MatchSettlementService matchSettlementService;

    @Override
    public SimulateDayResultDTO simulateOneDay() {
        gameStateService.loadGame();
        SimulateDayResultDTO out = new SimulateDayResultDTO();
        int year = GameTimeContext.getCurrentYear();
        int month = GameTimeContext.getCurrentMonth();
        int week = GameTimeContext.getCurrentWeek();
        int day = GameTimeContext.getCurrentDayOfWeek();
        out.setYear(year);
        out.setMonth(month);
        out.setWeek(week);
        out.setDayOfWeek(day);

        tournamentManageService.generateYearlyCalendar();

        if (ScheduleCalendar.isRankedWeek(week)) {
            out.setPhaseDescription("排位周");
            out.getMessages().addAll(simulateRankedDay());
        } else if (ScheduleCalendar.isTournamentWeek(week)) {
            out.setPhaseDescription("赛事周");
            if (week == 3 && day == 1) {
                out.getMessages().addAll(runTournamentWeekForCurrentMonth());
            } else {
                out.getMessages().add("赛事周：本场锦标赛已在本周第1天完成模拟（第一版规则）。");
            }
        } else {
            out.setPhaseDescription("未知阶段");
        }

        playerService.refreshBestRanks();
        gameStateService.advanceOneDay();
        return out;
    }

    private List<String> simulateRankedDay() {
        List<String> lines = new ArrayList<>();
        List<Player> all = new ArrayList<>(playerService.getRankedPlayers());
        Collections.shuffle(all, ThreadLocalRandom.current());
        int pairs = Math.min(GameConstants.RANKED_MATCHES_PER_DAY, all.size() / 2);
        for (int i = 0; i < pairs; i++) {
            Player a = all.get(i * 2);
            Player b = all.get(i * 2 + 1);
            RawMatchResult raw = matchEngine.simulate(a, b, 3);
            if (raw.getLoser() == null) {
                continue;
            }
            MatchResult mr = new MatchResult(
                    raw.getWinner(),
                    raw.getLoser(),
                    raw.getScores(),
                    TournamentLevel.RANKED,
                    "Daily Ranked",
                    "排位赛",
                    raw.getBestOf() > 0 ? raw.getBestOf() : 3
            );
            matchSettlementService.settleSingleMatch(mr, MatchRecordService.KIND_RANKED);
            lines.add(raw.getWinner().getName() + " 胜 " + raw.getLoser().getName() + "（排位）");
        }
        if (lines.isEmpty()) {
            lines.add("排位赛：球员不足，跳过。");
        }
        return lines;
    }

    private List<String> runTournamentWeekForCurrentMonth() {
        List<String> lines = new ArrayList<>();
        int year = GameTimeContext.getCurrentYear();
        int month = GameTimeContext.getCurrentMonth();
        List<TournamentLevel> template = calendarTemplateConfig.getLevelsForMonth(month);
        if (template == null || template.isEmpty()) {
            lines.add("当月无锦标赛配置。");
            return lines;
        }
        List<Player> ranked = playerService.getRankedPlayers();
        for (int i = 0; i < template.size(); i++) {
            TournamentLevel level = template.get(i);
            TournamentEntity row = tournamentService.lambdaQuery()
                    .eq(TournamentEntity::getYear, year)
                    .eq(TournamentEntity::getMonth, month)
                    .eq(TournamentEntity::getLevel, level)
                    .eq(TournamentEntity::getStatus, TournamentStatus.SCHEDULED)
                    .one();
            if (row == null) {
                lines.add("未找到 SCHEDULED 赛事: " + level + "（可能已开打或配置不一致）");
                continue;
            }
            boolean highTrack = (i == 0);
            List<Player> pool = playerAdmissionPolicy.pickTrack(ranked, highTrack, GameConstants.KNOCKOUT_FIELD_SIZE);
            if (pool.size() < 2) {
                lines.add("赛事 " + row.getName() + " 人数不足，跳过。");
                continue;
            }
            tournamentManageService.runScheduledTournamentToCompletion(row, pool);
            lines.add("完成锦标赛: " + row.getName() + " (" + level + ")");
        }
        return lines;
    }
}
