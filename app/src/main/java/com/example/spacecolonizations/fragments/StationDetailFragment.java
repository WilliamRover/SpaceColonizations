package com.example.spacecolonizations.fragments;

import android.os.Bundle;
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
import com.example.spacecolonizations.adapter.StationAdapter;
import com.example.spacecolonizations.model.crewmate.Crew;

import java.io.Serializable;
import java.util.List;

public class StationDetailFragment extends Fragment {

    private static final String ARG_STATION_NAME = "station_name";
    private static final String ARG_CREW_LIST = "crew_list";

    private String stationName;
    private List<Crew> crewList;

    public static StationDetailFragment newInstance(String stationName, List<Crew> crewList) {
        StationDetailFragment fragment = new StationDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STATION_NAME, stationName);
        args.putSerializable(ARG_CREW_LIST, (Serializable) crewList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stationName = getArguments().getString(ARG_STATION_NAME);
            crewList = (List<Crew>) getArguments().getSerializable(ARG_CREW_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_station_detail, container, false);

        TextView nameTxt = view.findViewById(R.id.txtViewStationName);
        RecyclerView recyclerView = view.findViewById(R.id.recViewCrew);

        nameTxt.setText(stationName);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (crewList != null) {
            recyclerView.setAdapter(new StationAdapter(crewList));
        }

        return view;
    }
}
