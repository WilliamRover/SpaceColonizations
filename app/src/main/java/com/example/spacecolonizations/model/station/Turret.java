package com.example.spacecolonizations.model.station;


import com.example.spacecolonizations.model.Statistics;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Gunner;
import com.example.spacecolonizations.model.ship.EnemyShip;
import com.example.spacecolonizations.model.ship.FriendlyShip;

public class Turret extends Station{
    private float damage;
    public Turret() {
        super();
        this.damage = 10;
        this.maxCrew = 2;
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
        ship.loseHealth((int) (this.damage));
    }

    public void setMaxCrew() {
        if (this.maxCrew == 3){
            return;
        }
        if (Statistics.getInstance().getShipKills() >= 3) {
            this.maxCrew = 3;
        } else {
            this.maxCrew = 2;
        }
    }

    @Override
    protected void setEfficiency() {
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