package com.example.spacecolonizations.model.station;

import android.os.Handler;
import android.os.Looper;

import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.CrewManager;
import com.example.spacecolonizations.model.crewmate.Technician;

import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Station implements Serializable {
    protected boolean isUsable;
    protected List<Crew> crewMembers;
    protected int maxCrew;
    protected float efficiency;
    protected List<Crew> repairMan;
    protected int maxRepairmen;
    protected float repairEfficiency;
    private final double baseRepairtime = 30000; // in milliseconds
    protected double repairTimeRemaining;
    private transient Handler repairHandler;
    private transient Runnable repairRunnable;
    protected int breakTimeRemaining;
    protected transient Handler breakHandler;
    protected transient Runnable breakRunnable;


    //TODO remove health and tie it to isuseable
    //TODO redo repairStation

    public Station() {
        this.efficiency = 0;
        this.crewMembers = new ArrayList<>();
        this.repairMan = new ArrayList<>();
        this.isUsable = true;
        this.maxRepairmen = 2;
        this.repairTimeRemaining = 0;
        this.initRepairHandler();
        this.initBreakHandler();
    }

    /**
     * Assign a crew to the station.
     * @param crew
     */
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

    /**
     * Remove a crew from the station.
     * @param crew
     */
    public void removeCrew(@NonNull Crew crew){
        if (this.crewMembers.remove(crew)) {

            if (crew.getCurrentStation() == this) {
                crew.setCurrentStation(null);
            }
            this.setEfficiency();
        }
    }

    /**
     * Assign a repairman to the station.
     * @param crew
     */
    public void addRepairMan(@NonNull Crew crew) {
        if (this.isUsable) {
            //TODO notification saying station is not damaged
            return;

        } else if (!crew.getCanWork()) {
            //TODO notification saying repairman cannot be assigned
            return;
        }
        if (this.repairMan.size() < this.maxRepairmen) {

            if (crew.getCurrentStation() != null) {
                crew.getCurrentStation().removeCrew(crew);
            }

            crew.setCurrentStation(this);
            crew.setCanWork(false);
            this.repairMan.add(crew);
            this.setRepairEfficiency();
            this.repairStation();
        }
    }


    /**
     * Remove a repairman from the station.
     * @param crew
     */
    private void removeRepairMan(@NonNull Crew crew) {
        if (this.repairMan.remove(crew)) {

            if (crew.getCurrentStation() == this) {
                crew.setCurrentStation(Barracks.getInstance());
                crew.setCanWork(true);
            }
            this.setRepairEfficiency();
        }
    }


    /**
     *
     */
    public void repairStation(){
        if (this.isUsable) {
            this.repairTimeRemaining = 0;
            return;
        }

        if (this.repairTimeRemaining <= 0){
            this.repairTimeRemaining = baseRepairtime;
        }
        if (!this.repairMan.isEmpty()) {
            repairHandler.removeCallbacks(repairRunnable);
            repairHandler.post(repairRunnable);
        }

    }


    protected void setRepairEfficiency() {
        float increment = 1;
        float totalEfficiency =  0;

        if (this.repairMan.isEmpty()){
            this.repairEfficiency = 0;
            return;
        }

        for (Crew crew: this.repairMan){
            if (crew.getHealthPoints() == 0) {
                continue;
            }
            totalEfficiency += increment;

            if (crew instanceof Technician) {
                totalEfficiency += 0.20F;
            }

            increment /= 2;
        }
        this.repairEfficiency = totalEfficiency;
    }

    public List<Crew> getCrewMembers() {
        return this.crewMembers;
    }

    protected float getEfficiency() {
        return this.efficiency;
    }
    public abstract void setEfficiency();


    /**
     * 'Destroys' the station and kills all crew, repairmen and patients
     */
    public void explode() {
        this.isUsable = false;
        this.breakTimeRemaining = 60000;
        breakHandler.removeCallbacks(this.breakRunnable);
        breakHandler.post(this.breakRunnable);
    }

    protected void initRepairHandler() {
        repairHandler = new Handler(Looper.getMainLooper());
        repairRunnable = new Runnable() {
            @Override
            public void run() {
                if (repairMan.isEmpty() || isUsable) {
                    return;
                }

                repairTimeRemaining -= 1000 * repairEfficiency;

                if (repairTimeRemaining <= 0) {
                    repairTimeRemaining = 0;
                    isUsable = true;
                    setEfficiency();

                    for (int i = repairMan.size() - 1; i >= 0; i--) {
                        removeRepairMan(repairMan.get(i));
                    }
                    return;
                } else {
                    repairHandler.postDelayed(this, 1000);
                }
            }
        };

        //continue repair if game closed while repairing
        if (!isUsable && !repairMan.isEmpty()) {
            this.repairStation();
        }
    }

    /**
     * TO be overridden by medbay.
     * no for any other class
     */
    protected void clearPatients(){ return;}
    protected void initBreakHandler() {
        breakHandler = new Handler(Looper.getMainLooper());
        breakRunnable = new Runnable() {
            @Override
            public void run() {
                if (isUsable) {
                    return;
                }

                breakTimeRemaining -= 1000;

                if (breakTimeRemaining <= 0) {
                    for (int i = crewMembers.size() - 1; i >= 0; i--) {
                        Crew crew = crewMembers.get(i);
                        crew.setCurrentStation(null);
                        CrewManager.removeCrew(crew);
                    }
                    crewMembers.clear();

                    for (int i = repairMan.size() - 1; i >= 0; i--) {
                        Crew crew = repairMan.get(i);
                        crew.setCurrentStation(null);
                        CrewManager.removeCrew(crew);
                    }
                    repairMan.clear();

                    clearPatients();

                    breakTimeRemaining = 0;
                    repairTimeRemaining = 30000;
                    return;

                } else {
                    breakHandler.postDelayed(this, 1000);
                }
            }
        };


        if (!this.isUsable && this.breakTimeRemaining > 0) {
            breakHandler.postDelayed(this.breakRunnable, 1000);
        }
    }

    // Add this special method to re-initialize transient fields after loading
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        // Loading from file may cause dupliates so curret station is set to transient
        // this will hopefully fix the issue
        if (!this.crewMembers.isEmpty()) {
            for (Crew crew : this.crewMembers) {
                crew.setCurrentStation(this);
            }
        }

        if (!this.repairMan.isEmpty()) {
            for (Crew crew : this.repairMan) {
                crew.setCurrentStation(this);
            }
        }

        if (this instanceof MedBay) {
            if (!((MedBay) this).getPatients().isEmpty()) {
                for (Crew crew : ((MedBay) this).getPatients()) {
                    crew.setCurrentStation(this);
                }
            }
        }

        this.initRepairHandler();
        this.initBreakHandler();
    }
}