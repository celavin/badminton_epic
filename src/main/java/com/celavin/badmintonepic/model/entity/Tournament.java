package com.celavin.badmintonepic.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.celavin.badmintonepic.enums.TournamentLevel;
import com.celavin.badmintonepic.model.dto.MatchNode;

import java.util.Date;

@TableName("tournaments" )
public class Tournament {
    private String id;
    private String name;
    private TournamentLevel level;//todo pgsql怎么存这个
    private MatchNode finalNode;
    private Player champion;
    private Date date;//todo 后续需要一个封装类表述游戏内时间


}
