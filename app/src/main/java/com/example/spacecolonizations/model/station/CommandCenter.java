package com.example.spacecolonizations.model.station;


public class CommandCenter extends Station{
    public CommandCenter() {
        super();
        this.maxCrew = 2;
    }

    @Override
    protected void setEfficiency() {
        return;
    }

}
