package com.celavin.badmintonepic.model.dto;

import com.celavin.badmintonepic.engine.tournament.Tournament;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.entity.Player;
import java.time.LocalDate;
//用于记录赛事完结后的结果,方便渲染和查询
public class TournamentVO {

    // 1. 赛事基础信息
    private String tournamentName;     // 赛事名称，如"伦敦公开赛"
    private TournamentLevel level;     // 级别，如 Major
    private LocalDate date;            // 举办年月

    // 2. 荣誉榜单 (核心提取，方便快速查询)
    private Player champion;           // 冠军
    private Player runnerUp;           // 亚军
    // 可选：private List<Player> top4; // 四强名单，方便给球员履历添加成就

    // 3. 完整赛事快照 (用于渲染历届赛事的对阵图)
    private MatchNode finalBracketRoot; // 树形结构的根节点(决赛节点)，包含了整届比赛的所有交手记录

    // 构造器 todo 这个构造方法参数不够全面,1.直接补全2.其他方式优化
    public TournamentVO(String tournamentName, TournamentLevel level, LocalDate date,
                        Player champion, Player runnerUp, MatchNode finalBracketRoot) {
        this.tournamentName = tournamentName;
        this.level = level;
        this.date = date;
        this.champion = champion;
        this.runnerUp = runnerUp;
        this.finalBracketRoot = finalBracketRoot;
    }

    public TournamentVO(Tournament tournament){

        tournamentName=tournament.getTournamentName();
        level=tournament.getLevel();
        //todo 先不管 后续时间系统完善后补完
        date=null;
        champion=tournament.getChampion();
        finalBracketRoot=tournament.getFormat().getFinalNode();
    }


    // Getter 和 Setter
}