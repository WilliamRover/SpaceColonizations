package com.example.spacecolonizations.model.station;

import com.example.spacecolonizations.Crew;

import java.util.ArrayList;
import java.util.List;

public abstract class Station {
    private int stationStrength;
    protected List<Crew> crewMembers;
    private int energyLevel;
    private int maxCrew;
    protected float efficiency;

    public Station(int stationStrength, int energyLevel, int maxCrew){
        this.stationStrength = stationStrength;
        this.energyLevel = energyLevel;
        this.maxCrew = maxCrew;
        this.efficiency = 0;
        this.crewMembers = new ArrayList<>();
    }

    public void assignCrew(Crew crew){
        crewMembers.add(crew);
    }

    public abstract void setEfficiency();

    public  float getEfficiency(){
        return  this.efficiency;
    }

}
