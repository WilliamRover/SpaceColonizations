package com.example.spacecolonizations.model.mission;

import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.CrewManager;
import com.example.spacecolonizations.model.shop.Wallet;
import com.example.spacecolonizations.model.station.Barracks;

import java.util.ArrayList;
import java.util.List;

// TODO increase crew limit depending on stats
public class Rescue extends Mission {
    private ArrayList<Crew> crewMembers;

    private int turnRequire;

    private int turnRightNow;
    public Rescue(String missionName){
        super(missionName);
        this.numCrew = 2;
        this.crewMembers = new ArrayList<>();
        this.turnRequire = 2;
        this.turnRightNow = 0;
        CrewManager.addRescueMission(this);
    }
    @Override
    public String getMissionType(){
        return "Rescue";
    }

    public int getTurnRequire(){
        return turnRequire;
    }

    public int getTurnRightNow(){
        return turnRightNow;
    }

    public void addTime(){
        turnRightNow = turnRightNow + 1;
    }

    public boolean checkTime(){
        if (turnRightNow >= turnRequire){
            return true;
        }
        return false;
    }

    public void damageCrew(){
        for (Crew c : crewMembers){
            double dice = Math.random()*100;
            double dice2 = (Math.random()*40)+40;
            if (dice<=1){
                c.loseHealth(c.getMaxHealthPoints());
            } else if (dice<=51) {
                c.loseHealth((int) ((c.getMaxHealthPoints())*(dice2/100)));
            }
        }
    }

    /**
     * To be called when mission is complete
     * @return The list of crew members that were assigned to the mission
     */
    public ArrayList<Crew> returnCrew(){
        damageCrew();
        ArrayList<Crew> tempCrew = new ArrayList<>(crewMembers);
        for (int i = crewMembers.size() - 1; i >= 0; i--){

            Crew c = crewMembers.get(i);
            if (c.getHealthPoints()>0){
                setComplete(true);
            }
            removeCrew(c);
        }
        // TODO update statistics

        //reward
        if (getComplete()){
            Wallet.getInstance().addBalance(150);
        }
        CrewManager.removeRescueMission(this);

        return tempCrew;
    }

    public void addCrew(Crew crew){
        if (crewMembers.size() >= numCrew || !crew.getCanWork()){
            return;
        }

        if (crew.getCurrentStation() != null){
            crew.getCurrentStation().removeCrew(crew);
        }

        crewMembers.add(crew);
        crew.setCanWork(false);

    }

    public void removeCrew(Crew crew){
        crewMembers.remove(crew);
        crew.setCanWork(crew.getHealthPoints() != 0);
        Barracks.getInstance().assignCrew(crew);

    }

    public List<Crew> getCrewMembers(){
        return crewMembers;
    }
}
