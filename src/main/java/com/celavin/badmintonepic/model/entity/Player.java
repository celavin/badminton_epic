package com.celavin.badmintonepic.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("players")
public class Player {
    @TableId(type = IdType.AUTO)
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

    /** 历史最佳排名（数值越小越好），榜单刷新时更新 */
    private Integer bestRank;

    // 新增：动态排名字段，不持久化到数据库
    @TableField(exist = false)
    private Integer rank;

    @Override
    public String toString() {
        return name + "[" + power + "," + speed + "," + skill + "," + tactics + "," + stamina + "," + mental + "]";
    }


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

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public int getTactics() {
        return tactics;
    }

    public void setTactics(int tactics) {
        this.tactics = tactics;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getHighestPoints() {
        return highestPoints;
    }

    public void setHighestPoints(int highestPoints) {
        this.highestPoints = highestPoints;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getBestRank() {
        return bestRank;
    }

    public void setBestRank(Integer bestRank) {
        this.bestRank = bestRank;
    }
}
