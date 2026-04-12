package com.example.spacecolonizations.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacecolonizations.NameGen;
import com.example.spacecolonizations.R;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.CrewManager;
import com.example.spacecolonizations.model.mission.Rescue;

import java.util.ArrayList;

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

        TextView textView5 = findViewById(R.id.textView5);

        ArrayList<String> crewName = new ArrayList<>();

        Rescue rescue = new Rescue(NameGen.nGen((int) ((Math.random()*5) + 3)));
        for (int i = 0; i<2; i++){
            Crew c = CrewManager.getCrew().get((int) (Math.random()*CrewManager.getCrew().size()));
            rescue.addCrew(c);
            crewName.add(c.getName());
        }
        String outRescueTxt = crewName.get(0) + " and " + crewName.get(1) + " have been sent to aid ally ships";
        textView5.setText(outRescueTxt);
    }
}
