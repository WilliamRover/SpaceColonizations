package com.example.spacecolonizations.model.ship;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EnemyShip extends Ship{
    private int hullStrength;

    public EnemyShip(int hullStrength) {
        super(hullStrength);
    }
}
