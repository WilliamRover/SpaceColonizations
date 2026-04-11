package com.example.spacecolonizations.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.spacecolonizations.R;

public class PauseFragment extends Fragment {
    private View pauseOverlayFrag;
    private PauseOverlayFragment.OnPauseActionListener externalListener;

    public void setPauseOverlayListener(PauseOverlayFragment.OnPauseActionListener listener) {
        this.externalListener = listener;
        updateOverlayListener();
    }

    private void updateOverlayListener() {
        PauseOverlayFragment overlayFragment = (PauseOverlayFragment) getChildFragmentManager().findFragmentById(R.id.pauseOverlay);
        if (overlayFragment != null) {
            overlayFragment.setOnPauseActionListener(new PauseOverlayFragment.OnPauseActionListener() {
                @Override
                public void onResumeRequested() {
                    pauseOverlayFrag.setVisibility(View.GONE);
                    if (externalListener != null) {
                        externalListener.onResumeRequested();
                    }
                }

                @Override
                public void onSaveRequested() {
                    if (externalListener != null) {
                        externalListener.onSaveRequested();
                    }
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pause_btn, container, false);
        pauseOverlayFrag = view.findViewById(R.id.pauseOverlay);
        setupButtons(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateOverlayListener();
    }

    private void setupButtons(View view) {
        View btnPause = view.findViewById(R.id.btnPause);
        if (btnPause != null) {
            btnPause.setOnClickListener(v -> pauseOverlayFrag.setVisibility(View.VISIBLE));
        }
    }
}
