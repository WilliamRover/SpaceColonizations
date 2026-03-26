package com.example.spacecolonizations.model.mission;

public class mission {
    String missionName;
    int numCrew;
    Boolean Complete;
    public mission(String missionName, int numCrew){
        this.missionName = missionName;
        this.numCrew = numCrew;
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
        Complete = n;
    }
}
