package com.example.spacecolonizations.model.station;


import com.example.spacecolonizations.model.crewmate.Crew;

import org.jspecify.annotations.Nullable;

public class Barracks extends Station{
    private static Barracks instance;

    public static Barracks getInstance(@Nullable int maxCrew) {
        if (instance == null) {
            instance = new Barracks(maxCrew);
        }
        return instance;
    }
    public static Barracks getInstance() {return instance;}

    private Barracks(int maxCrew) {
        super(maxCrew);
    }

    @Override
    public void setEfficiency() {
        return;
    }



}
