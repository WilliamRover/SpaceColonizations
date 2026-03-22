package com.example.spacecolonizations.model.station;

import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Gunner;
import com.example.spacecolonizations.model.crewmate.Technician;

public class EnergyContainer extends Station{

    public EnergyContainer(int stationStrength, int energyLevel, int maxCrew, Barracks barracks) {
        super(stationStrength, energyLevel, maxCrew, barracks);
    }

    public void depleteEnergy(){
        //TODO wtf is energy
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

            if (crew instanceof Technician) {
                ttotalEfficiency += 0.15F;
            }

            increment /= 2;
        }

        this.efficiency = ttotalEfficiency;
    }

    @Override
    public void loseHealth(int damage) {
        this.stationHealth -= damage;

        if (this.stationHealth <= 0) {
            this.stationHealth = 0;

            for (Crew crew : this.crewMembers) {
                crew.loseHealth(crew.getMaxHealthPoints());
                this.removeCrew(crew, this.barracks);
            }
        }
    }
}
