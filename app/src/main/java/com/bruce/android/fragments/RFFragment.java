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

public class RFFragment extends Fragment {

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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.rf_attacks, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        attackSpinner.setAdapter(adapter);

        scanBtn.setText("RF Scan");
        attackBtn.setText("Start Jammer");

        scanBtn.setOnClickListener(v -> updateStatus("RF Scanning... (433MHz)"));
        attackBtn.setOnClickListener(v -> {
            running = !running;
            if (running) {
                attackBtn.setText("Stop");
                attackSwitch.setChecked(true);
                updateStatus("RF Jammer running");
            } else {
                attackBtn.setText("Start Jammer");
                attackSwitch.setChecked(false);
                updateStatus("RF Jammer stopped");
            }
        });

        updateStatus("RF Ready");
        return view;
    }

    private void updateStatus(String s) { statusText.setText("Status: " + s); }
}
</write_to_file>