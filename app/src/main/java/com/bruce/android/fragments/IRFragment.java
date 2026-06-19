package com.bruce.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bruce.android.R;

public class IRFragment extends Fragment {
    private TextView statusText;
    private Button scanBtn, attackBtn;
    private Spinner attackSpinner;
    private Switch attackSwitch;
    private boolean running = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generic, container, false);
        statusText = view.findViewById(R.id.status_text);
        scanBtn = view.findViewById(R.id.btn_scan);
        attackBtn = view.findViewById(R.id.btn_attack);
        attackSpinner = view.findViewById(R.id.attack_spinner);
        attackSwitch = view.findViewById(R.id.attack_switch);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.ir_attacks, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        attackSpinner.setAdapter(adapter);
        scanBtn.setText("IR Receive");
        attackBtn.setText("TV-B-Gone");

        scanBtn.setOnClickListener(v -> updateStatus("Listening IR signals..."));
        attackBtn.setOnClickListener(v -> {
            running = !running;
            if (running) {
                attackBtn.setText("Stop");
                attackSwitch.setChecked(true);
                updateStatus("TV-B-Gone running");
            } else {
                attackBtn.setText("TV-B-Gone");
                attackSwitch.setChecked(false);
                updateStatus("IR stopped");
            }
        });
        updateStatus("IR Ready");
        return view;
    }
    private void updateStatus(String s) { statusText.setText("Status: " + s); }
}
</write_to_file>