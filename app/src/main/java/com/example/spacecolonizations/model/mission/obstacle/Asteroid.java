package com.example.spacecolonizations.model.mission.obstacle;


import com.example.spacecolonizations.model.crewmate.Commander;
import com.example.spacecolonizations.model.crewmate.Navigator;
import com.example.spacecolonizations.model.station.CommandCenter;

public class Asteroid extends Obstacle{
    public Asteroid (){
        super(1, 1);
        CommandCenter c = new CommandCenter();
        Navigator s = new Navigator("a",1,1);
        Commander cm = new Commander("cm",1,1);
        addLocationJob(c,s);
        addLocationJob(c,cm);
    }


    @Override
    public String returnType(){
        return "Asteroid";
    }


}
