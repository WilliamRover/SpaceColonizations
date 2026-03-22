package com.example.spacecolonizations.model.ship;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

abstract class Ship {
    private int hullStrength;
    public Ship(int hullStrength) {
        this.hullStrength = hullStrength;
    }
    public int getHullStrength() {
        return hullStrength;
    }
    public void setHullStrength(int hullStrength) {
        this.hullStrength = hullStrength;
        if (hullStrength <= 0) {
            this.hullStrength = 0;
        }
    }
    public void attackShip(Ship ship, View shipModel, View kaboom, ProgressBar hp, int shipDps) {
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
                    ship.setHullStrength(curHull - shipDps);
                    hp.setProgress(ship.getHullStrength());
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
}
