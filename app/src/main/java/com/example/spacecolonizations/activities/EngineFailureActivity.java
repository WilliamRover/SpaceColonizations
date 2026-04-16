package com.example.spacecolonizations.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacecolonizations.NameGen;
import com.example.spacecolonizations.R;
import com.example.spacecolonizations.model.Statistics;
import com.example.spacecolonizations.model.mission.Mission;
import com.example.spacecolonizations.model.mission.PassObstacle;
import com.example.spacecolonizations.model.mission.obstacle.EngineFailure;
import com.example.spacecolonizations.model.ship.FriendlyShip;
import com.example.spacecolonizations.model.shop.Wallet;

public class EngineFailureActivity extends AppCompatActivity {
    private Button buttonContinueEngine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.engine_failure);

        TextView textView3 = findViewById(R.id.textView3);
        buttonContinueEngine = findViewById(R.id.btnContinueEngine);
        ImageView imgEngineStatus = findViewById(R.id.imgEngineStatus);

        PassObstacle passObstacle = new PassObstacle(NameGen.nGen((int) ((Math.random()*5) + 3)));
        EngineFailure ef = new EngineFailure();
        passObstacle.setObstaclesType(ef);
        passObstacle.finallisePassObstacle();
        if(passObstacle.getComplete()){
            Wallet.getInstance().addBalance(40+(int)(Math.random()*11));
            textView3.setText("Technician performed engineer magics");
            imgEngineStatus.setImageResource(R.drawable.engine_fixed);
        } else{
            textView3.setText("Technician was not summoned (technician missing from command center)");
            imgEngineStatus.setImageResource(R.drawable.engine_not_fixed);
            Toast.makeText(this, "Check broken station lists", Toast.LENGTH_SHORT).show();
        }

        buttonContinueEngine.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            FriendlyShip.getShip().resetHp();
            startActivity(intent);
        });

    }
}
