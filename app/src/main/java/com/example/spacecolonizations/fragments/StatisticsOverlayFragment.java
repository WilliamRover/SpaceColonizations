package com.example.spacecolonizations.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.spacecolonizations.R;
import com.example.spacecolonizations.model.Statistics;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Locale;

public class StatisticsOverlayFragment extends Fragment {
    private TextView numAlive;
    private TextView numDead;
    private TextView numTotal;
    private TextView numSuccess;
    private TextView numFail;
    private TextView numShipKills;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.statistic, container, false);
        numAlive = view.findViewById(R.id.numAlive);
        numDead = view.findViewById(R.id.numDead);
        numTotal = view.findViewById(R.id.numTotal);
        numSuccess = view.findViewById(R.id.numSuccess);
        numFail = view.findViewById(R.id.numFail7);
        numShipKills = view.findViewById(R.id.numKills);

        updateText();
        return view;
    }

    private void updateText(){
        Statistics stats= Statistics.getInstance();
        numAlive.setText(String.format(Locale.US, "%d $", stats.getNumLivingCrews()));
        numDead.setText(String.format(Locale.US, "%d $", stats.getNumDeadCrews()));
        numTotal.setText(String.format(Locale.US, "%d $", stats.getNumTotalCrews()));
        numSuccess.setText(String.format(Locale.US, "%d $", stats.getNumSuccessfulMissions()));
        numFail.setText(String.format(Locale.US, "%d $", stats.getNumFailedMissions()));
        numShipKills.setText(String.format(Locale.US, "%d $", stats.getShipKills()));
    }

}
