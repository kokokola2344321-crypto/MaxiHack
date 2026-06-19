package com.bruce.android.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bruce.android.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class WiFiFragment extends Fragment {

    private WifiManager wifiManager;
    private ListView listView;
    private TextView statusText;
    private EditText ssidInput, passwordInput, targetInput, portInput, evilPortalInput;
    private Button scanBtn, beaconBtn, deauthBtn, connectBtn, createAPBtn, scanHostsBtn, startEvilPortalBtn, startTCPServerBtn, startTCPClientBtn;
    private Switch beaconSwitch, deauthSwitch, evilPortalSwitch;
    private Spinner attackSpinner;
    private boolean beaconRunning = false, deauthRunning = false, evilPortalRunning = false;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wifi, container, false);

        wifiManager = (WifiManager) requireContext().getApplicationContext().getSystemService(requireContext().WIFI_SERVICE);

        listView = view.findViewById(R.id.wifi_list);
        statusText = view.findViewById(R.id.wifi_status);
        ssidInput = view.findViewById(R.id.ssid_input);
        passwordInput = view.findViewById(R.id.password_input);
        targetInput = view.findViewById(R.id.target_input);
        portInput = view.findViewById(R.id.port_input);
        evilPortalInput = view.findViewById(R.id.evil_portal_html);

        attackSpinner = view.findViewById(R.id.attack_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.wifi_attacks, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        attackSpinner.setAdapter(adapter);

        scanBtn = view.findViewById(R.id.btn_scan);
        beaconBtn = view.findViewById(R.id.btn_beacon);
        deauthBtn = view.findViewById(R.id.btn_deauth);
        connectBtn = view.findViewById(R.id.btn_connect);
        createAPBtn = view.findViewById(R.id.btn_create_ap);
        scanHostsBtn = view.findViewById(R.id.btn_scan_hosts);
        startEvilPortalBtn = view.findViewById(R.id.btn_evil_portal);
        startTCPServerBtn = view.findViewById(R.id.btn_tcp_server);
        startTCPClientBtn = view.findViewById(R.id.btn_tcp_client);

        beaconSwitch = view.findViewById(R.id.switch_beacon);
        deauthSwitch = view.findViewById(R.id.switch_deauth);
        evilPortalSwitch = view.findViewById(R.id.switch_evil_portal);

        scanBtn.setOnClickListener(v -> scanWiFi());
        beaconBtn.setOnClickListener(v -> toggleBeacon());
        deauthBtn.setOnClickListener(v -> toggleDeauth());
        connectBtn.setOnClickListener(v -> connectToWiFi());
        createAPBtn.setOnClickListener(v -> createAccessPoint());
        scanHostsBtn.setOnClickListener(v -> scanHosts());
        startEvilPortalBtn.setOnClickListener(v -> toggleEvilPortal());
        startTCPServerBtn.setOnClickListener(v -> startTCPServer());
        startTCPClientBtn.setOnClickListener(v -> startTCPClient());

        updateStatus("Ready");

        return view;
    }

    private void updateStatus(String status) {
        statusText.setText("Status: " + status);
    }

    private void scanWiFi() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }
        updateStatus("Scanning...");
        wifiManager.startScan();
        List<ScanResult> results = wifiManager.getScanResults();
        List<String> wifiNames = new ArrayList<>();
        for (ScanResult result : results) {
            wifiNames.add(result.SSID + " (" + result.BSSID + ") " + result.level + "dBm");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, wifiNames);
        listView.setAdapter(arrayAdapter);
        updateStatus("Found " + results.size() + " networks");
    }

    private void toggleBeacon() {
        beaconRunning = !beaconRunning;
        if (beaconRunning) {
            beaconBtn.setText("Stop Beacon");
            beaconSwitch.setChecked(true);
            updateStatus("Beacon Spam running");
            startBeaconSpam();
        } else {
            beaconBtn.setText("Beacon Spam");
            beaconSwitch.setChecked(false);
            updateStatus("Beacon stopped");
        }
    }

    private void startBeaconSpam() {
        new Thread(() -> {
            while (beaconRunning) {
                try {
                    Process process = Runtime.getRuntime().exec("su");
                    // This would require root - for non-root we simulate
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void toggleDeauth() {
        deauthRunning = !deauthRunning;
        if (deauthRunning) {
            deauthBtn.setText("Stop Deauth");
            deauthSwitch.setChecked(true);
            updateStatus("Deauth Flood running");
        } else {
            deauthBtn.setText("Deauth Flood");
            deauthSwitch.setChecked(false);
            updateStatus("Deauth stopped");
        }
    }

    private void connectToWiFi() {
        String ssid = ssidInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        if (ssid.isEmpty()) {
            Toast.makeText(requireContext(), "Enter SSID", Toast.LENGTH_SHORT).show();
            return;
        }
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = "\"" + ssid + "\"";
        if (password.isEmpty()) {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        } else {
            config.preSharedKey = "\"" + password + "\"";
        }
        int netId = wifiManager.addNetwork(config);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();
        updateStatus("Connecting to " + ssid);
    }

    private void createAccessPoint() {
        String ssid = ssidInput.getText().toString().trim();
        if (ssid.isEmpty()) {
            Toast.makeText(requireContext(), "Enter SSID", Toast.LENGTH_SHORT).show();
            return;
        }
        updateStatus("Creating AP: " + ssid);
        Toast.makeText(requireContext(), "AP mode: " + ssid, Toast.LENGTH_SHORT).show();
    }

    private void scanHosts() {
        updateStatus("Scanning hosts...");
        new Thread(() -> {
            try {
                for (int i = 1; i < 255; i++) {
                    String host = "192.168.1." + i;
                    InetAddress inet = InetAddress.getByName(host);
                    if (inet.isReachable(200)) {
                        String finalHost = host;
                        handler.post(() -> updateStatus("Found: " + finalHost));
                    }
                }
                handler.post(() -> updateStatus("Host scan complete"));
            } catch (Exception e) {
                handler.post(() -> updateStatus("Scan error: " + e.getMessage()));
            }
        }).start();
    }

    private void toggleEvilPortal() {
        evilPortalRunning = !evilPortalRunning;
        if (evilPortalRunning) {
            startEvilPortalBtn.setText("Stop Portal");
            evilPortalSwitch.setChecked(true);
            updateStatus("Evil Portal running");
        } else {
            startEvilPortalBtn.setText("Evil Portal");
            evilPortalSwitch.setChecked(false);
            updateStatus("Evil Portal stopped");
        }
    }

    private void startTCPServer() {
        int port;
        try {
            port = Integer.parseInt(portInput.getText().toString().trim());
        } catch (NumberFormatException e) {
            port = 8080;
        }
        int finalPort = port;
        updateStatus("TCP Server on port " + port);
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(finalPort);
                Socket client = serverSocket.accept();
                handler.post(() -> updateStatus("Client connected"));
                serverSocket.close();
            } catch (Exception e) {
                handler.post(() -> updateStatus("Server error: " + e.getMessage()));
            }
        }).start();
    }

    private void startTCPClient() {
        String target = targetInput.getText().toString().trim();
        int port;
        try {
            port = Integer.parseInt(portInput.getText().toString().trim());
        } catch (NumberFormatException e) {
            port = 8080;
        }
        if (target.isEmpty()) {
            Toast.makeText(requireContext(), "Enter target IP", Toast.LENGTH_SHORT).show();
            return;
        }
        String finalTarget = target;
        int finalPort = port;
        updateStatus("Connecting to " + target + ":" + port);
        new Thread(() -> {
            try {
                Socket socket = new Socket(finalTarget, finalPort);
                handler.post(() -> updateStatus("Connected to " + finalTarget));
                socket.close();
            } catch (Exception e) {
                handler.post(() -> updateStatus("Connection error: " + e.getMessage()));
            }
        }).start();
    }
}