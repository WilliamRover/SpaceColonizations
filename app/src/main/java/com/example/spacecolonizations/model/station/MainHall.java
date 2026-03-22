package com.example.spacecolonizations.model.station;

import com.example.spacecolonizations.model.crewmate.Crew;

public class MainHall extends Station{
    public MainHall(int stationStrength, int energyLevel, int maxCrew) {
        super(stationStrength, energyLevel, maxCrew);
    }

    @Override
    public void setEfficiency() {
        this.efficiency = 1;
    }

    @Override
    public void loseHealth(int damage) {
        this.stationHealth -= damage;

        if (this.stationHealth <= 0) {
            this.stationHealth = 0;

            for (Crew crew : this.crewMembers) {
                crew.loseHealth(crew.getMaxHealthPoints());
            }
        }
    }


}
