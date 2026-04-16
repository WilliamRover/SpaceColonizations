package com.example.spacecolonizations.model.mission.obstacle;

import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.station.Station;

import java.util.HashMap;
import java.util.ArrayList;



public class Obstacle {
    private int crewNeed;
    private HashMap<Station, ArrayList<Crew>> locationJob;
    private int levelNeeded;

    public Obstacle(int crewNeed, int levelNeeded){
        this.crewNeed = crewNeed;
        this.locationJob = new HashMap<Station, ArrayList<Crew>>();
        this.levelNeeded = levelNeeded;
    }

    public int getCrewNeed(){
        return crewNeed;
    }

    public HashMap<Station, ArrayList<Crew>> getLocationJob(){
        return locationJob;
    }

    public int getLevelNeeded(){
        return levelNeeded;
    }

    public void setCrewNeed(int n){
        crewNeed = n;
    }


    public void addLocationJob(Station location, Crew job){
        ArrayList<Crew> crewList = locationJob.get(location);

        if (crewList == null) {
            crewList = new ArrayList<>();
            locationJob.put(location, crewList);
        }

        if (!crewList.contains(job)) {
            crewList.add(job);
        }
    }
    public void removeLocationJob(Station location){
        locationJob.remove(location);
    }

    public void setLevelNeeded(int n){
        levelNeeded = n;
    }


    public String returnType(){
        return "Obstacle";
    }

    public void setUp(){

    }



}
