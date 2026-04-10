package com.example.spacecolonizations.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacecolonizations.R;
import com.example.spacecolonizations.model.crewmate.Commander;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Gunner;
import com.example.spacecolonizations.model.crewmate.Medic;
import com.example.spacecolonizations.model.crewmate.Navigator;
import com.example.spacecolonizations.model.crewmate.Technician;
import com.example.spacecolonizations.model.ship.FriendlyShip;
import com.example.spacecolonizations.model.station.Barracks;
import com.example.spacecolonizations.model.station.CommandCenter;
import com.example.spacecolonizations.model.station.MedBay;
import com.example.spacecolonizations.model.station.Station;
import com.example.spacecolonizations.model.station.TrainingCenter;
import com.example.spacecolonizations.model.station.Turret;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;
import java.util.Locale;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.StationViewHolder> {

    private List<Crew> crewList;
    private int expandedPosition = -1;
    private final OnCrewMovedListener movedListener;
    private final String currentStationName;

    public interface OnCrewMovedListener {
        void onCrewMoved();
    }

    public StationAdapter(List<Crew> crewList, String currentStationName, OnCrewMovedListener movedListener) {
        this.crewList = crewList;
        this.currentStationName = currentStationName;
        this.movedListener = movedListener;
    }

    @NonNull
    @Override
    public StationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crew_card, parent, false);
        return new StationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StationViewHolder holder, int position) {
        Crew crew = crewList.get(position);
        holder.nameTxt.setText(crew.getName());
        holder.jobTxt.setText(crew.getClass().getSimpleName());
        
        holder.hpTxt.setText(String.format(Locale.US, "%d/%d", crew.getHealthPoints(), crew.getMaxHealthPoints()));
        holder.hpBar.setMax(crew.getMaxHealthPoints());
        holder.hpBar.setProgress(crew.getHealthPoints());
        
        holder.levelTxt.setText(String.valueOf(crew.getLevel()));
        
        int currentExp = Math.round(crew.getExp()); 
        int requiredExp = (int) (1000 * Math.exp(crew.getLevel()));
        
        holder.xpTxt.setText(String.format(Locale.US, "%d/%d", currentExp, requiredExp));
        holder.levelBar.setMax(requiredExp);
        holder.levelBar.setProgress(currentExp);

        if (crew instanceof Medic) {
            holder.crewImg.setImageResource(R.drawable.medic);
        } else if (crew instanceof Gunner) {
            holder.crewImg.setImageResource(R.drawable.gunner);
        } else if (crew instanceof Commander) {
            holder.crewImg.setImageResource(R.drawable.commander);
        } else if (crew instanceof Navigator) {
            holder.crewImg.setImageResource(R.drawable.navigator);
        } else if (crew instanceof Technician) {
            holder.crewImg.setImageResource(R.drawable.technician);
        } else {
            holder.crewImg.setImageResource(android.R.drawable.sym_def_app_icon);
        }

        final boolean isExpanded = position == expandedPosition;
        holder.recViewStationList.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);

        // Expand the function list
        if (isExpanded) {
            FunctionListAdapter adapter = new FunctionListAdapter(currentStationName, stationName -> {
                int currentPos = holder.getAdapterPosition();
                if (currentPos == RecyclerView.NO_POSITION) return;

                Station oldStation = crew.getCurrentStation();
                moveCrewToStation(crew, stationName);

                if (crew.getCurrentStation() != oldStation) {
                    expandedPosition = -1;
                    notifyItemRemoved(currentPos);
                    if (movedListener != null) {
                        movedListener.onCrewMoved();
                    }
                } else {
                    expandedPosition = -1;
                    notifyItemChanged(currentPos);
                    Toast.makeText(holder.itemView.getContext(), "Move failed", Toast.LENGTH_SHORT).show();
                }
            });
            holder.recViewStationList.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.VERTICAL, false));
            holder.recViewStationList.setAdapter(adapter);
        }

        holder.itemView.setOnClickListener(v -> {
            int currentPos = holder.getAdapterPosition();
            if (currentPos == RecyclerView.NO_POSITION) return;

            int prevExpanded = expandedPosition;
            if (currentPos == expandedPosition) {
                expandedPosition = -1;
            } else {
                expandedPosition = currentPos;
            }

            if (prevExpanded != -1) {
                notifyItemChanged(prevExpanded);
            }
            if (expandedPosition != -1 && expandedPosition != prevExpanded) {
                notifyItemChanged(expandedPosition);
            }
        });
    }

    private void moveCrewToStation(Crew crew, String stationName) {
        FriendlyShip ship = FriendlyShip.getShip();
        Station targetStation = null;

        switch (stationName) {
            case "Move to Turret":
                targetStation = ship.getStation(Turret.class);
                break;
            case "Move to Training Center":
                targetStation = ship.getStation(TrainingCenter.class);
                break;
            case "Move to Med Bay":
                targetStation = ship.getStation(MedBay.class);
                break;
            case "Move to Barracks":
                targetStation = ship.getStation(Barracks.class);
                break;
            case "Move to Command Center":
                targetStation = ship.getStation(CommandCenter.class);
                break;
        }

        if (targetStation != null) {
            targetStation.assignCrew(crew);
        }
    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }

    public static class StationViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTxt, hpTxt, jobTxt, levelTxt, xpTxt;
        private LinearProgressIndicator hpBar, levelBar;
        private ImageView crewImg;
        private RecyclerView recViewStationList;

        public StationViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.txtViewName);
            hpTxt = itemView.findViewById(R.id.crewHpTxt);
            jobTxt = itemView.findViewById(R.id.txtViewCrewJob);
            levelTxt = itemView.findViewById(R.id.txtViewLevelNum);
            xpTxt = itemView.findViewById(R.id.txtViewXpNum);
            hpBar = itemView.findViewById(R.id.crewHpBar);
            levelBar = itemView.findViewById(R.id.crewLevelBar);
            crewImg = itemView.findViewById(R.id.crewImg);
            recViewStationList = itemView.findViewById(R.id.recViewStationList);
        }
    }
}
