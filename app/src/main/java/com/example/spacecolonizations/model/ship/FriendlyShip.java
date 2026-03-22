package com.example.spacecolonizations.model.ship;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.spacecolonizations.model.crewmate.Crew;

import java.util.List;

public class FriendlyShip extends Ship{
    private int energy;
    private int innitEnergy;
    private List<Crew> crews;
    private int totalCrew;

    private TextView friendlyExplode;

    public FriendlyShip(int innitHullStrength, int innitEnergy) {
        super(innitHullStrength);
        this.innitEnergy = innitEnergy;
        this.energy = innitEnergy;

    }

    public int getEnergy() {
        return energy;
    }
    public int getInnitEnergy(){
        return innitEnergy;
    }
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void recruitCrew(Crew c) {
        crews.add(c);
        totalCrew++;
    }
}
