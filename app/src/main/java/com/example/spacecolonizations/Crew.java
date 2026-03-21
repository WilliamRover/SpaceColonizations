package com.example.spacecolonizations;

public abstract class Crew {
    private String name;
    private int healthPoints;
    private int level;
    private float levelMultiplier;
    private Float exp;
    private String job;

    Crew(String name, int healthPoints, int level, int levelMultiplier, String job){
        this.name = name;
        this.healthPoints = healthPoints;
        this.level = level;
        this.levelMultiplier = levelMultiplier;
        this.exp = (float) 0;
        this.job = job;
    }

    String getName(){
        return name;
    }

    int getHealthPoints(){
        return healthPoints;
    }
    void increaseHealthPoints(int n){
        healthPoints = healthPoints+n;
    }
    void reduceHealthPoints(int n){
        healthPoints = healthPoints - n;
        if (healthPoints<0){
            healthPoints = 0;
        }
    }

    void setMultiplier(float n){
        levelMultiplier = n;
    }

    Float getMultiplier(){
        return levelMultiplier;
    }

    int getLevel(){
        return level;
    }

    void checkExp() {
        while (true) {
            double requiredExp = 1000 * Math.exp(level);

            if (exp < requiredExp) {
                break;
            }

            exp -= (float)requiredExp;
            levelUp();
        }
    }

    void levelUp(){
        level = level+1;
    }

    Float getExp(){
        return exp;
    }

    void receiveExp(Float receiveAmount){
        exp = exp+ (receiveAmount * levelMultiplier);
    }

    String getJob(){
        return job;
    }

    void changeJob(String n){
        job = n;
    }

}
