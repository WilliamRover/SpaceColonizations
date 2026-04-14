package com.example.spacecolonizations.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacecolonizations.NameGen;
import com.example.spacecolonizations.R;
import com.example.spacecolonizations.model.Statistics;
import com.example.spacecolonizations.model.mission.PassObstacle;
import com.example.spacecolonizations.model.mission.obstacle.Asteroid;
import com.example.spacecolonizations.model.mission.obstacle.EngineFailure;
import com.example.spacecolonizations.model.shop.Wallet;

public class AsteroidActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asteroid);

        TextView textView3 = findViewById(R.id.textView5);
        Button buttonContinueAsteroid = findViewById(R.id.btnContinueAsteroid);

        PassObstacle passObstacle = new PassObstacle(NameGen.nGen((int) ((Math.random()*5) + 3)));
        Asteroid as = new Asteroid();
        passObstacle.setObstaclesType(as);
        passObstacle.finallisePassObstacle();
        if(passObstacle.getComplete()){
            Wallet.getInstance().addBalance(40+(int)(Math.random()*11));
            textView3.setText("There's a technicien on command station and able to resolve the situation");

        } else{
            textView3.setText("There is no technicien on command station and situation spirals out of control");
        }

        buttonContinueAsteroid.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

    }
}
