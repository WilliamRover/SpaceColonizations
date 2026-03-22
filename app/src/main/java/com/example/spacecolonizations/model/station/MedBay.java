package com.example.spacecolonizations.model.station;

import android.os.Handler;
import android.os.Looper;

import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Medic;

import java.util.ArrayList;
import java.util.List;

public class MedBay extends Station{
    private List<Crew> patients;
    private int baseHeal;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable healRunnable = new Runnable() {
        @Override
        public void run() {
            if (crewMembers.isEmpty() || patients.isEmpty()) {
                return;
            }

            for (Crew patient : patients) {
                patient.increaseHealthPoints((int) (baseHeal * efficiency));
            }
            handler.postDelayed(this, 1000);
        }
    };

    public MedBay(int stationStrength, int energyLevel, int maxCrew) {
        super(stationStrength, energyLevel, maxCrew);
        this.patients = new ArrayList<>();
        baseHeal = 10;
    }

    public void heal(){
        handler.removeCallbacks(healRunnable);
        handler.post(healRunnable);
    }

    public void addPatient(Crew crew) {
        patients.add(crew);
        heal();
    }

    public void removePatient(Crew crew) {
        patients.remove(crew);
    }

    @Override
    public void setEfficiency() {
        float eff = 1;

        if (this.crewMembers.isEmpty()){
            this.efficiency = 0;
            return;
        }

        this.efficiency = 0;
        for (Crew crew: this.crewMembers){
            this.efficiency += eff;

            if (crew instanceof Medic) {
                this.efficiency += 0.15F;
            }

            eff /= 2;
        }
        heal();
    }
}
