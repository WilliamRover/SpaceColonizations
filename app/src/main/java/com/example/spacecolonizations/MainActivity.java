package com.example.spacecolonizations;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacecolonizations.model.crewmate.Commander;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Gunner;
import com.example.spacecolonizations.model.crewmate.Medic;
import com.example.spacecolonizations.model.crewmate.Navigator;
import com.example.spacecolonizations.model.crewmate.Technician;
import com.example.spacecolonizations.model.ship.EnemyShip;
import com.example.spacecolonizations.model.ship.FriendlyShip;
import com.example.spacecolonizations.model.station.Barracks;
import com.example.spacecolonizations.model.station.CommandCenter;
import com.example.spacecolonizations.model.station.MedBay;
import com.example.spacecolonizations.model.station.Station;
import com.example.spacecolonizations.model.station.TrainingCenter;
import com.example.spacecolonizations.model.station.Turret;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.spacecolonizations.adapter.StationAdapter;

public class MainActivity extends AppCompatActivity {
    // Ship attributes
    private FriendlyShip friendlyShip;
    private EnemyShip enemyShip;
    private ProgressBar friendlyHpBar;
    private ImageView enemyShipImage;
    private ImageView friendlyShipImage;
    private ProgressBar enemyHpBar;
    private TextView friendlyExplode;
    private TextView enemyExplode;
    private TextView friendlyHpTxt;
    private TextView enemyHpTxt;
    private TextView stationNameTxt;
    private MaterialCardView stationCard;

    private RecyclerView recyclerView;
    private StationAdapter adapter;


    private Runnable runnable;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Do some shenanigans from here

        innitView();
        innitButtons();



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void innitView() {
        // Ship
        friendlyShip = new FriendlyShip(100);
        enemyShip = new EnemyShip(120);

        // Add sample crew and assign to stations
        addSampleCrews();

        friendlyHpBar = findViewById(R.id.shipHpBar);
        enemyHpBar = findViewById(R.id.enemyShipHp);
        friendlyShipImage = findViewById(R.id.friendlyShipModel);
        enemyShipImage = findViewById(R.id.enemyShipModel);
        friendlyExplode = findViewById(R.id.friendlyExplode);
        enemyExplode = findViewById(R.id.enemyExplode);
        friendlyHpTxt = findViewById(R.id.friendlyHpTxt);
        enemyHpTxt = findViewById(R.id.enemyHpTxt);
        
        stationCard = findViewById(R.id.stationCard);
        stationNameTxt = findViewById(R.id.txtViewStationName);

        // Set max values for progress bars
        friendlyHpBar.setMax(friendlyShip.getInnitHullStrength());
        enemyHpBar.setMax(enemyShip.getInnitHullStrength());

        // Initial set
        enemyHpBar.setProgress(enemyShip.getHullStrength());
        friendlyHpBar.setProgress(friendlyShip.getHullStrength());
        
        friendlyHpTxt.setText(String.format("%d/%d", friendlyShip.getHullStrength(), friendlyShip.getInnitHullStrength()));
        enemyHpTxt.setText(String.format("%d/%d", enemyShip.getHullStrength(), enemyShip.getInnitHullStrength()));

        // Recycler view for crew
        recyclerView = findViewById(R.id.recViewCrew);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StationAdapter(friendlyShip.getCrews());
        recyclerView.setAdapter(adapter);
    }

