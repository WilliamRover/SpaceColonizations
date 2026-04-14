package com.example.spacecolonizations.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacecolonizations.NameGen;
import com.example.spacecolonizations.R;
import com.example.spacecolonizations.adapter.TotalCrewAdapter;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.CrewManager;
import com.example.spacecolonizations.model.mission.Rescue;

import java.util.ArrayList;
import java.util.List;

public class RescueActivity extends AppCompatActivity {
    private Rescue rescue;
    private TotalCrewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rescue_mission);

        TextView textViewMaxCrew = findViewById(R.id.txtViewMaxCrewRescue);
        Button btnContinueRescue = findViewById(R.id.btnContinueRescue);

        // Initialize Rescue Mission
        rescue = new Rescue(NameGen.nGen((int) ((Math.random() * 5) + 3)));
        int requiredCrewCount = rescue.getNumCrew();
        String outRescueTxt = "Select " + requiredCrewCount + " to aid ally ship";
        textViewMaxCrew.setText(outRescueTxt);

        // Setup RecyclerView
        View crewListInclude = findViewById(R.id.crewListInclude);
        RecyclerView recViewTotalCrew = crewListInclude.findViewById(R.id.recViewTotalCrew);
        TextView titleTxt = crewListInclude.findViewById(R.id.txtViewCrewListTotal);
        titleTxt.setText("Available Crew Members");

        // Filter available crew members who can actually work (not on mission, not dead, not patient)
        List<Crew> availableCrews = new ArrayList<>();
        for (Crew c : CrewManager.getCrew()) {
            if (c.getCanWork() && !c.isPatient() && c.getHealthPoints() > 0) {
                availableCrews.add(c);
            }
        }

        if (availableCrews.size() < requiredCrewCount) {
            Toast.makeText(this, "Not enough healthy crew members available!", Toast.LENGTH_LONG).show();
            // We still show the list, but they can't proceed
        }

        adapter = new TotalCrewAdapter(availableCrews, requiredCrewCount, selectedCrews -> {
            boolean isReady = selectedCrews.size() == requiredCrewCount;
            btnContinueRescue.setEnabled(isReady);
            btnContinueRescue.setAlpha(isReady ? 1.0f : 0.5f);
            
            String updatedTxt = "Selected " + selectedCrews.size() + " / " + requiredCrewCount;
            textViewMaxCrew.setText(updatedTxt);
        });

        recViewTotalCrew.setLayoutManager(new LinearLayoutManager(this));
        recViewTotalCrew.setAdapter(adapter);

        btnContinueRescue.setAlpha(0.5f);
        btnContinueRescue.setEnabled(false);
        btnContinueRescue.setOnClickListener(v -> {
            List<Crew> selected = adapter.getSelectedCrews();
            if (selected.size() == requiredCrewCount) {
                // Add selected crew to mission
                for (Crew c : selected) {
                    rescue.addCrew(c);
                }
                
                // Add to manager and start
                CrewManager.addRescueMission(rescue);
                rescue.start();
                
                // Save state
                CrewManager.saveTOFile(this);
                
                Toast.makeText(this, "Rescue mission launched!", Toast.LENGTH_SHORT).show();
                
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
