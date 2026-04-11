package com.example.spacecolonizations.activities;

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

import com.example.spacecolonizations.R;
import com.example.spacecolonizations.fragments.ShipFragment;
import com.example.spacecolonizations.model.ship.EnemyShip;
import com.example.spacecolonizations.model.ship.FriendlyShip;

import java.util.Locale;

public class FightEnemyActivity extends AppCompatActivity {
    // Ship attributes from Fragment
    private FriendlyShip friendlyShip;
    private ShipFragment shipFragment;
    
    // Enemy attributes
    private EnemyShip enemyShip;
    private ImageView enemyShipImage;
    private ProgressBar enemyHpBar;
    private TextView enemyExplode;
    private TextView enemyHpTxt;
    
    private TextView friendlyExplode;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fight_enemy);

        // Retrieve the ShipFragment
        shipFragment = (ShipFragment) getSupportFragmentManager().findFragmentById(R.id.shipFragment);
        if (shipFragment != null) {
            friendlyShip = shipFragment.getShip();
        }

        innitEnemyView();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void innitEnemyView() {
        enemyShip = new EnemyShip((int) (100 + 100*Math.random()));

        enemyHpBar = findViewById(R.id.enemyShipHp);
        enemyShipImage = findViewById(R.id.enemyShipModel);
        enemyExplode = findViewById(R.id.enemyExplode);
        enemyHpTxt = findViewById(R.id.enemyHpTxt);
        friendlyExplode = findViewById(R.id.friendlyExplode);

        // Set max values
        enemyHpBar.setMax(enemyShip.getInnitHullStrength());
        enemyHpBar.setProgress(enemyShip.getHullStrength());
        
        enemyHpTxt.setText(String.format(Locale.US, "%d/%d", enemyShip.getHullStrength(), enemyShip.getInnitHullStrength()));
    }
}
