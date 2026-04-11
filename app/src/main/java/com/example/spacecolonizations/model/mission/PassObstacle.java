package com.example.spacecolonizations.model.mission;

import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.CrewManager;
import com.example.spacecolonizations.model.mission.obstacle.Asteroid;
import com.example.spacecolonizations.model.mission.obstacle.EngineFailure;
import com.example.spacecolonizations.model.mission.obstacle.Obstacle;
import com.example.spacecolonizations.model.station.Barracks;
import com.example.spacecolonizations.model.station.Station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PassObstacle extends Mission {

    private ArrayList<Obstacle> obstacles;
    private Asteroid asteroid;
    private EngineFailure engineFailure;
    private Obstacle e;
    private Obstacle m;



    public PassObstacle(String missionName){
        super(missionName);

        this.obstacles = new ArrayList<Obstacle>();

        this.asteroid = new Asteroid();
        this.asteroid.setUp();

        this.engineFailure = new EngineFailure();
        this.engineFailure.setUp();


        obstacles.add(asteroid);
        obstacles.add(engineFailure);
        this.e = randomObstacle(obstacles);

    }

    public Obstacle randomObstacle(ArrayList<Obstacle> obstacles) {
        int randomIndex = (int) (Math.random() * obstacles.size());
        return obstacles.get(randomIndex);
    }

    @Override
    public String getMissionType(){
        return e.returnType();
    }

    @Override
    public HashMap<Station, ArrayList<Crew>> getLocationJob(){
        return e.getLocationJob();
    }

    /**
     * checkPasssObstacle work by giving it the mission and list of all station want to check and
     * it will run through the hashmap of station and expected job of the crew if not found it will
     * output as False
     * - if station list is null it will return true
     * - if missionType != pass obstacle it will return true
     *
     */
    public boolean checkPassObstacle() {
        m = e;
        List<Station> stations = CrewManager.getStations();
        stations.remove(Barracks.getInstance());
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

    public void damageStation(){
        List<Station> stations = CrewManager.getStations();
        stations.remove(Barracks.getInstance());
        stations.get((int) (Math.random()*stations.size())).breakStation();

    }

    public void finallisePassObstacle(){
        if(checkPassObstacle()){
            setComplete(true);
        }else {
            setComplete(false);
            damageStation();
        }
    }
}

