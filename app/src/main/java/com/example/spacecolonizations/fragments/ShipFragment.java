package com.example.spacecolonizations.fragments;

import android.os.Bundle;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ship, container, false);

        friendlyShip = FriendlyShip.getShip();
        wallet = Wallet.getInstance();
        //addSampleCrews();

        shipHpBar = view.findViewById(R.id.shipHpBar);
        friendlyHpTxt = view.findViewById(R.id.friendlyHpTxt);
        moneyTxt = view.findViewById(R.id.txtViewMoneyNum);
        fragmentStationContainer = view.findViewById(R.id.stationDetailContainer);

        updateUI();
        setupButtons(view);

        return view;
    }

    private void updateUI() {
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

        // Root view listener to hide detail
        view.setOnClickListener(v -> hideStationDetail());
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
        }
    }

    public FriendlyShip getShip() {
        return friendlyShip;
    }

    private void addSampleCrews() {
        // Create sample crew members
        Gunner crew1 = new Gunner("Jew Burner", 100, 100);
        Medic crew2 = new Medic("Black Plague", 80, 100);
        Commander crew3 = new Commander("Captain Failure", 120, 120);
        Technician crew4 = new Technician("I dont have sleep", 100, 100);
        Navigator crew5 = new Navigator("Nigger", 100, 100);
        Navigator crew6 = new Navigator("Best pilot", 10, 222);
        Gunner crew7 = new Gunner("Allahu Akbar", 100, 100);
        Gunner crew8 = new Gunner("Slave", 100, 100);
        Commander crew9 = new Commander("Corrupted", 200, 500);
        Technician crew10 = new Technician("Im alone mf", 20, 200);
        Technician crew11 = new Technician("Engineering Student 1", 20, 200);
        Technician crew12 = new Technician("Engineering Student 2", 20, 200);
        Technician crew13 = new Technician("Engineering Student 3", 20, 200);
        Technician crew14 = new Technician("Engineering Student 4", 20, 200);
        Technician crew15 = new Technician("Engineering Student 5", 20, 200);
        Technician crew16 = new Technician("Engineering Student 6", 20, 200);
        Technician crew17 = new Technician("Engineering Student 7", 20, 200);
        Technician crew18 = new Technician("Engineering Student 8", 20, 200);
        Technician crew19 = new Technician("Engineering Student 9", 20, 200);
        Technician crew20 = new Technician("Engineering Student 10", 20, 200);

        // Recruit them to the ship
        friendlyShip.recruitCrew(crew1);
        friendlyShip.recruitCrew(crew2);
        friendlyShip.recruitCrew(crew3);
        friendlyShip.recruitCrew(crew4);
        friendlyShip.recruitCrew(crew5);
        friendlyShip.recruitCrew(crew6);
        friendlyShip.recruitCrew(crew7);
        friendlyShip.recruitCrew(crew8);
        friendlyShip.recruitCrew(crew9);
        friendlyShip.recruitCrew(crew10);
        friendlyShip.recruitCrew(crew11);
        friendlyShip.recruitCrew(crew12);
        friendlyShip.recruitCrew(crew13);
        friendlyShip.recruitCrew(crew14);
        friendlyShip.recruitCrew(crew15);
        friendlyShip.recruitCrew(crew16);
        friendlyShip.recruitCrew(crew17);
        friendlyShip.recruitCrew(crew18);
        friendlyShip.recruitCrew(crew19);
        friendlyShip.recruitCrew(crew20);

        // Assign to stations
        for (Station station : friendlyShip.getStations()) {
            if (station instanceof Turret) {
                station.assignCrew(crew1);
                station.assignCrew(crew4);
            } else if (station instanceof MedBay) {
                station.assignCrew(crew2);
                station.assignCrew(crew5);
            } else if (station instanceof CommandCenter) {
                station.assignCrew(crew3);
                station.assignCrew(crew6);
            } else if (station instanceof TrainingCenter) {
                station.assignCrew(crew7);
                station.assignCrew(crew8);
                station.assignCrew(crew9);
            } else if (station instanceof Barracks) {
                station.assignCrew(crew10);
                station.assignCrew(crew11);
                station.assignCrew(crew12);
                station.assignCrew(crew13);
                station.assignCrew(crew14);
                station.assignCrew(crew15);
                station.assignCrew(crew16);
                station.assignCrew(crew17);
                station.assignCrew(crew18);
                station.assignCrew(crew19);
                station.assignCrew(crew20);
            }
        }
    }
}
