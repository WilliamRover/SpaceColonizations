package com.example.spacecolonizations.model.ship;


import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.CrewManager;
import com.example.spacecolonizations.model.station.Barracks;
import com.example.spacecolonizations.model.station.Station;
import com.example.spacecolonizations.reuse.Damagable;

import java.util.ArrayList;
import java.util.List;

public abstract class Ship implements Damagable {
    private int hullStrength;
    private int innitHullStrength;

    public Ship(int innitHullStrength) {
        this.innitHullStrength = innitHullStrength;
        this.hullStrength = innitHullStrength;
    }
    public int getHullStrength() {
        return hullStrength;
    }
    public int getInnitHullStrength() {
        return innitHullStrength;
    }
    public void setHullStrength(int hullStrength) {
        this.hullStrength = hullStrength;
        if (this.hullStrength <= 0) {
            this.hullStrength = 0;
        }
        if (this instanceof FriendlyShip && this.hullStrength > 100) {
            this.hullStrength = 100;
        }
    }

    public void resetHp() {
        if (this instanceof FriendlyShip) {
            this.setHullStrength((int) Math.round(this.getHullStrength() + this.getHullStrength()*0.75));
        }
    }
    public void attackShip(Ship ship, View shipModel, View kaboom, ProgressBar hpProgress, TextView hpTxt, int shipDps) {

    }
    public void explode(View shipModel, View kaboom) {
        kaboom.setVisibility(View.VISIBLE);
        kaboom.setAlpha(1.0f);
        shipModel.setVisibility(View.INVISIBLE);
    }

    @Override
    public void loseHealth(int damage) {
        setHullStrength(this.hullStrength - damage);
        if (this instanceof FriendlyShip) {
            List<Station> stations = new ArrayList<>(CrewManager.getStations());
            stations.remove(Barracks.getInstance());
            if (!stations.isEmpty()) {
                Station randomStation = stations.get((int) (Math.random() * stations.size()));
                List<Crew> crews = randomStation.getCrewMembers();
                if (crews != null && !crews.isEmpty()) {
                    crews.get((int) (Math.random() * crews.size())).loseHealth(10);
                }
            }
        }
    }
}
