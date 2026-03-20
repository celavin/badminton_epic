package com.celavin.badmintonepic.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.celavin.badmintonepic.enums.TournamentLevel;

@TableName("tournaments" )
public class Tournament {
    private String name;
    private TournamentLevel level;//todo pgsql怎么存这个
}
