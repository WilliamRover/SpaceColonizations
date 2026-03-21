package com.example.spacecolonizations.model.station;

import com.example.spacecolonizations.model.crewmate.Crew;

public class TrainingCenter extends Station{
    public TrainingCenter(int stationStrength, int energyLevel, int maxCrew) {
        super(stationStrength, energyLevel, maxCrew);
    }

    @Override
    public void setEfficiency() {
        this.efficiency = 1;
    }

    public void train(){
        //TODO train crew periodically
    }
}
