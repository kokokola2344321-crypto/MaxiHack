package com.bruce.android.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bruce.android.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClockFragment extends Fragment {
    private TextView statusText, clockDisplay;
    private Button ntpSyncBtn, rtcBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generic, container, false);
        statusText = view.findViewById(R.id.status_text);
        clockDisplay = view.findViewById(R.id.status_text);
        view.findViewById(R.id.attack_spinner).setVisibility(View.GONE);
        view.findViewById(R.id.device_list).setVisibility(View.GONE);
        view.findViewById(R.id.attack_switch).setVisibility(View.GONE);

        Button scanBtn = view.findViewById(R.id.btn_scan);
        Button attackBtn = view.findViewById(R.id.btn_attack);

        scanBtn.setText("NTP Sync");
        attackBtn.setText("RTC Info");
        attackBtn.setBackgroundResource(R.drawable.btn_primary);

        scanBtn.setOnClickListener(v -> updateStatus("NTP sync: " + getCurrentTime()));
        attackBtn.setOnClickListener(v -> updateStatus("RTC: " + getCurrentTime()));

        updateStatus("Clock ready");
        return view;
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return sdf.format(new Date());
    }

    private void updateStatus(String s) { statusText.setText("Status: " + s); }
}
</write_to_file>