package com.example.spacecolonizations.model.crewmate;


import com.example.spacecolonizations.model.station.Station;
import com.example.spacecolonizations.reuse.Damagable;

import java.io.Serializable;

public abstract class Crew implements Damagable, Serializable {
    private String name;
    private int healthPoints;
    private int level;
    private Float exp;
    private int maxHealthPoints;
    private transient Station currentStation;
    private boolean canWork;
    private boolean isPatient;
    //The above attribute is used to figure out if the crew member is able to be assigned for jobs at station
    //They will not be able to be assigned if they are a patient or they are in mission.

    //TODO update this logic when missions are merged to main in the missions folder


    //TODO figure out a way to temporarily remove the crew from ship into rescue missions
    //TODO keep multiplier as possible future implementation
    public Crew(String name, int healthPoints, int maxHealthPoints){
        this.name = name;
        this.healthPoints = healthPoints;
        this.level = 1;
        this.exp = (float) 0;
        this.maxHealthPoints = maxHealthPoints;
        this.canWork = true;
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
            this.canWork = false;
        }
    }


    public int getLevel(){
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    private void checkExp() {
        while (true) {
            double requiredExp = (1000 * Math.exp(level))/2;

            if (exp < requiredExp) {
                break;
            }

            exp -= (float)requiredExp;
            levelUp();
        }
    }

    private void levelUp(){
        level = level+1;
        maxHealthPoints = (int)(maxHealthPoints*1.1);
    }

    public void receiveExp(Float receiveAmount){
        exp += receiveAmount;
        this.checkExp();
    }

    public Float getExp() {
        return exp;
    }

    public void setExp(Float exp) {
        this.exp = exp;
    }

    public Station getCurrentStation(){
        return this.currentStation;
    }

    /**
     * Sets the current station of the crew.
     * Do not call this function in main
     * @param station The station to set the crew to
     */
    public void setCurrentStation(Station station){
        this.currentStation = station;
    }

    public boolean getCanWork(){
        return canWork;
    }

    public void setCanWork(boolean n){
        canWork = n;
    }

    public boolean isPatient() {
        return isPatient;
    }

    public void setPatient(boolean patient) {
        isPatient = patient;
    }
}