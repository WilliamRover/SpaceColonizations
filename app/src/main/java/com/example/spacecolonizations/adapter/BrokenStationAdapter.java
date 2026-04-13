package com.example.spacecolonizations.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacecolonizations.R;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.station.Station;

import java.util.List;
import java.util.Locale;

public class BrokenStationAdapter extends RecyclerView.Adapter<BrokenStationAdapter.BrokenStationViewHolder> {

    public interface OnBrokenStationClickListener {
        void onStationClick(Station station);
        void onCrewActionNeeded();
    }

    private List<Station> brokenStations;
    private final OnBrokenStationClickListener listener;
    private int expandedPosition = -1;

    public BrokenStationAdapter(List<Station> brokenStations, OnBrokenStationClickListener listener) {
        this.brokenStations = brokenStations;
        this.listener = listener;
    }

    public void setBrokenStations(List<Station> brokenStations) {
        this.brokenStations = brokenStations;
        // Basic update. For production apps, DiffUtil is preferred for performance.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BrokenStationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.broken_station_card, parent, false);
        return new BrokenStationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrokenStationViewHolder holder, int position) {
        Station station = brokenStations.get(position);
        boolean isExpanded = position == expandedPosition;
        holder.bind(station, isExpanded, (pos) -> {
            int prevExpanded = expandedPosition;
            if (expandedPosition == pos) {
                expandedPosition = -1;
            } else {
                expandedPosition = pos;
            }
            if (prevExpanded != -1) notifyItemChanged(prevExpanded);
            if (expandedPosition != -1) notifyItemChanged(expandedPosition);
            
            if (listener != null) listener.onStationClick(station);
        }, listener);
    }

    @Override
    public int getItemCount() {
        if(brokenStations != null) {
            return brokenStations.size();
        }
        return 0;
    }

    static class BrokenStationViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtName;
        private final TextView txtTimeCollapse;
        private final TextView txtTimeRepair;
        private final TextView txtEfficiency;
        private final RecyclerView recViewRepairCrew;

        public BrokenStationViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtViewBrokenStationName);
            txtTimeCollapse = itemView.findViewById(R.id.txtViewTimeCollapse);
            txtTimeRepair = itemView.findViewById(R.id.txtViewTimeRepair);
            txtEfficiency = itemView.findViewById(R.id.txtViewEfficency);
            recViewRepairCrew = itemView.findViewById(R.id.recViewRepairCrew);
        }

        public void bind(Station station, boolean isExpanded, OnToggleListener toggleListener, OnBrokenStationClickListener externalListener) {
            txtName.setText(getDisplayName(station));
            txtTimeCollapse.setText(String.format(Locale.US, "%d sec", station.getBreakTimeRemaining() / 1000));
            
            if (station.getRepairTimeRemaining() > 0) {
                txtTimeRepair.setText(String.format(Locale.US, "%.0f sec", station.getRepairTimeRemaining() / 1000));
            } else {
                txtTimeRepair.setText("No crew assigned!!!");
            }
            
            txtEfficiency.setText(String.format(Locale.US, "%.0f%%", station.getRepairEfficiency() * 100));

            if (isExpanded) {
                recViewRepairCrew.setVisibility(View.VISIBLE);
            } else {
                recViewRepairCrew.setVisibility(View.GONE);
            }
            
            if (isExpanded) {
                List<Crew> repairCrew = station.getRepairMan();
                StationAdapter adapter = new StationAdapter(repairCrew, getDisplayName(station) + " (Repair)", new StationAdapter.OnActionRequests() {
                    @Override
                    public void onCrewMoved() {
                        if (externalListener != null) externalListener.onCrewActionNeeded();
                    }

                    @Override
                    public void onShowStatisticsRequested() {
                    }

                    @Override
                    public void onAssignToRepairRequested(Crew crew) {
                    }
                });
                recViewRepairCrew.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
                recViewRepairCrew.setAdapter(adapter);
            }

            itemView.setOnClickListener(v -> toggleListener.onToggle(getAdapterPosition()));
        }

        private String getDisplayName(Station station) {
            String simpleName = station.getClass().getSimpleName();
            switch (simpleName) {
                case "CommandCenter": return "Command Center";
                case "TrainingCenter": return "Training Center";
                case "MedBay": return "Med Bay";
                case "Turret": return "Turret";
                default: return simpleName;
            }
        }
    }

    interface OnToggleListener {
        void onToggle(int position);
    }
}
