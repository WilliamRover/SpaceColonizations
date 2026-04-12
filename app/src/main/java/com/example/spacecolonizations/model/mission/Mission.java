package com.example.spacecolonizations.model.mission;

import com.example.spacecolonizations.model.Statistics;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.shop.Wallet;
import com.example.spacecolonizations.model.station.Station;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Mission implements Serializable {
    String missionName;
    int numCrew;
    Boolean Complete;
    public Mission(String missionName){
        this.numCrew = 1;
        this.missionName = missionName;
        this.Complete = false;
    }

    public String getMissionName(){
        return missionName;
    }
    public String getMissionType(){
        return "";
    }
    public int getNumCrew(){
        return numCrew;
    }
    public Boolean getComplete(){
        return Complete;
    }
    public void setComplete(Boolean n){
        Statistics stats = Statistics.getInstance();
        if (n){
            stats.setNumSuccessfulMissions(stats.getNumSuccessfulMissions() + 1);
            Wallet.getInstance().addBalance(100);
        } else {
            stats.setNumFailedMissions(stats.getNumFailedMissions() + 1);
        }
        Complete = n;
    }

    public HashMap<Station, ArrayList<Crew>> getLocationJob(){
        return null;
    }
}
