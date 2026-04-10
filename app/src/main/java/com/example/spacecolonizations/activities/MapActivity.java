package com.example.spacecolonizations.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spacecolonizations.R;
import com.example.spacecolonizations.fragments.PauseFragment;
import com.example.spacecolonizations.fragments.PauseOverlayFragment;
import com.example.spacecolonizations.model.crewmate.CrewManager;

public class MapActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Button btnEnemy1 = findViewById(R.id.btnEnemy1);
        btnEnemy1.setOnClickListener(v -> {
            Intent intent = new Intent(this, FightEnemyActivity.class);
            startActivity(intent);
        });

        Button btnEnemy2 = findViewById(R.id.btnEnemy2);
        btnEnemy2.setOnClickListener(v -> {
            Intent intent = new Intent(this, FightEnemyActivity.class);
            startActivity(intent);
        });
    }
}
