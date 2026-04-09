package com.example.spacecolonizations;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CreditActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits);
        setupButton();
    }

    private void setupButton() {
        Button btnContinue = findViewById(R.id.btnBack);
        btnContinue.setOnClickListener(v -> {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        });
    }
}
