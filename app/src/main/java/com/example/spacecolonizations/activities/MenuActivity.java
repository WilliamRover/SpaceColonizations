package com.example.spacecolonizations.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacecolonizations.R;
import com.example.spacecolonizations.model.crewmate.CrewManager;
import com.example.spacecolonizations.model.station.Barracks;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        setupButton();
    }

    private void setupButton() {
        Button btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(v -> {
            CrewManager.loadFromFile(this);
            CrewManager.getStations();
            CrewManager.getCrew();

            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

        Button btnNewGame = findViewById(R.id.btnNewGame);
        btnNewGame.setOnClickListener(v -> {
            CrewManager.deleteSave(this);
            CrewManager.loadFromFile(this);
            CrewManager.getStations();
            if (!Barracks.getInstance().getCrewMembers().isEmpty()){
                Barracks.getInstance().getCrewMembers().clear();
            }
            CrewManager.getCrew();

            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

        Button btnCredits = findViewById(R.id.btnCredits);
        btnCredits.setOnClickListener((v -> {
            Intent intent = new Intent(this, CreditActivity.class);
            startActivity(intent);
        }));
    }
}
