package com.example.spacecolonizations.model.station;


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
    public void explode() {
        return;
    }

    @Override
    protected void initRepairHandler() {return;}

    @Override
    protected void initBreakHandler() {return;}


    // used to resolve serialization issues with singleton pattern
    private Object readResolve() {
        return getInstance();
    }



}
