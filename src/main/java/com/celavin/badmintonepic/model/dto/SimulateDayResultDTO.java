package com.celavin.badmintonepic.model.dto;

import java.util.ArrayList;
import java.util.List;

public class SimulateDayResultDTO {

    private int year;
    private int month;
    private int week;
    private int dayOfWeek;
    private String phaseDescription;
    private final List<String> messages = new ArrayList<>();

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

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getPhaseDescription() {
        return phaseDescription;
    }

    public void setPhaseDescription(String phaseDescription) {
        this.phaseDescription = phaseDescription;
    }

    public List<String> getMessages() {
        return messages;
    }
}
