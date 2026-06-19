package com.bruce.android.fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bruce.android.R;

import java.util.ArrayList;
import java.util.List;

public class BLEFragment extends Fragment {

    private TextView statusText;
    private ListView listView;
    private Button scanBtn, spamBtn;
    private Spinner attackSpinner;
    private Switch spamSwitch;
    private BluetoothAdapter bluetoothAdapter;
    private boolean spamRunning = false;
    private Handler handler = new Handler(Looper.getMainLooper());
    private List<String> devices = new ArrayList<>();
    private ArrayAdapter<String> deviceAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generic, container, false);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        statusText = view.findViewById(R.id.status_text);
        listView = view.findViewById(R.id.device_list);
        scanBtn = view.findViewById(R.id.btn_scan);
        spamBtn = view.findViewById(R.id.btn_attack);
        attackSpinner = view.findViewById(R.id.attack_spinner);
        spamSwitch = view.findViewById(R.id.attack_switch);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.ble_attacks, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        attackSpinner.setAdapter(adapter);
        attackSpinner.setSelection(6); // Spam All

        deviceAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, devices);
        listView.setAdapter(deviceAdapter);

        scanBtn.setText("BLE Scan");
        spamBtn.setText("Spam All");

        scanBtn.setOnClickListener(v -> scanBLE());
        spamBtn.setOnClickListener(v -> toggleSpam());

        updateStatus("Ready");

        return view;
    }

    private void updateStatus(String status) {
        statusText.setText("Status: " + status);
    }

    private void scanBLE() {
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            updateStatus("BLE not enabled");
            return;
        }
        updateStatus("Scanning BLE...");
        devices.clear();
        deviceAdapter.notifyDataSetChanged();

        BluetoothLeScanner scanner = bluetoothAdapter.getBluetoothLeScanner();
        if (scanner != null) {
            scanner.startScan(new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    String name = result.getDevice().getName() != null ? result.getDevice().getName() : "Unknown";
                    String addr = result.getDevice().getAddress();
                    devices.add(name + " [" + addr + "] RSSI:" + result.getRssi());
                    deviceAdapter.notifyDataSetChanged();
                }
            });
            handler.postDelayed(() -> {
                scanner.stopScan(new ScanCallback() {});
                updateStatus("Scan complete: " + devices.size() + " devices");
            }, 5000);
        }
    }

    private void toggleSpam() {
        spamRunning = !spamRunning;
        if (spamRunning) {
            spamBtn.setText("Stop");
            spamSwitch.setChecked(true);
            updateStatus("BLE Spam running");
        } else {
            spamBtn.setText("Spam All");
            spamSwitch.setChecked(false);
            updateStatus("BLE Spam stopped");
        }
    }
}
</write_to_file>