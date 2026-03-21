package com.example.spacecolonizations.model.station;

import com.example.spacecolonizations.Crew;

public class MainHall extends Station{
    public MainHall(int stationStrength, int energyLevel, int maxCrew) {
        super(stationStrength, energyLevel, maxCrew);
    }

    @Override
    public void setEfficiency() {
        if (this.crewMembers.isEmpty()) {
            this.efficiency = 1;
        }

        for (Crew crew: this.crewMembers){

        }
    }


}
