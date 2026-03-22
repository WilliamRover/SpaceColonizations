package com.example.spacecolonizations.model.station;



import com.example.spacecolonizations.model.crewmate.Crew;

import java.util.ArrayList;
import java.util.List;

public abstract class Station {
    private int stationHealth;
    protected List<Crew> crewMembers;
    private int energyLevel;
    private int maxCrew;
    protected float efficiency;

    public Station(int stationHealth, int energyLevel, int maxCrew){
        this.stationHealth = stationHealth;
        this.energyLevel = energyLevel;
        this.maxCrew = maxCrew;
        this.efficiency = 0;
        this.crewMembers = new ArrayList<>();
    }

    public void assignCrew(Crew crew){
        if (this.crewMembers.size() < this.maxCrew) {
            crewMembers.add(crew);
            this.setEfficiency();
            return;
        }

    }

    public void removeCrew(Crew crew){
        if (this.crewMembers.isEmpty()){
            return;
        }
        this.crewMembers.remove(crew);
        this.setEfficiency();
    }

    public abstract void setEfficiency();

}
