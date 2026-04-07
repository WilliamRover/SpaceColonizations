package com.example.spacecolonizations.model.mission;

import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.CrewManager;
import com.example.spacecolonizations.model.station.Barracks;

import java.util.ArrayList;
import java.util.List;
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
    public ArrayList<Crew> returnCrew(){
        damageCrew();
        ArrayList<Crew> tempCrew = crewMembers;
        for (Crew i : crewMembers){
            if (i.getHealthPoints()>0){
                setComplete(true);
            }
            removeCrew(i);
        }

        return tempCrew;
    }

    public void addCrew(Crew crew){
        if (crewMembers.size() >= numCrew){
            return;
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
