package com.example.spacecolonizations.model.station;


import com.example.spacecolonizations.model.crewmate.Crew;

import org.jspecify.annotations.Nullable;

public class Barracks extends Station{
    private static Barracks instance;

    public static Barracks getInstance() {
        if (instance == null) {
            instance = new Barracks();
        }
        return instance;
    }


    private Barracks() {
        super();
        this.maxCrew = 100;
    }

    @Override
    public void setEfficiency() {
        return;
    }



}
