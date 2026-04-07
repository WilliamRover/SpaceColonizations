package com.example.spacecolonizations.model.mission;


import static com.example.spacecolonizations.NameGen.nGen;
import com.example.spacecolonizations.NameGen;
import com.example.spacecolonizations.model.mission.FightEnemy;
import com.example.spacecolonizations.model.mission.PassObstacle;
import com.example.spacecolonizations.model.mission.Rescue;

import java.util.ArrayList;

public class PathGeneration {

    public static ArrayList<Mission> generatePath(){

        PassObstacle po = new PassObstacle("PassObstacle");
        Rescue r = new Rescue("Rescue");
        FightEnemy fe = new FightEnemy("Fight Enemy");

        ArrayList<Mission> missionSelection = new ArrayList<>();
        missionSelection.add(po);
        missionSelection.add(r);
        missionSelection.add(fe);

        ArrayList<Mission> missionRandom = new ArrayList<>();
        int numMission = 3;

        for (int i =0; i<numMission;i++){
            missionRandom.add(missionSelection.get((int)(Math.random() * missionSelection.size())));
        }

        return missionRandom;

    }
}
