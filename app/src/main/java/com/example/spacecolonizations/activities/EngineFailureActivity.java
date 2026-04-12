package com.example.spacecolonizations.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacecolonizations.NameGen;
import com.example.spacecolonizations.R;
import com.example.spacecolonizations.model.mission.Mission;
import com.example.spacecolonizations.model.mission.PassObstacle;
import com.example.spacecolonizations.model.mission.obstacle.EngineFailure;

public class EngineFailureActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.engine_failure);

        PassObstacle passObstacle = new PassObstacle(NameGen.nGen((int) ((Math.random()*5) + 3)));
        EngineFailure ef = new EngineFailure();
        passObstacle.setObstaclesType(ef);
        passObstacle.finallisePassObstacle();
    }
}
