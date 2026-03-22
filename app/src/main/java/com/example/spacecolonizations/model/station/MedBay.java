package com.example.spacecolonizations.model.station;

import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Medic;

import java.util.ArrayList;
import java.util.List;

public class MedBay extends Station{
    private List<Crew> patients;
    private int baseHeal;
    public MedBay(int stationStrength, int energyLevel, int maxCrew) {
        super(stationStrength, energyLevel, maxCrew);
        this.patients = new ArrayList<>();
        baseHeal = 10;
    }

    public void heal(){

        for (Crew crew: this.crewMembers){
            float multiplier = this.efficiency;

            crew.increaseHealthPoints((int) (baseHeal * this.efficiency));
        }
    }

    public void addPatient(Crew crew) {
        patients.add(crew);
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

        for (Crew crew: this.crewMembers){
            this.efficiency += eff;

            if (crew instanceof Medic) {
                this.efficiency += 0.15F;
            }

            eff /= 2;

        }
    }
}
