package com.example.spacecolonizations.model.station;


import com.example.spacecolonizations.model.crewmate.Crew;

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

        singleton.crewMembers = this.crewMembers;
        singleton.maxCrew = this.maxCrew;

        if (!this.crewMembers.isEmpty()) {
            for (Crew crew : this.crewMembers) {
                crew.setCurrentStation(singleton);
            }

        }

        return singleton;
    }



}
