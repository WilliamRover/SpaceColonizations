package com.example.spacecolonizations.model.mission.obstacle;

import java.util.HashMap;



public class Obstacle {
    private int crewNeed;
    private HashMap<String,String> locationJob;
    private int levelNeeded;

    public Obstacle(int crewNeed, int levelNeeded){
        this.crewNeed = crewNeed;
        this.locationJob = new HashMap<String,String>();
        this.levelNeeded = levelNeeded;
    }

    public int getCrewNeed(){
        return crewNeed;
    }

    public HashMap<String,String> getLocationJob(){
        return locationJob;
    }

    public int getLevelNeeded(){
        return levelNeeded;
    }

    public void setCrewNeed(int n){
        crewNeed = n;
    }


    public void addLocationJob(String location, String job){
        locationJob.put(location, job);
    }
    public void removeLocationJob(String location){
        locationJob.remove(location);
    }

    public void setLevelNeeded(int n){
        levelNeeded = n;
    }

    public void setUp(){
        return;
    }

    public String returnType(){
        return "Obstacle";
    }



}
