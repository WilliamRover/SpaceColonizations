package com.example.spacecolonizations.model.ship;

import android.widget.TextView;

import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.CrewManager;
import com.example.spacecolonizations.model.station.Barracks;
import com.example.spacecolonizations.model.station.CommandCenter;
import com.example.spacecolonizations.model.station.MedBay;
import com.example.spacecolonizations.model.station.Station;
import com.example.spacecolonizations.model.station.TrainingCenter;
import com.example.spacecolonizations.model.station.Turret;

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
}
