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

        this.engineFailure = new EngineFailure();


        obstacles.add(asteroid);
        obstacles.add(engineFailure);

        if (!obstacles.isEmpty()) {
            this.e = randomObstacle(obstacles);
        }

    }

    public Obstacle randomObstacle(ArrayList<Obstacle> obstacles) {
        int randomIndex = (int) (Math.random() * obstacles.size());
        return obstacles.get(randomIndex);
    }


    public void setObstaclesType(Obstacle o){
        e = o;
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
        List<Station> stations = CrewManager.getStations();

        if (e == null || stations == null) {
            return true;
        }

        HashMap<Station, ArrayList<Crew>> reqMap = e.getLocationJob();

        for (Station station : stations) {

            if (station instanceof Barracks) continue;

            ArrayList<Crew> requiredCrew = null;

            // Match by CLASS instead of instance
            for (Station reqStation : reqMap.keySet()) {
                if (reqStation.getClass().equals(station.getClass())) {
                    requiredCrew = reqMap.get(reqStation);
                    break;
                }
            }

            if (requiredCrew != null) {

                for (Crew required : requiredCrew) {
                    boolean found = false;

                    for (Crew actual : station.getCrewMembers()) {
                        if (actual.getClass().equals(required.getClass())) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public void damageStation(){
        List<Station> stations = new ArrayList<>(CrewManager.getStations());

        stations.remove(Barracks.getInstance());

        if (stations.isEmpty()) return; // prevent crash

        stations.get((int) (Math.random() * stations.size())).breakStation();
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

