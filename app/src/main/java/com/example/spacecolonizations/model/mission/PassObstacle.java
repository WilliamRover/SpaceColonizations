package com.example.spacecolonizations.model.mission;

public class PassObstacle extends mission{
    public PassObstacle(String missionName, int numCrew){
        super(missionName, numCrew);
    }
    @Override
    public String getMissionType(){
        return "Pass obstacle";
    }
}
