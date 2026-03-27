package com.example.spacecolonizations.model.mission.obstacle;

public class EngineFailure extends Obstacle{
    public EngineFailure(){
        super(1,1);
    }

    @Override
    public void setUp(){
        addLocationJob("CommandCenter","Technician");
    }

    @Override
    public String returnType(){
        return "EngineFailure";
    }
}
