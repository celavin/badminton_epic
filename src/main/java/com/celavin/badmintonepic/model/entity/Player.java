package com.celavin.badmintonepic.model.entity;


import com.baomidou.mybatisplus.annotation.TableName;

@TableName("players")
public class Player {
    private Long id;
    private String name;
    private int age;
    private String nationality; // 国籍

    // 六大核心属性 (1-100)
    private int offense =10;   // 进攻
    private int defense =10;   // 防守
    private int skill =10;     // 技术
    private int speed =10;     // 速度
    private int stamina =10;   // 体能
    private int mental =10;    // 精神

    // 动态状态
    private int morale = 5; // 士气 (0-10, 初始5)


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getOffense() {
        return offense;
    }

    public void setOffense(int offense) {
        this.offense = offense;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getMental() {
        return mental;
    }

    public void setMental(int mental) {
        this.mental = mental;
    }

    public int getMorale() {
        return morale;
    }

    public void setMorale(int morale) {
        this.morale = morale;
    }
}