    private void addSampleCrews() {
        // Create sample crew members
        Gunner crew1 = new Gunner("Jew Burner", 100, 100);
        Medic crew2 = new Medic("Black Plague", 80, 100);
        Commander crew3 = new Commander("Captain Failure", 120, 120);
        Technician crew4 = new Technician("I dont have sleep", 100, 100);
        Navigator crew5 = new Navigator("Nigger", 100, 100);
        Navigator crew6 = new Navigator("Best pilot", 10, 222);
        Gunner crew7 = new Gunner("Allahu Akbar", 100, 100);
        Gunner crew8 = new Gunner("Slave", 100, 100);
        Commander crew9 = new Commander("Corrupted", 200, 500);
        Technician crew10 = new Technician("Im alone mf", 20, 200);
        Technician crew11 = new Technician("Engineering Student 1", 20, 200);
        Technician crew12 = new Technician("Engineering Student 2", 20, 200);
        Technician crew13 = new Technician("Engineering Student 3", 20, 200);
        Technician crew14 = new Technician("Engineering Student 4", 20, 200);
        Technician crew15 = new Technician("Engineering Student 5", 20, 200);
        Technician crew16 = new Technician("Engineering Student 6", 20, 200);
        Technician crew17 = new Technician("Engineering Student 7", 20, 200);
        Technician crew18 = new Technician("Engineering Student 8", 20, 200);
        Technician crew19 = new Technician("Engineering Student 9", 20, 200);
        Technician crew20 = new Technician("Engineering Student 10", 20, 200);

        // Recruit them to the ship
        friendlyShip.recruitCrew(crew1);
        friendlyShip.recruitCrew(crew2);
        friendlyShip.recruitCrew(crew3);

        // Assign to stations
        for (Station station : friendlyShip.getStations()) {
            if (station instanceof Turret) {
                station.assignCrew(crew1);
                station.assignCrew(crew4);
            } else if (station instanceof MedBay) {
                station.assignCrew(crew2);
                station.assignCrew(crew5);
            } else if (station instanceof CommandCenter) {
                station.assignCrew(crew3);
                station.assignCrew(crew6);
            } else if (station instanceof TrainingCenter) {
                station.assignCrew(crew7);
                station.assignCrew(crew8);
                station.assignCrew(crew9);
            } else if (station instanceof Barracks) {
                station.assignCrew(crew10);
                station.assignCrew(crew11);
                station.assignCrew(crew12);
                station.assignCrew(crew13);
                station.assignCrew(crew14);
                station.assignCrew(crew15);
                station.assignCrew(crew16);
                station.assignCrew(crew17);
                station.assignCrew(crew18);
                station.assignCrew(crew19);
                station.assignCrew(crew20);
            }
        }
    }

    private void innitButtons() {
        // Hide station card when clicking outside on the root layout
        findViewById(R.id.main).setOnClickListener(v -> {
            if (stationCard.getVisibility() == View.VISIBLE) {
                stationCard.setVisibility(View.GONE);
            }
        });

        // Prevent clicks on the card itself from triggering the root's listener
        stationCard.setOnClickListener(v -> {
            // Do nothing, just consume the click
        });
        findViewById(R.id.trainingCenterBtn).setOnClickListener(v -> {
            showStation("Training Center", com.example.spacecolonizations.model.station.TrainingCenter.class);
        });
        findViewById(R.id.commandBtn).setOnClickListener(v -> {
            showStation("Command Center", com.example.spacecolonizations.model.station.CommandCenter.class);
        });
        findViewById(R.id.barracksBtn).setOnClickListener(v -> {
            showStation("Barracks", com.example.spacecolonizations.model.station.Barracks.class);
        });
        findViewById(R.id.turretBtn).setOnClickListener(v -> {
            showStation("Turret", com.example.spacecolonizations.model.station.Turret.class);
        });
        findViewById(R.id.medBayBtn).setOnClickListener(v -> {
            showStation("Med Bay", com.example.spacecolonizations.model.station.MedBay.class);
        });
    }

    private void showStation(String name, Class<? extends Station> stationClass) {
        stationCard.setVisibility(View.VISIBLE);
        stationNameTxt.setText(name);
        
        Station station = friendlyShip.getStation(stationClass);
        if (station != null) {
            adapter = new StationAdapter(station.getCrewMembers());
            recyclerView.setAdapter(adapter);
        }
    }
}
