package com.example.spacecolonizations.model.mission;

import com.example.spacecolonizations.model.crewmate.Crew;
import java.util.ArrayList;
import java.util.List;
public class Rescue extends mission {
    private List<Crew> crewMembers;
    public Rescue(String missionName, int numCrew){
        super(missionName, numCrew);
        this.crewMembers = new ArrayList<>();
    }
    @Override
    public String getMissionType(){
        return "Rescue";
    }

    public void addCrew(Crew crew){
        if (crewMembers.size() >= numCrew){
            return;
        }
        crewMembers.add(crew);
    }
    public void removeCrew(Crew crew){
        crewMembers.remove(crew);
    }
    public List<Crew> getCrewMembers(){
        return crewMembers;
    }
}
