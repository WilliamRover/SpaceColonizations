package com.example.spacecolonizations.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.spacecolonizations.R;
import com.example.spacecolonizations.adapter.StationAdapter;
import com.example.spacecolonizations.fragments.PauseFragment;
import com.example.spacecolonizations.fragments.PauseOverlayFragment;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.CrewManager;
import com.example.spacecolonizations.model.crewmate.Navigator;
import com.example.spacecolonizations.model.mission.FightEnemy;
import com.example.spacecolonizations.model.mission.Mission;
import com.example.spacecolonizations.model.mission.PassObstacle;
import com.example.spacecolonizations.model.mission.Rescue;
import com.example.spacecolonizations.model.mission.obstacle.Asteroid;
import com.example.spacecolonizations.model.station.CommandCenter;
import com.example.spacecolonizations.model.station.Station;
import com.example.spacecolonizations.model.mission.PathGeneration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapActivity extends AppCompatActivity {
    private PathGeneration path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.path = new PathGeneration();
        setContentView(R.layout.map);
        setupButton();

    }

    @Override
    public void onAttachFragment(@NonNull androidx.fragment.app.Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof PauseFragment) {
            ((PauseFragment) fragment).setPauseOverlayListener(new PauseOverlayFragment.OnPauseActionListener() {
                @Override
                public void onResumeRequested() {
                    // Literally does nothing
                }

                @Override
                public void onSaveRequested() {
                    Toast.makeText(MapActivity.this, "Game Saved!", Toast.LENGTH_SHORT).show();
                    CrewManager.saveTOFile(MapActivity.this);
                }
            });
        }
    }

    private void setupButton() {
        boolean navigatoronCommand = false;

        Mission m1 = path.properRandomGeneration();
        HashMap<String,Integer> mp1 = path.getMissionPercent();
        Mission m2 = path.properRandomGeneration();
        HashMap<String,Integer> mp2 = path.getMissionPercent();

        ArrayList<Mission> m = new ArrayList<>();
        m.add(m1);
        m.add(m2);

        String chancefight1 = String.valueOf(mp1.get("FightEnemy"));
        String chanceobstacle1 = String.valueOf(mp1.get("PassObstacle"));
        String chanceRescue1 = String.valueOf(mp1.get("Rescue"));

        String chancefight2 = String.valueOf(mp2.get("FightEnemy"));
        String chanceobstacle2 = String.valueOf(mp2.get("PassObstacle") );
        String chanceRescue2 = String.valueOf(mp2.get("Rescue"));

        String txtfight1 = chancefight1 + " % Fight";
        String txtobstacle1 = chanceobstacle1 + " % obstacle";
        String txtrescue1 = chanceRescue1 + " % rescue";

        String txtfight2 = chancefight2 + " % Fight";
        String txtobstacle2 = chanceobstacle2 + " % obstacle";
        String txtrescue2 = chanceRescue2 + " % rescue";

        for (Crew c : CrewManager.getCrew()){
            if (c.getCurrentStation() instanceof CommandCenter && c instanceof Navigator ){
                navigatoronCommand = true;
                break;
            }
        }


        Button btnShop = findViewById(R.id.btnShop);
        btnShop.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShopActivity.class);
            startActivity(intent);

        });

        Button btnEnemy1 = findViewById(R.id.btnEnemy1);
        TextView txtEnemyLabel1 = findViewById(R.id.txtEnemyLabel1);
        TextView txtViewFightProb1 = findViewById(R.id.txtViewFightProb1);
        TextView txtViewObstacleProb1 = findViewById(R.id.txtViewObstacleProb1);
        TextView txtVIewRescueProb1 = findViewById(R.id.txtViewRescueProb1);

        txtViewFightProb1.setText(txtfight1);
        txtViewObstacleProb1.setText(txtobstacle1);
        txtVIewRescueProb1.setText(txtrescue1);

        if (navigatoronCommand){
            txtViewFightProb1.setText("");
            txtViewObstacleProb1.setText("");
            txtVIewRescueProb1.setText("");
            if (m.get(0) instanceof FightEnemy){
                txtViewFightProb1.setText("Fight Enemy");
                btnEnemy1.setBackgroundColor(
                        ContextCompat.getColor(this,R.color.red)
                );
                txtEnemyLabel1.setTextColor(
                        ContextCompat.getColor(this,R.color.red)
                );
            } else if (m.get(0) instanceof PassObstacle) {
                txtViewFightProb1.setText(m.get(0).getMissionType());
                btnEnemy1.setBackgroundColor(
                        ContextCompat.getColor(this,R.color.yellow)
                );
                txtEnemyLabel1.setTextColor(
                        ContextCompat.getColor(this,R.color.yellow)
                );
            } else if (m.get(0) instanceof Rescue) {
                txtViewFightProb1.setText("Rescue");
                btnEnemy1.setBackgroundColor(
                        ContextCompat.getColor(this,R.color.blue)
                );
                txtEnemyLabel1.setTextColor(
                        ContextCompat.getColor(this,R.color.blue)
                );

            }
        }
        btnEnemy1.setOnClickListener(v -> {
            Intent intent = null;
            if (m.get(0) instanceof FightEnemy){
                intent = new Intent(this, FightEnemyActivity.class);
            } else if (m.get(0) instanceof PassObstacle) {
                if (m.get(0).getMissionType().equals("Asteroid")){
                    intent = new Intent(this, AsteroidActivity.class);
                } else if (m.get(0).getMissionType().equals("EngineFailure")) {
                    intent = new Intent(this, EngineFailureActivity.class);
                }
            } else if (m.get(0) instanceof Rescue) {
                intent = new Intent(this, RescueActivity.class);
            }

            if (intent != null) {
                startActivity(intent);
            }

        });

        Button btnEnemy2 = findViewById(R.id.btnEnemy2);
        TextView txtEnemyLabel2 = findViewById(R.id.txtEnemyLabel2);
        TextView txtViewFightProb2 = findViewById(R.id.txtViewFightProb2);
        TextView txtViewObstacleProb2 = findViewById(R.id.txtViewObstacleProb2);
        TextView txtViewRescueProb2 = findViewById(R.id.txtViewRescueProb2);

        txtViewFightProb2.setText(txtfight2);
        txtViewObstacleProb2.setText(txtobstacle2);
        txtViewRescueProb2.setText(txtrescue2);

        if (navigatoronCommand){
            txtViewFightProb2.setText("");
            txtViewObstacleProb2.setText("");
            txtViewRescueProb2.setText("");
            if (m.get(1) instanceof FightEnemy){
                txtViewFightProb2.setText("Fight Enemy");
                btnEnemy2.setBackgroundColor(
                        ContextCompat.getColor(this,R.color.red)
                );
                txtEnemyLabel2.setTextColor(
                        ContextCompat.getColor(this,R.color.red)
                );
            } else if (m.get(1) instanceof PassObstacle) {
                txtViewFightProb2.setText(m.get(1).getMissionType());
                btnEnemy2.setBackgroundColor(
                        ContextCompat.getColor(this,R.color.yellow)
                );
                txtEnemyLabel2.setTextColor(
                        ContextCompat.getColor(this,R.color.yellow)
                );
            } else if (m.get(1) instanceof Rescue) {
                txtViewFightProb2.setText("Rescue");
                btnEnemy2.setBackgroundColor(
                        ContextCompat.getColor(this,R.color.blue)
                );
                txtEnemyLabel2.setTextColor(
                        ContextCompat.getColor(this,R.color.blue)
                );
            }
        }
        btnEnemy2.setOnClickListener(v -> {
            Intent intent = null;
            if (m.get(1) instanceof FightEnemy){
                intent = new Intent(this, FightEnemyActivity.class);
            } else if (m.get(1) instanceof PassObstacle) {
                if (m.get(1).getMissionType().equals("Asteroid")){
                    intent = new Intent(this, AsteroidActivity.class);
                } else if (m.get(1).getMissionType().equals("EngineFailure")) {
                    intent = new Intent(this, EngineFailureActivity.class);
                }
            } else if (m.get(1) instanceof Rescue) {
                intent = new Intent(this, RescueActivity.class);
            }

            if (intent != null) {
                startActivity(intent);
            }
        });
    }
}
