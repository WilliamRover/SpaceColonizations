package com.example.spacecolonizations.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacecolonizations.NameGen;
import com.example.spacecolonizations.R;
import com.example.spacecolonizations.model.mission.PassObstacle;
import com.example.spacecolonizations.model.mission.obstacle.Asteroid;
import com.example.spacecolonizations.model.mission.obstacle.EngineFailure;

public class AsteroidActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asteroid);

        PassObstacle passObstacle = new PassObstacle(NameGen.nGen((int) ((Math.random()*5) + 3)));
        Asteroid as = new Asteroid();
        passObstacle.setObstaclesType(as);
    }
}
