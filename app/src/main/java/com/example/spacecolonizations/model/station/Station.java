package com.example.spacecolonizations.model.station;



import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Technician;

import java.util.ArrayList;
import java.util.List;

public abstract class Station {
    private int stationHealth;
    private int maxStationHealth;
    protected List<Crew> crewMembers;
    private int energyLevel;
    private int maxCrew;
    protected float efficiency;
    protected List<Crew> repairMan;

    public Station(int stationHealth, int energyLevel, int maxCrew){
        this.stationHealth = stationHealth;
        this.maxStationHealth = stationHealth;
        this.energyLevel = energyLevel;
        this.maxCrew = maxCrew;
        this.efficiency = 0;
        this.crewMembers = new ArrayList<>();
        this.repairMan = new ArrayList<>();
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
            this.efficiency = 0;
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
