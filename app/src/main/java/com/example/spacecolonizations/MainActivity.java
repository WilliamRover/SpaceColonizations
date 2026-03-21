package com.example.spacecolonizations;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import model.EnemyShip;
import model.FriendlyShip;

public class MainActivity extends AppCompatActivity {

    private FriendlyShip friendlyShip;
    private EnemyShip enemyShip;
    private TextView friendlyHpTextView;
    private ImageView friendlyShipImage;
    private ImageView enemyShipImage;
    private TextView friendlyExplode;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;

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

        friendlyHpTextView = findViewById(R.id.friendlyShipHP);
        TextView friendlyEnergyTextView = findViewById(R.id.friendlyShipEnergy);
        TextView enemyHpTextView = findViewById(R.id.enemyShipHP);
        friendlyShipImage = findViewById(R.id.friendlyShipModel);
        enemyShipImage = findViewById(R.id.enemyShipModel);
        friendlyExplode = findViewById(R.id.friendlyExplode);

        // Initial set
        updateUI();
        friendlyEnergyTextView.setText(String.valueOf(friendlyShip.getEnergy()));
        enemyHpTextView.setText(String.valueOf(enemyShip.getHullStrength()));

        // Enemy ship attack
        enemyAttack();
    }

    private void enemyAttack() {
        runnable = new Runnable() {
            @Override
            public void run() {
                // Decrease hull strength
                int curHull = friendlyShip.getHullStrength();
                
                if (curHull <= 0) {
                    friendlyExplode.setVisibility(View.VISIBLE);
                    friendlyExplode.setAlpha(1.0f);
                    friendlyShipImage.setVisibility(View.INVISIBLE);
                    // Stop the loop
                    handler.removeCallbacks(this);
                    return;
                }

                if (curHull > 0) {
                    friendlyShip.setHullStrength(Math.max(0, curHull - 6));
                    updateUI();
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    private void updateUI() {
        friendlyHpTextView.setText(String.valueOf(friendlyShip.getHullStrength()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}