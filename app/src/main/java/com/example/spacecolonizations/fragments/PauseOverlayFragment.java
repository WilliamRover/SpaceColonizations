package com.example.spacecolonizations.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.spacecolonizations.MenuActivity;
import com.example.spacecolonizations.R;

public class PauseOverlayFragment extends Fragment {

    public interface OnPauseActionListener {
        void onResumeRequested();
        default void onSaveRequested() {}
    }

    private OnPauseActionListener listener;

    public void setOnPauseActionListener(OnPauseActionListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pause_overlay, container, false);

        setupButtons(view);

        return view;
    }

    private void setupButtons(View view) {
        view.findViewById(R.id.btnResume).setOnClickListener(v -> {
            if (listener != null) {
                listener.onResumeRequested();
            }
        });

        view.findViewById(R.id.btnSaveGame).setOnClickListener(v -> {
            if (listener != null) {
                listener.onSaveRequested();
            }
        });

        view.findViewById(R.id.btnGoMenu).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}
