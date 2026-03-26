package com.example.spacecolonizations.model.crewmate;


import com.example.spacecolonizations.reuse.Damagable;

public abstract class Crew implements Damagable {
    private String name;
    private int healthPoints;
    private int level;
    private float levelMultiplier;
    private Float exp;
    private int maxHealthPoints;


    //TODO figure out a way to temporarily remove the crew from ship into rescue missions
    //TODO implement Damageable
    //TODO keep multiplier as possible future implementation
    // and implement exp
    public Crew(String name, int healthPoints, int level, float levelMultiplier, int maxHealthPoints){
        this.name = name;
        this.healthPoints = healthPoints;
        this.level = level;
        this.levelMultiplier = levelMultiplier;
        this.exp = (float) 0;
        this.maxHealthPoints = maxHealthPoints;
    }

    public String getName(){
        return name;
    }

    public int getHealthPoints(){
        return healthPoints;
    }
    public void increaseHealthPoints(int n){
        healthPoints = healthPoints+n;
        if (healthPoints>maxHealthPoints){
            healthPoints = maxHealthPoints;
        }
    }

    public int getMaxHealthPoints(){
        return maxHealthPoints;
    }


    @Override
    public void loseHealth(int damage) {
        healthPoints = healthPoints-damage;
        if (healthPoints<0){
            healthPoints = 0;
        }
    }


    public void setMultiplier(float n){
        levelMultiplier = n;
    }

    public void resetMultiplier(){
        levelMultiplier = 1;
    }

    public Float getMultiplier(){
        return levelMultiplier;
    }

    public int getLevel(){
        return level;
    }

    public void checkExp() {
        while (true) {
            double requiredExp = 1000 * Math.exp(level);

            if (exp < requiredExp) {
                break;
            }

            exp -= (float)requiredExp;
            levelUp();
            maxHealthPoints = (int)(maxHealthPoints*1.1);
        }
    }

    public void levelUp(){
        level = level+1;
    }

    public Float getExp(){
        return exp;
    }

    public void receiveExp(Float receiveAmount){
        exp = exp+ (receiveAmount * levelMultiplier);
    }

}