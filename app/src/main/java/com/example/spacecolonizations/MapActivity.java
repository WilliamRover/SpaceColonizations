package com.example.spacecolonizations;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        setupButton();
    }

    private void setupButton() {
        Button btnEnemy1 = findViewById(R.id.btnPause);
        btnEnemy1.setOnClickListener(v -> {
        // Do some random shits here
        });

        Button btnEnemy2 = findViewById(R.id.btnEnemy2);
        btnEnemy2.setOnClickListener(v -> {
            // Do some random shits here
        });


    }
}
