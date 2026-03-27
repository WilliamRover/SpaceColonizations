package com.example.spacecolonizations.model.mission;

import com.example.spacecolonizations.model.mission.obstacle.Asteroid;
import com.example.spacecolonizations.model.mission.obstacle.EngineFailure;
import com.example.spacecolonizations.model.mission.obstacle.Obstacle;

import java.util.ArrayList;
public class PassObstacle extends Mission {

    private ArrayList<Obstacle> obstacles;
    private Asteroid asteroid;
    private EngineFailure engineFailure;
    private Obstacle e;



    public PassObstacle(String missionName, int numCrew){
        super(missionName, numCrew);
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
}
