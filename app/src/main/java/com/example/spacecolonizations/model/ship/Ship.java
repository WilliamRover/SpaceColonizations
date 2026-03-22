package com.example.spacecolonizations.model.ship;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.spacecolonizations.reuse.ChangeHealth;

public abstract class Ship implements ChangeHealth {
    private int hullStrength;
    private int innitHullStrength;

    public Ship(int innitHullStrength) {
        this.innitHullStrength = innitHullStrength;
        this.hullStrength = innitHullStrength;
    }
    public int getHullStrength() {
        return hullStrength;
    }
    public int getInnitHullStrength() {
        return innitHullStrength;
    }
    public void setHullStrength(int hullStrength) {
        this.hullStrength = hullStrength;
        if (this.hullStrength <= 0) {
            this.hullStrength = 0;
        }
    }
    public void attackShip(Ship ship, View shipModel, View kaboom, ProgressBar hpProgress, TextView hpTxt, int shipDps) {
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (Ship.this.getHullStrength() <= 0) {
                    handler.removeCallbacks(this);
                    return;
                }

                // Decrease target's hull strength
                int curHull = ship.getHullStrength();

                if (curHull <= 0) {
                    explode(shipModel, kaboom);
                    // Stop the loop
                    handler.removeCallbacks(this);
                    return;
                }

                if (curHull > 0) {
                    ship.loseHealth(shipDps);
                    hpTxt.setText(ship.getHullStrength() + "/" + ship.getInnitHullStrength());
                    hpProgress.setProgress(ship.getHullStrength());
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }
    private void explode(View shipModel, View kaboom) {
        kaboom.setVisibility(View.VISIBLE);
        kaboom.setAlpha(1.0f);
        shipModel.setVisibility(View.INVISIBLE);
    }

    public void loseHealth(int damage) {
        this.hullStrength -= damage;
    }
    public void increaseHealth(int health) {
        this.hullStrength += health;
    }
}
