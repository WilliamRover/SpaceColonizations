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
    }
    public void recruitCrew(Crew c) {
        crews.add(c);
        totalCrew++;
    }
}
