package com.example.spacecolonizations.model.station;


import com.example.spacecolonizations.model.crewmate.Crew;

public class Barracks extends Station{
    public Barracks(int stationStrength, int energyLevel, int maxCrew) {
        super(stationStrength, energyLevel, maxCrew);
    }

    @Override
    public void setEfficiency() {
        this.efficiency = 1;
    }

    public void deployCrew(Crew crew){
        //TODO deploycrew
    }

    public void reserveCrew(Crew crew) {
        //TODO reserve crew
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
