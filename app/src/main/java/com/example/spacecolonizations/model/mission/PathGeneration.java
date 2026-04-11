package com.example.spacecolonizations.model.mission;


import static com.example.spacecolonizations.NameGen.nGen;
import com.example.spacecolonizations.NameGen;
import com.example.spacecolonizations.model.mission.FightEnemy;
import com.example.spacecolonizations.model.mission.PassObstacle;
import com.example.spacecolonizations.model.mission.Rescue;

import java.util.ArrayList;

public class PathGeneration {

    private ArrayList<Mission> missionSelection;
    private ArrayList<Mission> missionRandom;
    private int numMission;

    public PathGeneration(){
        this.missionSelection = new ArrayList<>();
        this.missionRandom = new ArrayList<>();
        this.numMission = 3;

        PassObstacle po = new PassObstacle("PassObstacle");
        Rescue r = new Rescue("Rescue");
        FightEnemy fe = new FightEnemy("Fight Enemy");

        missionSelection.add(po);
        missionSelection.add(r);
        missionSelection.add(fe);

    }

    public int getNumMission(){
        return numMission;
    }
    public void changeNumMission (int n){
        numMission = n;
    }

    public ArrayList<Mission> GenerateControlPath(float PassObstacleChance, float RescueChance, float FightEnemyChance){

        missionSelection.clear();

        float chanceOfEverything = PassObstacleChance + RescueChance + FightEnemyChance;

        for (int i =0; i<numMission;i++){
            float RandomChance = (float) Math.random()*chanceOfEverything;
            if (RandomChance<PassObstacleChance){
                missionSelection.add(missionRandom.get(0));
            } else if (RandomChance<(PassObstacleChance+RescueChance)) {
                missionSelection.add(missionRandom.get(1));
            } else {
                missionSelection.add(missionRandom.get(2));
            }
        }

        return missionSelection;

    }

    public ArrayList<Mission> GenerateNormalPath(){

        missionSelection.clear();

        for (int i =0; i<numMission;i++){
            missionRandom.add(missionSelection.get((int)(Math.random() * missionSelection.size())));
        }

        return missionRandom;

    }
}
