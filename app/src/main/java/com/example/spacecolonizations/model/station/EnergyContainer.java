package com.example.spacecolonizations.model.station;

import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Technician;

public class EnergyContainer extends Station{

    public EnergyContainer(int stationStrength, int energyLevel, int maxCrew) {
        super(stationStrength, energyLevel, maxCrew);
    }

    public void depleteEnergy(){
        //TODO wtf is energy
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

            if (crew instanceof Technician) {
                this.efficiency += 0.15F;
            }

            eff /= 2;

        }
    }
}
