package com.example.spacecolonizations.model.mission.obstacle;

import com.example.spacecolonizations.model.crewmate.Navigator;
import com.example.spacecolonizations.model.crewmate.Technician;
import com.example.spacecolonizations.model.station.CommandCenter;

public class EngineFailure extends Obstacle{
    public EngineFailure(){
        super(1,1);
        CommandCenter c = new CommandCenter();
        Technician s = new Technician("a",1,1);
        addLocationJob(c,s);
    }


    @Override
    public String returnType(){
        return "EngineFailure";
    }
}
