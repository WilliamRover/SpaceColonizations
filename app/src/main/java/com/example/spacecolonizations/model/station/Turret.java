package com.example.spacecolonizations.model.station;


import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Gunner;
import com.example.spacecolonizations.model.ship.EnemyShip;

public class Turret extends Station{
    private float damage;
    public Turret(int stationStrength, int energyLevel, int maxCrew, Barracks barracks) {
        super(stationStrength, energyLevel, maxCrew, barracks);
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


    public void dealDamage(EnemyShip ship){
        if (!this.isUseable){
            return;
        }
        ship.setHullStrength((int) (ship.getHullStrength() - this.damage));
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

            if (crew instanceof Gunner) {
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
            this.isUseable = false;

            for (Crew crew : this.crewMembers) {
                crew.loseHealth(crew.getMaxHealthPoints());
                this.removeCrew(crew, this.barracks);
            }
        }
    }
}
