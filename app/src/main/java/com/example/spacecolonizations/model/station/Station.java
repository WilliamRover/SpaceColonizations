package com.example.spacecolonizations.model.station;



import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Technician;
import com.example.spacecolonizations.reuse.Damagable;

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class Station implements Damagable {
    protected int stationHealth;

    protected boolean isUseable;
    private int maxStationHealth;
    protected List<Crew> crewMembers;
    private int energyLevel;
    private int maxCrew;
    protected float efficiency;
    protected List<Crew> repairMan;
    protected Barracks barracks;

    public Station(int stationHealth, int energyLevel, int maxCrew, Barracks barracks) {
        this.stationHealth = stationHealth;
        this.maxStationHealth = stationHealth;
        this.energyLevel = energyLevel;
        this.maxCrew = maxCrew;
        this.efficiency = 0;
        this.crewMembers = new ArrayList<>();
        this.repairMan = new ArrayList<>();
        this.isUseable = true;
        this.barracks = barracks;
    }

    public Station(int stationStrength, int energyLevel, int maxCrew) {
        this.stationHealth = stationHealth;
        this.maxStationHealth = stationHealth;
        this.energyLevel = energyLevel;
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
        float eff = 1;
        if (this.repairMan.isEmpty()){
            return;
        }
        if (stationHealth<maxStationHealth){
            for (Crew repairMan: this.repairMan){
                repairRate = repairRate + eff;
                if (repairMan instanceof Technician){
                    repairRate += 0.15F;
                }
                eff /= 2;
            }

            stationHealth = (int) (stationHealth + 10*repairRate);
            if (stationHealth>maxStationHealth){
                stationHealth = maxStationHealth;
            }

        }
    }

    public abstract void setEfficiency();

}
