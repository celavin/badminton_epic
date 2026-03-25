package com.celavin.badmintonepic.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.celavin.badmintonepic.service.GameStateService;


@TableName("game_state")
public class GameState {
    @TableId(type = IdType.INPUT)//
    private Integer id =1 ; // 永远只有一条记录

    private int year = 2026; // 初始年份
    private int month = 1;   // 1-12
    private int week = 1;    // 1-4
    private int day = 1;     // 1-7（周一到周日）

    // 推进一天的方法
    public void advanceOneDay() {
        day++;
        if (day > 7) {
            day = 1;
            week++;
            if (week > 4) {
                week = 1;
                month++;
                if (month > 12) {
                    month = 1;
                    year++;
                }
            }
        }
    }
    public void advanceOneMonth(){
        month++;
        if(month>12){
            month=1;
            year++;
        }
    }

    @Override
    public String toString() {
        return year+"年"+month+"月第"+week+"周第"+day+"日";
    }
    public GameState(){}
    public GameState(int year,int month,int week,int day){
        this.year=year;
        this.month=month;
        this.week=week;
        this.day=day;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
