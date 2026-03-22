package com.example.spacecolonizations.model.station;


import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Gunner;

public class Turret extends Station{
    private float damage;
    public Turret(int stationStrength, int energyLevel, int maxCrew) {
        super(stationStrength, energyLevel, maxCrew);
        this.damage = 10;
    }

    public float getdamage(){
        return this.damage;
    }

    public void setDamage(){
        this.damage *= this.efficiency;
        //TODO increase damage depending on energy system

        //something related to energy needs to be input to this function
    }

    public void decreasseDamage(){
        //TODO implement decrease dps epending on damage received or whatever
    }

    public void dealDamage(){

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

            if (crew instanceof Gunner) {
                this.efficiency += 0.15F;
            }

            eff /= 2;
        }
    }
}
