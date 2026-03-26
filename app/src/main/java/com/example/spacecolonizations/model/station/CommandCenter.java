package com.example.spacecolonizations.model.station;

import com.example.spacecolonizations.model.crewmate.Commander;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Gunner;
import com.example.spacecolonizations.model.crewmate.Navigator;

//TODO showStatistic function
//TODO getMission function
public class CommandCenter extends Station{
    public CommandCenter(int stationStrength, int energyLevel, int maxCrew, Barracks barracks) {
        super(stationStrength, energyLevel, maxCrew);
    }

    @Override
    public void setEfficiency() {
        float increment = 1;
        float ttotalEfficiency =  0;

        if (this.crewMembers.isEmpty()){
            this.efficiency = 0;
            return;
        }

        for (Crew crew: this.crewMembers){
            if (crew.getHealthPoints() == 0) {
                continue;
            }
            ttotalEfficiency += increment;

            if (crew instanceof Commander || crew instanceof Navigator) {
                ttotalEfficiency += 0.15F;
            }

            increment /= 2;
        }

        this.efficiency = ttotalEfficiency;
    }

}
