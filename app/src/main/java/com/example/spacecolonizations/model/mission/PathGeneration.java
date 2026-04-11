package com.example.spacecolonizations.model.mission;


import static com.example.spacecolonizations.NameGen.nGen;
import com.example.spacecolonizations.NameGen;
import com.example.spacecolonizations.model.mission.FightEnemy;
import com.example.spacecolonizations.model.mission.PassObstacle;
import com.example.spacecolonizations.model.mission.Rescue;

import java.util.ArrayList;
import java.util.HashMap;

public class PathGeneration {

    private ArrayList<Mission> missionSelection;
    private ArrayList<Mission> missionRandom;
    private int numMission;

    private HashMap<Mission,Integer> missionPercent;

    public PathGeneration(){
        this.missionSelection = new ArrayList<>();
        this.missionRandom = new ArrayList<>();
        this.numMission = 2;
        this.missionPercent = new HashMap<>();

        PassObstacle po = new PassObstacle("Pass Obstacle");
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

    public HashMap<Mission,Integer> randomMissionPercent(){
        HashMap<Mission,Integer> hm = new HashMap<>();
        int fullper = 100;
        int FightEnemyChance = (int) (Math.random()*100);
        int PassObstacleRescueChance = 100 - FightEnemyChance;
        int PassObstacleChance = (int) (Math.random()*PassObstacleRescueChance);
        int RescueChance = PassObstacleRescueChance-PassObstacleChance;

        hm.put(missionRandom.get(2),FightEnemyChance);
        hm.put(missionRandom.get(0),PassObstacleChance);
        hm.put(missionRandom.get(1),RescueChance);
        return hm;

    }

    /**
     * please use this function after properRandomGeneration to get the percent of the mission of thoese mission tpe
     *
     */
    public HashMap<Mission,Integer> getMissionPercent(){
        return missionPercent;
    }
    public ArrayList<Mission> properRandomGeneration(){
        missionPercent = randomMissionPercent();
        return GenerateControlPath(missionPercent.get(missionRandom.get(0)),missionPercent.get(missionRandom.get(1)),missionPercent.get(missionRandom.get(2)));

    }

    public ArrayList<Mission> GenerateControlPath(int PassObstacleChance, int RescueChance, int FightEnemyChance){

        missionSelection.clear();

        int chanceOfEverything = PassObstacleChance + RescueChance + FightEnemyChance;

        for (int i =0; i<numMission;i++){
            int RandomChance = (int) (Math.random()*chanceOfEverything);
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
