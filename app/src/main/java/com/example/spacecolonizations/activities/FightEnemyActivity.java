package com.example.spacecolonizations.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
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
import com.example.spacecolonizations.model.Statistics;
import com.example.spacecolonizations.model.crewmate.CrewManager;
import com.example.spacecolonizations.model.ship.EnemyShip;
import com.example.spacecolonizations.model.ship.FriendlyShip;
import com.example.spacecolonizations.model.station.Barracks;
import com.example.spacecolonizations.model.station.Station;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FightEnemyActivity extends AppCompatActivity {
    // Ship attributes from Fragment
    private FriendlyShip friendlyShip;
    private ShipFragment shipFragment;
    private View attackOverlay;
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
        attackOverlay = findViewById(R.id.attackOverlay);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //new code for damage friendily ship and module
        AtomicInteger enemyShipHealth = new AtomicInteger(enemyShip.getHullStrength());

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleWithFixedDelay(() -> {
            if (enemyShipHealth.get() != enemyShip.getHullStrength()) {
                enemyShipHealth.set(enemyShip.getHullStrength());
                showAttackOverlay();
                int progessiveDamage = (int) (Statistics.getInstance().getShipKills() / 1.1);
                if (progessiveDamage > 20) {
                    progessiveDamage = 20;
                }

                // Damage the singleton instance to ensure consistency
                FriendlyShip.getShip().loseHealth(20 + progessiveDamage);
                Log.e("Enemy Health", String.valueOf(enemyShip.getHullStrength()));


                // UI updates must happen on the Main Thread
                runOnUiThread(() -> {
                    if (shipFragment != null) {
                        shipFragment.updateUI();
                    }
                });

                if (Math.random() < 0.5) {
                    while (true) {

                        List<Station> station = CrewManager.getStations();
                        station.remove(Barracks.getInstance());
                        boolean i = true;
                        for (Station s : station) {
                            if (s.getisUsable()) {
                                i = false;
                            }
                        }
                        if (i) {
                            break;
                        }
                        Station selectedStation = station.get((int) (Math.random() * station.size()));
                        if (selectedStation.getisUsable()) {
                            selectedStation.breakStation();
                            station.add(Barracks.getInstance());
                            break;
                        }
                    }

                }
            }
            if (FriendlyShip.getShip().getHullStrength() <= 0) {
//                FriendlyShip.getShip().explode();
                scheduler.shutdown();
            }
            if (enemyShip.getHullStrength() <= 0) {
                // Statistics.getInstance().setShipKills(Statistics.getInstance().getShipKills() + 1); // Handled in updateEnemyUI
                scheduler.shutdown();
            }
        }, 0, 1, TimeUnit.SECONDS);

    }

    public void enemyAttack() {

    }
    private void innitEnemyView() {
        enemyShip = new EnemyShip((int) (100 + 100 * Math.random()));

        enemyHpBar = findViewById(R.id.enemyShipHp);
        enemyShipImage = findViewById(R.id.enemyShipModel);
        enemyExplode = findViewById(R.id.enemyExplode);
        enemyHpTxt = findViewById(R.id.enemyHpTxt);
        friendlyExplode = findViewById(R.id.friendlyExplode);

        // Set max values
        enemyHpBar.setMax(enemyShip.getInnitHullStrength());
        updateEnemyUI();
    }

    public void updateEnemyUI() {
        if (enemyShip != null) {
            enemyHpBar.setProgress(enemyShip.getHullStrength());
            if (enemyShip.getHullStrength() <= 0) {
                enemyHpTxt.setText(String.format(Locale.US, "%d/%d", 0, enemyShip.getInnitHullStrength()));
                Statistics.getInstance().setShipKills(Statistics.getInstance().getShipKills() + 1);
                
                enemyExplode.setVisibility(View.VISIBLE);
                enemyExplode.setAlpha(1.0f);
                enemyShipImage.setVisibility(View.INVISIBLE);
            } else {
                enemyHpTxt.setText(String.format(Locale.US, "%d/%d", enemyShip.getHullStrength(), enemyShip.getInnitHullStrength()));
            }
        }
    }

    public void showAttackOverlay() {
        if (attackOverlay != null) {
            runOnUiThread(() -> {
                attackOverlay.setVisibility(View.VISIBLE);
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    attackOverlay.setVisibility(View.GONE);
                }, 2000);
            });
        }
    }

    public EnemyShip getEnemyShip() {
        return enemyShip;
    }
}
