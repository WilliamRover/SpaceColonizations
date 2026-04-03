package com.example.spacecolonizations.model.mission;

import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.CrewManager;
import com.example.spacecolonizations.model.station.Barracks;

import java.util.ArrayList;
import java.util.List;
public class Rescue extends Mission {
    private ArrayList<Crew> crewMembers;

    private int timeRequire;

    private int timeRightNow;
    public Rescue(String missionName, int numCrew){
        super(missionName, numCrew);
        this.crewMembers = new ArrayList<>();
        this.timeRequire = 2;
        this.timeRightNow = 0;
    }
    @Override
    public String getMissionType(){
        return "Rescue";
    }

    public int getTimeRequire(){
        return timeRequire;
    }

    public int getTimeRightNow(){
        return timeRightNow;
    }

    public void addTime(){
        timeRightNow = timeRightNow + 1;
    }

    public boolean checkTime(){
        if (timeRightNow >= timeRequire){
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
                c.setCanWork(false);
            } else if (dice<=51) {
                c.loseHealth((int) ((c.getMaxHealthPoints())*(dice2/100)));
                if (c.getHealthPoints() == 0){
                    c.setCanWork(false);
                }
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
        if (crew.getHealthPoints()!=0){
            CrewManager.removeCrew(crew);
        }
        else {
            crew.setCanWork(true);
            Barracks.getInstance().assignCrew(crew);
        }

    }
    public List<Crew> getCrewMembers(){
        return crewMembers;
    }
}
