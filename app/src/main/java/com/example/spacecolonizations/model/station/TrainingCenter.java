package com.example.spacecolonizations.model.station;

import android.os.Handler;
import android.os.Looper;

import com.example.spacecolonizations.model.crewmate.Crew;



public class TrainingCenter extends Station{
    //TODO remove multiplier stuff and implement xp
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
    public TrainingCenter(int stationStrength, int energyLevel, int maxCrew, Barracks barracks) {
        super(maxCrew);
        this.multiplierIncrement = 20;
    }

    @Override
    public void setEfficiency() {
        return;
    }

    public void train(){
        handler.removeCallbacks(trainRunnable);
        handler.post(trainRunnable);
    }


}
