package com.celavin.badmintonepic.model.entity;


import com.baomidou.mybatisplus.annotation.TableName;

@TableName("players")
public class Player {
    private Long id;
    private String name;
    private int age;
    private String nationality; // 国籍

    // 六大核心属性 (1-20)
    private int power =10;   // 力量
    private int speed =10;   // 速度
    private int skill =10;     //技术
    private int tactics =10;     // 战术
    private int stamina =10;   // 体能
    private int mental =10;    // 心态

    // 动态状态
    private int morale = 5;// 士气 (0-10, 初始5)

    private int points = 1200;//default
    private int highestPoints = 1200;//default(从第二年开始,每年第一月第一天刷新)
}
