package com.example.spacecolonizations;

import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.mission.Mission;
import com.example.spacecolonizations.model.station.Station;
import java.util.ArrayList;

public class ObstacleCheck {


    /**
     * CheckPasssObstacle work by giving it the mission and array of all station want to check and
     * it will run through the hashmap of station and expected job of the crew if not found it will
     * output as False
     * - if station arraylist is null it will return true
     * - if missionType != pass obstacle it will return true
     *
     */
    public boolean checkPassObstacle(Mission m, ArrayList<Station> s) {

        if (m == null) {
            return true;
        }

        if (s == null) {
            return true;
        }

        int size = m.getLocationJob().size();

        for (Station t : s) {
            boolean found = false;

            if (m.getLocationJob().get(t) != null) {
                Crew requiredCrew = m.getLocationJob().get(t);

                for (Crew inStation : t.getCrewMembers()) {
                    if (requiredCrew.equals(inStation)) {
                        found = true;
                        size--;
                        break;
                    }
                }

                if (!found) {
                    return false;
                }
            }
        }

        return size == 0;
    }
}