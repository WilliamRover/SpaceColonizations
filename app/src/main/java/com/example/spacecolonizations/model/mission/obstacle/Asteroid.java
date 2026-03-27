package com.example.spacecolonizations.model.mission.obstacle;



public class Asteroid extends Obstacle{
    public Asteroid (){
        super(1, 1);
    }

    @Override
    public void setUp(){
        addLocationJob("CommandCenter","Navigator");
    }

    @Override
    public String returnType(){
        return "Asteroid";
    }


}
