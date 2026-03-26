package com.example.spacecolonizations.model.mission;

/**
 * fight enemy subclass of mission
 * missionName -> name of the mission in string
 * numCrew -> number of crew members require in the mission
 */
public class FightEnemy extends mission{
    public FightEnemy(String missionName, int numCrew){
        super(missionName, numCrew);
    }

    @Override
    public String getMissionType(){
        return "Fight enemy";
    }

}
