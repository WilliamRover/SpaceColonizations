package com.example.spacecolonizations.model.ship;

import static java.lang.Math.round;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
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
    }
    public void attackShip(Ship ship, View shipModel, View kaboom, ProgressBar hpProgress, TextView hpTxt, int shipDps) {

    }
    private void explode(View shipModel, View kaboom) {
        kaboom.setVisibility(View.VISIBLE);
        kaboom.setAlpha(1.0f);
        shipModel.setVisibility(View.INVISIBLE);
    }

    @Override
    public void loseHealth(int damage) {
        this.hullStrength -= damage;
        if (this instanceof FriendlyShip) {
            List<Station> stations = CrewManager.getStations();
            stations.remove(Barracks.getInstance());
            List<Crew> crews = stations.get((int) round((Math.random()*stations.size()))).getCrewMembers();
            crews.get((int) round((Math.random()*crews.size()))).loseHealth(10);
        }
    }
}
