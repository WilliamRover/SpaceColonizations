package com.example.spacecolonizations.adapter;

import android.graphics.Color;
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
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.lang.Math.round;

public class TotalCrewAdapter extends RecyclerView.Adapter<TotalCrewAdapter.TotalCrewViewHolder> {

    private List<Crew> crewList;
    private List<Crew> selectedCrews = new ArrayList<>();
    private int maxSelection;
    private OnSelectionChangedListener listener;

    public interface OnSelectionChangedListener {
        void onSelectionChanged(List<Crew> selectedCrews);
    }

    public TotalCrewAdapter(List<Crew> crewList, int maxSelection, OnSelectionChangedListener listener) {
        this.crewList = crewList;
        this.maxSelection = maxSelection;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TotalCrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crew_card, parent, false);
        return new TotalCrewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TotalCrewViewHolder holder, int position) {
        Crew crew = crewList.get(position);
        boolean isSelected = selectedCrews.contains(crew);
        holder.bind(crew, isSelected);

        holder.itemView.setOnClickListener(v -> {
            if (selectedCrews.contains(crew)) {
                selectedCrews.remove(crew);
            } else if (selectedCrews.size() < maxSelection) {
                selectedCrews.add(crew);
            }
            notifyItemChanged(position);
            if (listener != null) {
                listener.onSelectionChanged(new ArrayList<>(selectedCrews));
            }
        });
    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }

    public List<Crew> getSelectedCrews() {
        return selectedCrews;
    }

    public static class TotalCrewViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTxt, hpTxt, jobTxt, levelTxt, xpTxt, isPatient, expandTxt;
        private LinearProgressIndicator hpBar, levelBar;
        private ImageView crewImg;
        private RecyclerView recViewStationList;

        public TotalCrewViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.txtViewName);
            hpTxt = itemView.findViewById(R.id.crewHpTxt);
            jobTxt = itemView.findViewById(R.id.txtViewCrewJob);
            isPatient = itemView.findViewById(R.id.txtViewIsPatient);
            levelTxt = itemView.findViewById(R.id.txtViewLevelNum);
            xpTxt = itemView.findViewById(R.id.txtViewXpNum);
            hpBar = itemView.findViewById(R.id.crewHpBar);
            levelBar = itemView.findViewById(R.id.crewLevelBar);
            crewImg = itemView.findViewById(R.id.crewImg);
            recViewStationList = itemView.findViewById(R.id.recViewStationList);
            recViewStationList.setVisibility(View.GONE);
            expandTxt = itemView.findViewById(R.id.txtViewExpand);
            expandTxt.setVisibility(View.GONE);
        }

        public void bind(Crew crew, boolean isSelected) {
            nameTxt.setText(crew.getName());
            jobTxt.setText(crew.getClass().getSimpleName());
            hpTxt.setText(String.format(Locale.US, "%d/%d", crew.getHealthPoints(), crew.getMaxHealthPoints()));
            hpBar.setMax(crew.getMaxHealthPoints());
            hpBar.setProgress(crew.getHealthPoints());
            levelTxt.setText(String.valueOf(crew.getLevel()));

            int currentExp = round(crew.getExp());
            int requiredExp = (int) (1000 * Math.exp(crew.getLevel()));
            xpTxt.setText(String.format(Locale.US, "%d/%d", currentExp, requiredExp));
            levelBar.setMax(requiredExp);
            levelBar.setProgress(currentExp);
            isPatient.setText(crew.isPatient() ? "Yes" : "No");

            if (crew instanceof Medic) crewImg.setImageResource(R.drawable.medic);
            else if (crew instanceof Gunner) crewImg.setImageResource(R.drawable.gunner);
            else if (crew instanceof Commander) crewImg.setImageResource(R.drawable.commander);
            else if (crew instanceof Navigator) crewImg.setImageResource(R.drawable.navigator);
            else if (crew instanceof Technician) crewImg.setImageResource(R.drawable.technician);
            else crewImg.setImageResource(android.R.drawable.sym_def_app_icon);

            MaterialCardView cardView = (MaterialCardView) itemView;
            if (isSelected) {
                cardView.setStrokeWidth(4);
                cardView.setStrokeColor(Color.WHITE);
                cardView.setCardBackgroundColor(Color.parseColor("#5A7BA8"));
            } else {
                cardView.setStrokeWidth(0);
                cardView.setCardBackgroundColor(Color.parseColor("#415C7E"));
            }
        }
    }
}
