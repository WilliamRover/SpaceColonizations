package com.example.spacecolonizations.model.station;

import android.os.Handler;
import android.os.Looper;

import com.example.spacecolonizations.model.crewmate.Crew;



public class TrainingCenter extends Station{
    private int multiplierIncrement;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable trainRunnable = new Runnable() {
        @Override
        public void run() {
            if (crewMembers.isEmpty() || !isUseable) {
                return;
            }

            for (Crew crew: crewMembers) {
                crew.setMultiplier(multiplierIncrement);
            }
        }
    };
    public TrainingCenter(int stationStrength, int energyLevel, int maxCrew) {
        super(stationStrength, energyLevel, maxCrew);
        this.multiplierIncrement = 20;
    }

    @Override
    public void setEfficiency() {
        this.efficiency = 1;
    }

    public void train(){
        handler.removeCallbacks(trainRunnable);
        handler.post(trainRunnable);
    }

    @Override
    public void loseHealth(int damage) {
        this.stationHealth -= damage;
        this.isUseable = false;

        if (this.stationHealth <= 0) {
            this.stationHealth = 0;

            for (Crew crew : this.crewMembers) {
                crew.loseHealth(crew.getMaxHealthPoints());
            }
        }
    }
}
