package com.example.spacecolonizations.model.station;

import android.os.Handler;
import android.os.Looper;

import com.example.spacecolonizations.model.Statistics;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.CrewManager;
import com.example.spacecolonizations.model.crewmate.Technician;
import com.example.spacecolonizations.model.shop.Wallet;

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
        if (crewMembers.contains(crew)) {
            crew.setCurrentStation(this);
            return;
        }
        
        // Prevent moving to collapsed station
        if (!this.isUsable && this.breakTimeRemaining <= 0) {
            return;
        }

        if (crew.getCurrentStation() == this) {
            return;
        } else if (!crew.getCanWork()) {
            return;
        }
        if (this.crewMembers.size() < this.maxCrew) {

            if (crew.getCurrentStation() != null) {
                crew.getCurrentStation().removeCrew(crew);
            }

            crew.setCurrentStation(this);
            this.crewMembers.add(crew);
            this.setEfficiency();

            if (this instanceof TrainingCenter){
                ((TrainingCenter) this).train();
            }
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
            return;

        } else if (!crew.getCanWork()) {
            return;
        }
        
        // Prevent assigning repairmen to collapsed station
        if (this.breakTimeRemaining <= 0) {
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
            crew.setCanWork(true);
            Barracks.getInstance().assignCrew(crew);
            this.setRepairEfficiency();
        }
    }


    /**
     *Try to repair the Station
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


    /**
     * Calculate the repair efficiency of the station.
     */
    public void setRepairEfficiency() {
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

    /**
     * Get the current crew members of the station.
     * @return a list of crew members.
     */
    public List<Crew> getCrewMembers() {
        return this.crewMembers;
    }

    public List<Crew> getRepairMan() {
        return this.repairMan;
    }

    /**
     * Get the current repair men of the station.
     * @return efficiency as a float
     */
    protected float getEfficiency() {
        return this.efficiency;
    }

    public float getRepairEfficiency() {
        return this.repairEfficiency;
    }

    public int getBreakTimeRemaining() {
        return this.breakTimeRemaining;
    }

    public double getRepairTimeRemaining() {
        return this.repairTimeRemaining;
    }

    public void setisUsable(boolean usable) {
        isUsable = usable;
    }

    public void setRepairTimeRemaining(double repairTimeRemaining) {
        this.repairTimeRemaining = repairTimeRemaining;
    }

    public void setBreakTimeRemaining(int breakTimeRemaining) {
        this.breakTimeRemaining = breakTimeRemaining;
    }

    /**
     * Set the efficiency of the station.
     * This method should be overridden by subclasses.
     */
    protected abstract void setEfficiency();


    /**
     * 'Destroys' the station and kills all crew, repairmen and patients
     */
    public void breakStation() {
        this.isUsable = false;
        this.breakTimeRemaining = 60000;
        breakHandler.removeCallbacks(this.breakRunnable);
        breakHandler.post(this.breakRunnable);
    }

    public boolean getisUsable() {
        return this.isUsable;
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
                    //stop break handler
                    if (breakHandler != null){
                        breakHandler.removeCallbacks(breakRunnable);
                    }

                    isUsable = true;
                    breakTimeRemaining = 60000;
                    repairTimeRemaining = 30000;

                    setEfficiency();

                    for (int i = repairMan.size() - 1; i >= 0; i--) {
                        repairMan.get(i).receiveExp(100F);
                        removeRepairMan(repairMan.get(i));
                    }

                    Wallet.getInstance().addBalance(100);
                    Statistics stats = Statistics.getInstance();
                    stats.setNumSuccessfulMissions(stats.getNumSuccessfulMissions() + 1);
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
    protected void clearPatients(){
        return;
    }

    /**
     * Timer and crew death logic for breaking the station
     */
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
                    breakTimeRemaining = 0; // Collapsed
                    
                    //stop repair handler
                    if (repairHandler != null){
                        repairHandler.removeCallbacks(repairRunnable);
                    }

                    Statistics stats = Statistics.getInstance();
                    stats.setNumDeadCrews(stats.getNumDeadCrews() + crewMembers.size() + repairMan.size());
                    stats.setNumFailedMissions(stats.getNumFailedMissions() + 1);

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

    protected boolean isSingleton() {
        return false;
    }

//    this thing will resume handler from the json
    public void resumeHandlers() {
        if (!isUsable) {
            if (!repairMan.isEmpty()) {
                this.setRepairEfficiency();
                this.repairStation();
            }
            if (this.breakTimeRemaining > 0) {
                breakHandler.removeCallbacks(this.breakRunnable);
                breakHandler.postDelayed(this.breakRunnable, 1000);
            }
        }
    }

    // Add this special method to re-initialize transient fields after loading
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        if (isSingleton()) {
            return;
        }


        // Loading from file may cause duplicates so current station is set to transient
        // this will hopefully fix the issue
        if (this.crewMembers != null && !this.crewMembers.isEmpty()) {
            for (Crew crew : this.crewMembers) {
                crew.setCurrentStation(this);
            }
        } else if (this.crewMembers == null) {
            this.crewMembers = new ArrayList<>();
        }

        if (this.repairMan != null && !this.repairMan.isEmpty()) {
            for (Crew crew : this.repairMan) {
                crew.setCurrentStation(this);
            }
        } else if (this.repairMan == null) {
            this.repairMan = new ArrayList<>();
        }

        if (this instanceof MedBay) {
            List<Crew> patients = ((MedBay) this).getPatients();
            if (patients != null && !patients.isEmpty()) {
                for (Crew crew : patients) {
                    crew.setCurrentStation(this);
                }
            }
        }

        this.initRepairHandler();
        this.initBreakHandler();
    }


}
