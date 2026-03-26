package com.example.spacecolonizations.model.mission;

public class Rescue extends mission {
    public Rescue(String missionName, int numCrew){
        super(missionName, numCrew);
    }
    @Override
    public String getMissionType(){
        return "Rescue";
    }
}
