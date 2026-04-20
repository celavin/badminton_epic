package com.celavin.badmintonepic.web.dto;

public class PlayerDetailDTO {

    private Long id;
    private String name;
    private int age;
    private String nationality;
    private Integer rank;
    private int points;
    private int highestPoints;
    private Integer bestRank;
    private int morale;
    private int power;
    private int speed;
    private int skill;
    private int tactics;
    private int stamina;
    private int mental;
    private String lastTenRecord;
    private int streak;
    private String streakDescription;

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

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
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

    public Integer getBestRank() {
        return bestRank;
    }

    public void setBestRank(Integer bestRank) {
        this.bestRank = bestRank;
    }

    public int getMorale() {
        return morale;
    }

    public void setMorale(int morale) {
        this.morale = morale;
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

    public String getLastTenRecord() {
        return lastTenRecord;
    }

    public void setLastTenRecord(String lastTenRecord) {
        this.lastTenRecord = lastTenRecord;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public String getStreakDescription() {
        return streakDescription;
    }

    public void setStreakDescription(String streakDescription) {
        this.streakDescription = streakDescription;
    }
}
