package com.example.spacecolonizations.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacecolonizations.R;
import com.example.spacecolonizations.adapter.BrokenStationAdapter;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.CrewManager;
import com.example.spacecolonizations.model.station.Station;

import java.util.ArrayList;
import java.util.List;

public class BrokenStationListFragment extends Fragment {

    private static final String ARG_CREW = "repair_crew";
    
    private Crew selectedCrew;
    private RecyclerView recyclerView;
    private BrokenStationAdapter adapter;
    private final Handler handler = new Handler(Looper.getMainLooper());
    
    private final Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            refreshList();
            handler.postDelayed(this, 1000);
        }
    };

    public static BrokenStationListFragment newInstance(Crew crew) {
        BrokenStationListFragment fragment = new BrokenStationListFragment();
        Bundle args = new Bundle();
        if (crew != null) {
            args.putSerializable(ARG_CREW, crew);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedCrew = (Crew) getArguments().getSerializable(ARG_CREW);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_broken_station_list, container, false);

        recyclerView = view.findViewById(R.id.recViewBrokenStation);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        
        setupAdapter();

        return view;
    }

    private void setupAdapter() {
        adapter = new BrokenStationAdapter(getBrokenStations(), new BrokenStationAdapter.OnBrokenStationClickListener() {
            @Override
            public void onStationClick(Station station) {
                if (getParentFragment() instanceof ShipFragment) {
                    ShipFragment shipFragment = (ShipFragment) getParentFragment();
                    
                    if (selectedCrew != null) {
                        station.addRepairMan(selectedCrew);
                        Toast.makeText(getContext(), selectedCrew.getName() + " is now repairing " + getDisplayName(station), Toast.LENGTH_SHORT).show();
                        
                        shipFragment.hideBrokenStationList();
                        shipFragment.hideStationDetail();
                        shipFragment.updateUI();
                    } else {
                        String name = getDisplayName(station);
                        shipFragment.showStationDetail(name, station.getClass());
                    }
                }
            }

            @Override
            public void onCrewActionNeeded() {
                if (getParentFragment() instanceof ShipFragment) {
                    ((ShipFragment) getParentFragment()).updateUI();
                }
                refreshList();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void refreshList() {
        if (adapter != null) {
            List<Station> broken = getBrokenStations();
            adapter.setBrokenStations(broken);
        }
    }

    private List<Station> getBrokenStations() {
        List<Station> allStations = CrewManager.getStations();
        List<Station> brokenStations = new ArrayList<>();
        for (Station s : allStations) {
            if (!s.getisUsable()) {
                brokenStations.add(s);
            }
        }
        return brokenStations;
    }

    private String getDisplayName(Station station) {
        String simpleName = station.getClass().getSimpleName();
        switch (simpleName) {
            case "CommandCenter": return "Command Center";
            case "TrainingCenter": return "Training Center";
            case "MedBay": return "Med Bay";
            case "Turret": return "Turret";
            case "Barracks": return "Barracks";
            default: return simpleName;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.post(updateRunnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(updateRunnable);
    }
}
