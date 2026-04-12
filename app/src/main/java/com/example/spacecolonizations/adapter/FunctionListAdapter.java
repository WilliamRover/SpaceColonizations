package com.example.spacecolonizations.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacecolonizations.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FunctionListAdapter extends RecyclerView.Adapter<FunctionListAdapter.FunctionViewHolder> {

    private final List<String> moveStations = Arrays.asList("Move to Turret", "Move to Training Center", "Move to Med Bay", "Move to Barracks", "Move to Command Center");
    private final List<String> availableFunctions;
    private final OnStationClickListener listener;

    public interface OnStationClickListener {
        void onStationClick(String stationName);
    }

    public FunctionListAdapter(String currentStation, OnStationClickListener listener) {
        this.listener = listener;
        this.availableFunctions = new ArrayList<>();
        
        // Add movement options
        for (String station : moveStations) {
            if (!station.equalsIgnoreCase(currentStation) && !station.equalsIgnoreCase("Move to " + currentStation)) {
                availableFunctions.add(station);
            }
        }
        if ("Turret".equalsIgnoreCase(currentStation)) {
            availableFunctions.add("Deal damage to enemy");
        }
        
        if ("Command Center".equalsIgnoreCase(currentStation)) {
            availableFunctions.add("Show statistics");
        }
    }

    @NonNull
    @Override
    public FunctionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crew_function_card, parent, false);
        return new FunctionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FunctionViewHolder holder, int position) {
        String stationName = availableFunctions.get(position);
        holder.functionName.setText(stationName);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onStationClick(stationName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return availableFunctions.size();
    }

    public static class FunctionViewHolder extends RecyclerView.ViewHolder {
        private final TextView functionName;
        public FunctionViewHolder(View view) {
            super(view);
            functionName = view.findViewById(R.id.txtViewCrewFunc);
        }
    }
}
