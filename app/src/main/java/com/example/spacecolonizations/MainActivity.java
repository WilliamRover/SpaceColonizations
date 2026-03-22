package com.example.spacecolonizations;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.spacecolonizations.model.ship.EnemyShip;
import com.example.spacecolonizations.model.ship.FriendlyShip;

public class MainActivity extends AppCompatActivity {
    // Ship attributes
    private FriendlyShip friendlyShip;
    private EnemyShip enemyShip;
    private ProgressBar friendlyHpBar;
    private ProgressBar friendlyEnergyBar;
    private ImageView enemyShipImage;
    private ImageView friendlyShipImage;
    private ProgressBar enemyHpBar;
    private TextView friendlyExplode;
    private TextView enemyExplode;


    private Runnable runnable;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ship
        friendlyShip = new FriendlyShip(100, 110);
        enemyShip = new EnemyShip(120);

        friendlyHpBar = findViewById(R.id.friendlyShipHp);
        friendlyEnergyBar = findViewById(R.id.friendlyShipEnergy);
        enemyHpBar = findViewById(R.id.enemyShipHp);
        friendlyShipImage = findViewById(R.id.friendlyShipModel);
        enemyShipImage = findViewById(R.id.enemyShipModel);
        friendlyExplode = findViewById(R.id.friendlyExplode);
        enemyExplode = findViewById(R.id.enemyExplode);

        // Initial set
        friendlyEnergyBar.setProgress(friendlyShip.getEnergy());
        enemyHpBar.setProgress(enemyShip.getHullStrength());

        // Enemy ship attack
        enemyShip.attackShip(friendlyShip, friendlyShipImage, friendlyExplode, friendlyHpBar, 6);
        friendlyShip.attackShip(enemyShip, enemyShipImage, enemyExplode, enemyHpBar, 12);
    }

}