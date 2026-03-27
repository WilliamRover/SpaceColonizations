package com.example.spacecolonizations.model.station;

import android.os.Handler;
import android.os.Looper;

import com.example.spacecolonizations.model.crewmate.Crew;



public class TrainingCenter extends Station{
    //TODO remove multiplier stuff and implement xp

    private float expIncrement;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable trainRunnable = new Runnable() {
        @Override
        public void run() {
            if (crewMembers.isEmpty() || !isUsable) {
                return;
            }

            for (Crew crew: crewMembers) {
                crew.receiveExp(expIncrement);
            }
            handler.postDelayed(this, 1000);
        }
    };

    public TrainingCenter() {
        super();
        this.expIncrement = 10;
        this.maxCrew = 5;
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
