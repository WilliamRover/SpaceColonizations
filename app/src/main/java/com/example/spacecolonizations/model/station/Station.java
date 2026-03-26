package com.example.spacecolonizations.model.station;

import android.widget.Toast;

import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Technician;
import com.example.spacecolonizations.reuse.Damagable;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class Station {
    protected boolean isUseable;
    protected List<Crew> crewMembers;
    private int maxCrew;
    protected float efficiency;
    protected List<Crew> repairMan;
    protected int maxRepairmen;

    //TODO remove health and tie it to isuseable
    //TODO redo repairStation

    public Station(int maxCrew) {
        this.maxCrew = maxCrew;
        this.efficiency = 0;
        this.crewMembers = new ArrayList<>();
        this.repairMan = new ArrayList<>();
        this.isUseable = true;
        this.maxRepairmen = 2;
    }

    public void assignCrew(@NonNull Crew crew){
        if (crew.getCurrentStation() == this) {
            return;
        } else if (!crew.getCanWork()) {
            //TODO notification saying crew cannot be assigned
            return;
        }
        if (this.crewMembers.size() < this.maxCrew) {

            if (crew.getCurrentStation() != null) {
                crew.getCurrentStation().removeCrew(crew);
            }

            crew.setCurrentStation(this);
            this.crewMembers.add(crew);
            this.setEfficiency();
        }

    }

    public void removeCrew(@NonNull Crew crew){
        if (this.crewMembers.remove(crew)) {

            if (crew.getCurrentStation() == this) {
                crew.setCurrentStation(null);
            }
            this.setEfficiency();
        }
    }

    public void addRepairMan(@NonNull Crew crew) {
        if (this.repairMan.size() < this.maxRepairmen) {

            if (crew.getCurrentStation() != null) {
                crew.getCurrentStation().removeCrew(crew);
            }

            crew.setCurrentStation(this);
            crew.setCanWork(false);
            this.repairMan.add(crew);
            this.setEfficiency();
        }
    }

    public void removeRepairMan(@NonNull Crew crew) {
        if (this.repairMan.remove(crew)) {

            if (crew.getCurrentStation() == this) {
                crew.setCurrentStation(Barracks.getInstance());
                crew.setCanWork(true);
            }
        }
    }


    public void repairStation(){
        float repairRate = 0;
        float repairEfficiency = 1;


    }


    protected float getEfficiency() {
        return this.efficiency;
    }
    public abstract void setEfficiency();

}
