package com.example.spacecolonizations.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacecolonizations.NameGen;
import com.example.spacecolonizations.R;
import com.example.spacecolonizations.model.crewmate.CrewManager;
import com.example.spacecolonizations.model.mission.Rescue;

public class RescueActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rescue_mission);

        Button btnContinueRescue = findViewById(R.id.btnContinueRescue);
        btnContinueRescue.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

        Rescue rescue = new Rescue(NameGen.nGen((int) ((Math.random()*5) + 3)));
    }
}
