package com.example.spacecolonizations.model.station;

import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Technician;
import com.example.spacecolonizations.reuse.Damagable;

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class Station {
    protected boolean isUseable;
    protected List<Crew> crewMembers;
    private int maxCrew;
    protected float efficiency;
    protected List<Crew> repairMan;
    protected Barracks barracks;

    //TODO remove health and tie it to isuseable
    //TODO redo repairStation
    public Station(int stationHealth, int energyLevel, int maxCrew, Barracks barracks) {
        this.maxCrew = maxCrew;
        this.efficiency = 0;
        this.crewMembers = new ArrayList<>();
        this.repairMan = new ArrayList<>();
        this.isUseable = true;
        this.barracks = barracks;
    }

    public Station(int stationStrength, int energyLevel, int maxCrew) {
        this.maxCrew = maxCrew;
        this.efficiency = 0;
        this.crewMembers = new ArrayList<>();
        this.repairMan = new ArrayList<>();
        this.isUseable = true;
    }

    public void assignCrew(Crew crew){
        if (this.crewMembers.size() < this.maxCrew) {
            crewMembers.add(crew);
            this.setEfficiency();
        }

    }

    public void removeCrew(Crew crew, Station targetStation){
        if (this.crewMembers.remove(crew)) {
            this.setEfficiency();
            targetStation.assignCrew(crew);

        }
    }

    public void addRepairMan(Crew crew) {
        this.repairMan.add(crew);
    }

    public void removeRepairMan(Crew crew) {
        this.repairMan.remove(crew);
    }


    public void repairStation(){
        float repairRate = 0;
        float repairEfficiency = 1;



    }

    public abstract void setEfficiency();

}
