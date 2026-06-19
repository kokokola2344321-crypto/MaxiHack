package com.bruce.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bruce.android.R;

public class FMFragment extends Fragment {
    private TextView statusText;
    private Button broadcastBtn, stopBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generic, container, false);
        statusText = view.findViewById(R.id.status_text);
        broadcastBtn = view.findViewById(R.id.btn_scan);
        stopBtn = view.findViewById(R.id.btn_attack);

        view.findViewById(R.id.attack_spinner).setVisibility(View.GONE);
        view.findViewById(R.id.device_list).setVisibility(View.GONE);
        view.findViewById(R.id.attack_switch).setVisibility(View.GONE);

        broadcastBtn.setText("Broadcast FM");

        stopBtn.setText("Stop Broadcast");
        stopBtn.setBackgroundResource(R.drawable.btn_primary);

        broadcastBtn.setOnClickListener(v -> updateStatus("Broadcasting FM... (87.5-108MHz)"));
        stopBtn.setOnClickListener(v -> updateStatus("Broadcast stopped"));

        updateStatus("FM Ready");
        return view;
    }
    private void updateStatus(String s) { statusText.setText("Status: " + s); }
}
</write_to_file>