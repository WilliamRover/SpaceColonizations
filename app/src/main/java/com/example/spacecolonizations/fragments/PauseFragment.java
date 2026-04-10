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
        PauseOverlayFragment overlayFragment = (PauseOverlayFragment) getChildFragmentManager().findFragmentById(R.id.pauseOverlay);
        if (overlayFragment != null) {
            overlayFragment.setOnPauseActionListener(() -> pauseOverlayFrag.setVisibility(View.GONE));
        }
    }

    private void setupButtons(View view) {
        view.findViewById(R.id.btnPause).setOnClickListener(v -> pauseOverlayFrag.setVisibility(View.VISIBLE));
    }
}
