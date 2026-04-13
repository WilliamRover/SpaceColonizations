package com.example.spacecolonizations.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacecolonizations.R;
import com.example.spacecolonizations.adapter.FunctionListAdapter;
import com.example.spacecolonizations.adapter.StationAdapter;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.ship.FriendlyShip;
import com.example.spacecolonizations.model.station.Barracks;
import com.example.spacecolonizations.model.station.CommandCenter;
import com.example.spacecolonizations.model.station.MedBay;
import com.example.spacecolonizations.model.station.Station;
import com.example.spacecolonizations.model.station.TrainingCenter;
import com.example.spacecolonizations.model.station.Turret;

public class StationDetailFragment extends Fragment {

    public interface OnStationNavigationListener {
        void onStationSelected(String name, Class<? extends Station> stationClass);
        void onDataChanged();
        void onShowStatistics();
        void onAssignToRepairRequested(Crew crew);
    }

    private static final String ARG_STATION_NAME = "station_name";
    private static final String ARG_STATION_CLASS = "station_class";

    private String stationName;
    private Class<? extends Station> stationClass;
    private OnStationNavigationListener navigationListener;
    private RecyclerView recyclerView;
    private RecyclerView patientRecyclerView;
    private TextView patientHeader;
    private RecyclerView navRecyclerView;

    // Real-time update handler
    private final Handler updateHandler = new Handler(Looper.getMainLooper());
    private final Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            if (recyclerView != null && recyclerView.getAdapter() != null) {
                recyclerView.getAdapter().notifyDataSetChanged();
            }
            if (patientRecyclerView != null && patientRecyclerView.getAdapter() != null) {
                patientRecyclerView.getAdapter().notifyDataSetChanged();
            }
            updateHandler.postDelayed(this, 1000); // Refresh every second
        }
    };

    public static StationDetailFragment newInstance(String stationName, Class<? extends Station> stationClass) {
        StationDetailFragment fragment = new StationDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STATION_NAME, stationName);
        args.putSerializable(ARG_STATION_CLASS, stationClass);
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnStationNavigationListener(OnStationNavigationListener listener) {
        this.navigationListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stationName = getArguments().getString(ARG_STATION_NAME);
            stationClass = (Class<? extends Station>) getArguments().getSerializable(ARG_STATION_CLASS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_station_detail, container, false);

        TextView nameTxt = view.findViewById(R.id.txtViewStationName);
        recyclerView = view.findViewById(R.id.recViewCrew);
        patientRecyclerView = view.findViewById(R.id.recViewPatients);
        patientHeader = view.findViewById(R.id.txtViewPatientHeader);

        nameTxt.setText(stationName);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (patientRecyclerView != null) {
            patientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        
        refreshCrewList();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateHandler.post(updateRunnable); // Start real-time updates
    }

    @Override
    public void onPause() {
        super.onPause();
        updateHandler.removeCallbacks(updateRunnable); // Stop updates to save resources
    }

    private Class<? extends Station> getStationClass(String name) {
        switch (name) {
            case "Turret": return Turret.class;
            case "Training Center": return TrainingCenter.class;
            case "Med Bay": return MedBay.class;
            case "Barracks": return Barracks.class;
            case "Command Center": return CommandCenter.class;
            default: return null;
        }
    }

    public void refreshCrewList() {
        if (stationClass != null && recyclerView != null) {
            Station station = FriendlyShip.getShip().getStation(stationClass);
            if (station != null) {
                recyclerView.setAdapter(new StationAdapter(station.getCrewMembers(), stationName, new StationAdapter.OnActionRequests() {
                    @Override
                    public void onCrewMoved() {
                        if (navigationListener != null) {
                            navigationListener.onDataChanged();
                        }
                        refreshCrewList();
                    }

                    @Override
                    public void onShowStatisticsRequested() {
                        if (navigationListener != null) {
                            navigationListener.onShowStatistics();
                        }
                    }

                    @Override
                    public void onAssignToRepairRequested(Crew crew) {
                        if (navigationListener != null) {
                            navigationListener.onAssignToRepairRequested(crew);
                        }
                    }
                }));

                if (station instanceof MedBay) {
                    MedBay medBay = (MedBay) station;
                    if (patientRecyclerView != null && patientHeader != null) {
                        patientHeader.setVisibility(View.VISIBLE);
                        patientRecyclerView.setVisibility(View.VISIBLE);
                        patientRecyclerView.setAdapter(new StationAdapter(medBay.getPatients(), "Med Bay Patients", new StationAdapter.OnActionRequests() {
                            @Override
                            public void onCrewMoved() {
                                if (navigationListener != null) {
                                    navigationListener.onDataChanged();
                                }
                                refreshCrewList();
                            }

                            @Override
                            public void onShowStatisticsRequested() {
                                if (navigationListener != null) {
                                    navigationListener.onShowStatistics();
                                }
                            }

                            @Override
                            public void onAssignToRepairRequested(Crew crew) {
                                if (navigationListener != null) {
                                    navigationListener.onAssignToRepairRequested(crew);
                                }
                            }
                        }));
                    }
                } else {
                    if (patientHeader != null) patientHeader.setVisibility(View.GONE);
                    if (patientRecyclerView != null) patientRecyclerView.setVisibility(View.GONE);
                }
            }
        }
    }
}
