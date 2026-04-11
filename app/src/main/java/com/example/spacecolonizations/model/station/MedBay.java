package com.example.spacecolonizations.model.station;

import android.os.Handler;
import android.os.Looper;

import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.CrewManager;
import com.example.spacecolonizations.model.crewmate.Medic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MedBay extends Station{
    private List<Crew> patients;
    private int maxPatients;
    private int baseHeal;
    private boolean isHealing;
    private transient Handler handler;
    private transient Runnable healRunnable;

    public MedBay() {
        super();
        this.patients = new ArrayList<>();
        this.baseHeal = 10;
        this.maxPatients = 5;
        this.maxCrew = 5;
        this.initMedBayHandler();
    }

    public void heal(){
        handler.removeCallbacks(healRunnable);
        handler.post(healRunnable);
    }

    public void addPatient(Crew crew) {

        if (this.patients == null) {
            this.patients = new ArrayList<>();
        }

        if (!(this.patients.size() < this.maxPatients) || this.patients.contains(crew)) {
        //TODO notification saying patient cannot be assigned
            return;

        }

        if (crew.getCurrentStation() != null) {
            crew.getCurrentStation().removeCrew(crew);
        }

        crew.setCurrentStation(this);
        crew.setCanWork(false);
        this.patients.add(crew);

        if (!this.isHealing) {
            heal();
        }
    }

    private void removePatient(Crew crew) {
        if (this.patients.remove(crew)) {

            if (crew.getCurrentStation() == this) {
                crew.setCurrentStation(Barracks.getInstance());
                Barracks.getInstance().assignCrew(crew);
            }
            crew.setCanWork(true);

        }
    }

    protected List<Crew> getPatients() {
        return this.patients;
    }

    /**
     * kill the patients if med-bay breaks
     */
    @Override
    protected void clearPatients(){
        for (int i = patients.size() - 1; i >= 0; i--) {
            Crew crew = patients.get(i);
            crew.setCurrentStation(null);
            CrewManager.removeCrew(crew);
        }
        patients.clear();
        isHealing = false;
    }

    @Override
    public void setEfficiency() {
        float increment = 1;
        float totalEfficiency =  0;

        if (this.crewMembers.isEmpty()){
            this.efficiency = 0;
            return;
        }

        for (Crew crew: this.crewMembers){
            if (crew.getHealthPoints() == 0) {
                continue;
            }
            totalEfficiency += increment;

            if (crew instanceof Medic) {
                totalEfficiency += 0.15F;
            }

            increment /= 2;
        }

        this.efficiency = totalEfficiency;
        heal();
    }


    private void initMedBayHandler() {
        handler = new Handler(Looper.getMainLooper());
        healRunnable = new Runnable() {
            @Override
            public void run() {
                if (crewMembers.isEmpty() || patients.isEmpty() || !isUsable || getEfficiency() == 0){
                    isHealing = false;
                    return;
                }
                isHealing = true;

                for (int i = patients.size() - 1; i >= 0; i--) {
                    Crew patient = patients.get(i);
                    patient.increaseHealthPoints((int) (baseHeal * efficiency));
                    if (patient.getHealthPoints() >= patient.getMaxHealthPoints()) {
                        removePatient(patient);
                    }
                }
                handler.postDelayed(this, 1000);
            }
        };

        if (!isUsable || crewMembers.isEmpty() || patients.isEmpty()) {
            isHealing = false;
        } else {
            this.heal();
        }
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.initMedBayHandler();
    }
}