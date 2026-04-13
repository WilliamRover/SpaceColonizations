package com.example.spacecolonizations.model.ship;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spacecolonizations.activities.MenuActivity;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.CrewManager;
import com.example.spacecolonizations.model.station.Station;

import java.util.ArrayList;
import java.util.List;

public class FriendlyShip extends Ship {
    private List<Crew> crews;
    private List<Station> stations;
    private static FriendlyShip ship;
    private int shipKill;
    private TextView friendlyExplode;

    private FriendlyShip(int innitHullStrength) {
        super(innitHullStrength);
        this.crews = new ArrayList<>();
        this.stations = new ArrayList<>();
        this.stations = CrewManager.getStations();
    }

    public static FriendlyShip getShip() {
        if (ship == null) {
            ship = new FriendlyShip(100);
        }
        return ship;
    }

    public void recruitCrew(Crew c) {
        crews.add(c);
        CrewManager.addCrew(c);
    }

    public void resetShip() {
        this.setHullStrength(this.getInnitHullStrength());
        this.crews.clear();
        this.shipKill = 0;
        // The stations list in CrewManager is cleared and re-populated separately,
        // but since this.stations points to it, it will be updated.
    }

    /**
     * Adds a station to the ship if no station of the same type already exists.
     * @param newStation The station to add.
     */
    public void addStation(Station newStation) {
        for (Station s : stations) {
            if (s.getClass().equals(newStation.getClass())) {
                return;
            }
        }
        stations.add(newStation);
    }

    /**
     * Returns the station of the specified type.
     * @param stationClass The class of the station to retrieve.
     * @return The station instance, or null if not found.
     */
    public Station getStation(Class<? extends Station> stationClass) {
        for (Station s : stations) {
            if (s.getClass().equals(stationClass)) {
                return s;
            }
        }
        return null;
    }

    public List<Station> getStations() {
        return stations;
    }

    @Override
    public void explode(View shipModel, View kaboom) {
        kaboom.setVisibility(View.VISIBLE);
        kaboom.setAlpha(1.0f);
        shipModel.setVisibility(View.INVISIBLE);

        Context context = shipModel.getContext();

        CrewManager.deleteSave(context);
        //force the player to the main menu
        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
            CrewManager.deleteSave(context);

            Toast.makeText(context, "Ship kaboom. Game over noob!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(context, MenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }, 2000);
    }
}
