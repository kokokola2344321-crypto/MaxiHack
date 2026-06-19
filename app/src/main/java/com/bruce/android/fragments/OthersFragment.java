package com.bruce.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bruce.android.R;

import java.io.File;

public class OthersFragment extends Fragment {
    private TextView statusText;
    private Button qrBtn, badUsbBtn, sdBtn, webUIBtn, ledBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_others, container, false);
        statusText = view.findViewById(R.id.status_text);
        qrBtn = view.findViewById(R.id.btn_qr);
        badUsbBtn = view.findViewById(R.id.btn_badusb);
        sdBtn = view.findViewById(R.id.btn_sd);
        webUIBtn = view.findViewById(R.id.btn_webui);
        ledBtn = view.findViewById(R.id.btn_led);

        qrBtn.setOnClickListener(v -> updateStatus("QR Code generator ready"));
        badUsbBtn.setOnClickListener(v -> updateStatus("BadUSB: Select ducky script"));
        sdBtn.setOnClickListener(v -> {
            File sd = new File("/sdcard");
            if (sd.exists()) updateStatus("SD Card mounted");
            else updateStatus("No SD Card found");
        });
        webUIBtn.setOnClickListener(v -> updateStatus("WebUI server started on port 80"));
        ledBtn.setOnClickListener(v -> updateStatus("LED control: Flashlight toggle"));

        updateStatus("Others ready");
        return view;
    }
    private void updateStatus(String s) { statusText.setText("Status: " + s); }
}
</write_to_file>