package com.example.spacecolonizations.model.station;

public class MainHall extends Station{
    public MainHall(int stationStrength, int energyLevel, int maxCrew) {
        super(stationStrength, energyLevel, maxCrew);
    }

    @Override
    public void setEfficiency() {
        this.efficiency = 1;
    }


}
