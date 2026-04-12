package com.example.spacecolonizations.model.station;

import android.os.Handler;
import android.os.Looper;

import com.example.spacecolonizations.model.crewmate.Crew;

import java.io.IOException;


public class TrainingCenter extends Station{

    private float expIncrement;

    private transient Handler trainHandler;
    private transient Runnable trainRunnable;

    public TrainingCenter() {
        super();
        this.expIncrement = 10;
        this.maxCrew = 5;
        this.initTrainingHandler();
    }

    @Override
    protected void setEfficiency() {
        return;
    }

    protected void train(){
        trainHandler.removeCallbacks(trainRunnable);
        trainHandler.post(trainRunnable);
    }


    private void initTrainingHandler() {
        trainHandler = new Handler(Looper.getMainLooper());
        trainRunnable = new Runnable() {
            @Override
            public void run() {
                if (crewMembers.isEmpty() || !isUsable) {
                    return;
                }

                for (Crew crew: crewMembers) {
                    crew.receiveExp((float) (expIncrement * (1 + crew.getLevel() * 0.05)));
                }
                trainHandler.postDelayed(this, 1000);
            }
        };


        if (isUsable && !crewMembers.isEmpty()) {
            train();
        }
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.initTrainingHandler();
    }

}
