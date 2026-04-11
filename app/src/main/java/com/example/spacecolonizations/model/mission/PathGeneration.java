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

    private HashMap<String,Integer> missionPercent;

    public PathGeneration(){
        this.missionSelection = new ArrayList<>();
        this.missionRandom = new ArrayList<>();
        this.numMission = 1;
        this.missionPercent = new HashMap<>();

        PassObstacle po = new PassObstacle("Pass Obstacle");
        Rescue r = new Rescue("Rescue");
        FightEnemy fe = new FightEnemy("Fight Enemy");

        missionRandom.add(po);
        missionRandom.add(r);
        missionRandom.add(fe);

    }

    public int getNumMission(){
        return numMission;
    }
    public void changeNumMission (int n){
        numMission = n;
    }

    public HashMap<String,Integer> randomMissionPercent(){
        HashMap<String,Integer> hm = new HashMap<>();
        int fullper = 100;
        int FightEnemyChance = (int) (Math.random()*100);
        int PassObstacleRescueChance = 100 - FightEnemyChance;
        int PassObstacleChance = (int) (Math.random()*PassObstacleRescueChance);
        int RescueChance = PassObstacleRescueChance-PassObstacleChance;

        hm.put("FightEnemy",FightEnemyChance);
        hm.put("PassObstacle",PassObstacleChance);
        hm.put("Rescue",RescueChance);
        return hm;

    }

    /**
     * please use this function after properRandomGeneration to get the percent of the mission of thoese mission tpe
     *
     */
    public HashMap<String,Integer> getMissionPercent(){
        return missionPercent;
    }
    public Mission properRandomGeneration(){
        missionPercent = randomMissionPercent();
        return GenerateControlPath(missionPercent.get("PassObstacle"),missionPercent.get("Rescue"),missionPercent.get("FightEnemy")).get(0);

    }

    public ArrayList<Mission> GenerateControlPath(int PassObstacleChance, int RescueChance, int FightEnemyChance){

        missionSelection.clear();
        int chanceOfEverything = PassObstacleChance + RescueChance + FightEnemyChance;

        int RandomChance = (int) (Math.random()*chanceOfEverything);
        if (RandomChance<PassObstacleChance){
            missionSelection.add(missionRandom.get(0));
        } else if (RandomChance<(PassObstacleChance+RescueChance)) {
            missionSelection.add(missionRandom.get(1));
        } else {
            missionSelection.add(missionRandom.get(2));
        }


        return missionSelection;

    }

    public ArrayList<Mission> GenerateNormalPath(){

        missionSelection.clear();

        for (int i =0; i<numMission;i++){
            missionSelection.add(missionRandom.get((int)(Math.random() * missionRandom.size())));
        }

        return missionSelection;

    }
}
