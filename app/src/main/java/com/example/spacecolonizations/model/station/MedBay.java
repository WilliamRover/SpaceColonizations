package com.example.spacecolonizations.model.station;

import android.os.Handler;
import android.os.Looper;

import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Medic;

import java.util.ArrayList;
import java.util.List;

public class MedBay extends Station{
    private List<Crew> patients;
    private int maxPatients;
    private int baseHeal;
    private boolean isHealing;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable healRunnable = new Runnable() {
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

    public MedBay(int maxCrew) {
        super(maxCrew);
        this.patients = new ArrayList<>();
        this.baseHeal = 10;
        this.maxPatients = 5;
    }

    public void heal(){
        handler.removeCallbacks(healRunnable);
        handler.post(healRunnable);
    }

    public void addPatient(Crew crew) {

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
            }
            crew.setCanWork(true);
        }
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


}

