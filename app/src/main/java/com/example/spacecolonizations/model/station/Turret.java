package com.example.spacecolonizations.model.station;


import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Gunner;
import com.example.spacecolonizations.model.ship.EnemyShip;

public class Turret extends Station{
    private float damage;
    public Turret() {
        super();
        this.damage = 10;
        this.maxCrew = 5;
    }

    public float getdamage(){
        return this.damage;
    }

    private void setDamage(){
        this.damage *= this.efficiency;
    }

    public void dealDamage(EnemyShip ship){
        if (!this.isUsable){
            return;
        }
        ship.setHullStrength((int) (ship.getHullStrength() - this.damage));
    }

    @Override
    public void setEfficiency() {
        float increment = 1;
        float ttotalEfficiency =  0;

        if (this.crewMembers.isEmpty() || !this.isUsable){
            this.efficiency = 0;
            return;
        }

        for (Crew crew: this.crewMembers){
            if (crew.getHealthPoints() == 0) {
                continue;
            }

            // Damage scales with 10% of level also
            ttotalEfficiency += (float) (increment + crew.getLevel() * 0.01);

            if (crew instanceof Gunner) {
                ttotalEfficiency += 0.15F;
            }

            increment /= 2;
        }

        this.efficiency = ttotalEfficiency;
        this.setDamage();

    }


}
