package com.bruce.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bruce.android.R;

public class ConnectFragment extends Fragment {
    private TextView statusText;
    private Button sendBtn, receiveBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connect, container, false);
        statusText = view.findViewById(R.id.status_text);
        sendBtn = view.findViewById(R.id.btn_send);
        receiveBtn = view.findViewById(R.id.btn_receive);

        sendBtn.setOnClickListener(v -> {
            statusText.setText("Status: Broadcast sent via ESPNOW");
            Toast.makeText(requireContext(), "Data broadcasted", Toast.LENGTH_SHORT).show();
        });
        receiveBtn.setOnClickListener(v -> {
            statusText.setText("Status: Listening for data...");
            Toast.makeText(requireContext(), "Listening...", Toast.LENGTH_SHORT).show();
        });

        updateStatus("Connect (ESPNOW) ready");
        return view;
    }

    private void updateStatus(String s) { statusText.setText("Status: " + s); }
}
</write_to_file>