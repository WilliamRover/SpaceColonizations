package com.example.spacecolonizations.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.spacecolonizations.R;
import com.example.spacecolonizations.model.crewmate.Commander;
import com.example.spacecolonizations.model.crewmate.Gunner;
import com.example.spacecolonizations.model.crewmate.Medic;
import com.example.spacecolonizations.model.crewmate.Navigator;
import com.example.spacecolonizations.model.crewmate.Technician;
import com.example.spacecolonizations.model.ship.FriendlyShip;
import com.example.spacecolonizations.model.shop.Wallet;
import com.example.spacecolonizations.model.station.Barracks;
import com.example.spacecolonizations.model.station.CommandCenter;
import com.example.spacecolonizations.model.station.MedBay;
import com.example.spacecolonizations.model.station.Station;
import com.example.spacecolonizations.model.station.TrainingCenter;
import com.example.spacecolonizations.model.station.Turret;

import java.util.Locale;

public class ShipFragment extends Fragment {

    private FriendlyShip friendlyShip;
    private Wallet wallet;
    private ProgressBar shipHpBar;
    private TextView friendlyHpTxt;
    private TextView moneyTxt;
    private View fragmentStationContainer;
    private View statisticsContainer;

    private final Handler refreshHandler = new Handler(Looper.getMainLooper());
    private final Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            updateUI();
            refreshHandler.postDelayed(this, 1000); // Refresh every second
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ship, container, false);

        friendlyShip = FriendlyShip.getShip();
        wallet = Wallet.getInstance();

        shipHpBar = view.findViewById(R.id.shipHpBar);
        friendlyHpTxt = view.findViewById(R.id.friendlyHpTxt);
        moneyTxt = view.findViewById(R.id.txtViewMoneyNum);
        fragmentStationContainer = view.findViewById(R.id.stationDetailContainer);
        statisticsContainer = view.findViewById(R.id.statisticsContainer);

        updateUI();
        setupButtons(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshHandler.post(refreshRunnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        refreshHandler.removeCallbacks(refreshRunnable);
    }

    public void updateUI() {
        if (friendlyShip == null || wallet == null) return;
        
        shipHpBar.setMax(friendlyShip.getInnitHullStrength());
        shipHpBar.setProgress(friendlyShip.getHullStrength());
        friendlyHpTxt.setText(String.format(Locale.US, "%d/%d", friendlyShip.getHullStrength(), friendlyShip.getInnitHullStrength()));
        moneyTxt.setText(String.format(Locale.US, "%d $", wallet.getBalance()));
    }

    private void setupButtons(View view) {
        view.findViewById(R.id.trainingCenterBtn).setOnClickListener(v -> showStationDetail("Training Center", TrainingCenter.class));
        view.findViewById(R.id.commandBtn).setOnClickListener(v -> showStationDetail("Command Center", CommandCenter.class));
        view.findViewById(R.id.barracksBtn).setOnClickListener(v -> showStationDetail("Barracks", Barracks.class));
        view.findViewById(R.id.turretBtn).setOnClickListener(v -> showStationDetail("Turret", Turret.class));
        view.findViewById(R.id.medBayBtn).setOnClickListener(v -> showStationDetail("Med Bay", MedBay.class));

        view.setOnClickListener(v -> {
            hideStationDetail();
            hideStatistics();
        });
    }

    private void showStationDetail(String name, Class<? extends Station> stationClass) {
        fragmentStationContainer.setVisibility(View.VISIBLE);
        StationDetailFragment fragment = StationDetailFragment.newInstance(name, stationClass);
        fragment.setOnStationNavigationListener(new StationDetailFragment.OnStationNavigationListener() {
            @Override
            public void onStationSelected(String name, Class<? extends Station> stationClass) {
                showStationDetail(name, stationClass);
            }

            @Override
            public void onDataChanged() {
                updateUI();
            }

            @Override
            public void onShowStatistics() {
                showStatistics();
            }
        });
        getChildFragmentManager().beginTransaction()
                .replace(R.id.stationDetailContainer, fragment)
                .commit();
    }

    public void hideStationDetail() {
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.stationDetailContainer);
        if (fragment != null) {
            getChildFragmentManager().beginTransaction().remove(fragment).commit();
            fragmentStationContainer.setVisibility(View.GONE);
            updateUI();
        }
    }

    private void showStatistics() {
        statisticsContainer.setVisibility(View.VISIBLE);
        StatisticsOverlayFragment fragment = new StatisticsOverlayFragment();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.statisticsContainer, fragment)
                .commit();
    }

    public void hideStatistics() {
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.statisticsContainer);
        if (fragment != null) {
            getChildFragmentManager().beginTransaction().remove(fragment).commit();
            statisticsContainer.setVisibility(View.GONE);
        }
    }

    public FriendlyShip getShip() {
        return friendlyShip;
    }
}
