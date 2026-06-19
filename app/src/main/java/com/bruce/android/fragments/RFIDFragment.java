package com.bruce.android.fragments;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
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

public class RFIDFragment extends Fragment {

    private TextView statusText;
    private Button scanBtn, attackBtn;
    private Spinner attackSpinner;
    private Switch attackSwitch;
    private NfcAdapter nfcAdapter;
    private boolean running = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generic, container, false);

        nfcAdapter = NfcAdapter.getDefaultAdapter(requireContext());

        statusText = view.findViewById(R.id.status_text);
        scanBtn = view.findViewById(R.id.btn_scan);
        attackBtn = view.findViewById(R.id.btn_attack);
        attackSpinner = view.findViewById(R.id.attack_spinner);
        attackSwitch = view.findViewById(R.id.attack_switch);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.rfid_attacks, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        attackSpinner.setAdapter(adapter);

        scanBtn.setText("Read Tag");
        attackBtn.setText("Clone Tag");

        scanBtn.setOnClickListener(v -> {
            if (nfcAdapter == null) {
                updateStatus("NFC not available");
            } else if (!nfcAdapter.isEnabled()) {
                updateStatus("Enable NFC first");
            } else {
                updateStatus("Tap tag to read...");
            }
        });

        attackBtn.setOnClickListener(v -> {
            running = !running;
            if (running) {
                attackBtn.setText("Stop");
                attackSwitch.setChecked(true);
                updateStatus("RFID emulation running");
            } else {
                attackBtn.setText("Clone Tag");
                attackSwitch.setChecked(false);
                updateStatus("RFID stopped");
            }
        });

        updateStatus("RFID Ready" + (nfcAdapter != null ? " (NFC available)" : " (No NFC)"));
        return view;
    }

    private void updateStatus(String s) { statusText.setText("Status: " + s); }
}
</write_to_file>