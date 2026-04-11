package com.example.spacecolonizations.model.mission;

import com.example.spacecolonizations.activities.FightEnemyActivity;
import com.example.spacecolonizations.model.ship.EnemyShip;

import java.util.ArrayList;

/**
 * fight enemy subclass of mission
 * missionName -> name of the mission in string
 * numCrew -> number of crew members require in the mission
 */
public class FightEnemy extends Mission {
    public FightEnemy(String missionName){
        super(missionName);
    }

    @Override
    public String getMissionType(){
        return "Fight enemy";
    }

    public void setUp(){

    }

    // TODO remember to pick a random crew from a random station and damage them a bit

}
