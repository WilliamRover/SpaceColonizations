package com.example.spacecolonizations.model.station;

import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Medic;

public class MedBay extends Station{
    public MedBay(int stationStrength, int energyLevel, int maxCrew) {
        super(stationStrength, energyLevel, maxCrew);
    }

    public void heal(){
        int baseIncrease = 10;
        for (Crew crew: this.crewMembers){
            float multiplier = this.efficiency;

            crew.increaseHealthPoints((int) (baseIncrease * this.efficiency));
        }
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
