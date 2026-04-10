package com.example.spacecolonizations.model.station;


import com.example.spacecolonizations.model.crewmate.Crew;

import java.util.ArrayList;

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

    //Barracks is meant to be a safe place
    //does not require repair or explosion or breaking
    @Override
    public void breakStation() {
        return;
    }

    @Override
    protected void initRepairHandler() {return;}

    @Override
    protected void initBreakHandler() {return;}

    @Override
    protected boolean isSingleton() {
        return true;
    }


    // used to resolve serialization issues with singleton pattern and possible ghost objects
    private Object readResolve() {
        Barracks singleton = getInstance();

        if (this.crewMembers != null) {
            singleton.crewMembers = this.crewMembers;
            for (Crew crew : singleton.crewMembers) {
                crew.setCurrentStation(singleton);
            }
        } else {
            singleton.crewMembers = new ArrayList<>();
        }

        singleton.maxCrew = this.maxCrew;

        return singleton;
    }



}
