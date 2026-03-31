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
    public boolean checkPassObstacle(Mission m, ArrayList<Station> stations) {
        if (m == null || stations == null) {
            return true;
        }

        for (Station station : stations) {
            ArrayList<Crew> requiredCrew = m.getLocationJob().get(station);

            if (requiredCrew != null) {
                boolean allCrewFound = true;

                for (Crew crew : requiredCrew) {
                    if (!station.getCrewMembers().contains(crew)) {
                        allCrewFound = false;
                        break;
                    }
                }

                if (!allCrewFound) {
                    return false;
                }
            }
        }

        return true;
    }
}