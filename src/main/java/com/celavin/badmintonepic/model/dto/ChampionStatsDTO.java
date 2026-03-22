package com.celavin.badmintonepic.model.dto;

public class ChampionStatsDTO {
    private String name;
    private Integer major;
    private Integer elite;
    private Integer challenge;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getElite() {
        return elite;
    }

    public void setElite(Integer elite) {
        this.elite = elite;
    }

    public Integer getChallenge() {
        return challenge;
    }

    public void setChallenge(Integer challenge) {
        this.challenge = challenge;
    }

    @Override
    public String toString() {
        return name + " [Major: " + major + ", Elite: " + elite + ", Challenge: " + challenge + "]";
    }
}
