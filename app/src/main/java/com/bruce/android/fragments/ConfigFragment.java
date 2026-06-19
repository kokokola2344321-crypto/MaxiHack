package com.bruce.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bruce.android.R;

public class ConfigFragment extends Fragment {
    private TextView statusText;
    private SeekBar brightnessBar;
    private Switch dimSwitch, bootSoundSwitch, sleepSwitch;
    private Button restartBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config, container, false);
        statusText = view.findViewById(R.id.status_text);
        brightnessBar = view.findViewById(R.id.brightness_bar);
        dimSwitch = view.findViewById(R.id.switch_dim);
        bootSoundSwitch = view.findViewById(R.id.switch_boot_sound);
        sleepSwitch = view.findViewById(R.id.switch_sleep);
        restartBtn = view.findViewById(R.id.btn_restart);

        brightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                statusText.setText("Brightness: " + progress + "%");
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        dimSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                statusText.setText("Dim time: " + (isChecked ? "10s" : "off")));

        bootSoundSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                statusText.setText("Boot sound: " + (isChecked ? "on" : "off")));

        sleepSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                statusText.setText("Sleep: " + (isChecked ? "enabled" : "disabled")));

        restartBtn.setOnClickListener(v -> {
            statusText.setText("Restarting...");
            // In Android we just simulate restart
            statusText.setText("Restart simulated");
        });

        updateStatus("Config ready");
        return view;
    }

    private void updateStatus(String s) { statusText.setText("Status: " + s); }
}
</write_to_file>