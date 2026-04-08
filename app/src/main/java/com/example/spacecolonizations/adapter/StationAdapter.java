package com.example.spacecolonizations.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacecolonizations.R;
import com.example.spacecolonizations.model.crewmate.Commander;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Gunner;
import com.example.spacecolonizations.model.crewmate.Medic;
import com.example.spacecolonizations.model.crewmate.Navigator;
import com.example.spacecolonizations.model.crewmate.Technician;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;
import java.util.Locale;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.StationViewHolder> {

    private List<Crew> crewList;

    public StationAdapter(List<Crew> crewList) {
        this.crewList = crewList;
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
        
        // Using direct calculation since getters were rolled back
        int currentExp = 0; // Defaulting to 0 since exp is private
        int requiredExp = (int) (1000 * Math.exp(crew.getLevel()));
        
        holder.xpTxt.setText(String.format(Locale.US, "%d/%d", currentExp, requiredExp));
        holder.levelBar.setMax(requiredExp);
        holder.levelBar.setProgress(currentExp);

        // Set image based on job type
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
    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }

    public static class StationViewHolder extends RecyclerView.ViewHolder {
        TextView nameTxt, hpTxt, jobTxt, levelTxt, xpTxt;
        LinearProgressIndicator hpBar, levelBar;
        ImageView crewImg;

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
        }
    }
}
