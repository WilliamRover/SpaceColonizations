package com.example.spacecolonizations.model.station;

import com.example.spacecolonizations.model.crewmate.Commander;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Navigator;

public class CommandCenter extends Station{
    public CommandCenter(int stationStrength, int energyLevel, int maxCrew) {
        super(stationStrength, energyLevel, maxCrew);
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

            if (crew instanceof Commander || crew instanceof Navigator) {
                this.efficiency += 0.15F;
            }

            eff /= 2;

        }
    }
}
