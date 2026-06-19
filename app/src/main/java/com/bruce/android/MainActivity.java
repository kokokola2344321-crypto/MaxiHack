package com.bruce.android;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bruce.android.fragments.BLEFragment;
import com.bruce.android.fragments.ClockFragment;
import com.bruce.android.fragments.ConfigFragment;
import com.bruce.android.fragments.ConnectFragment;
import com.bruce.android.fragments.FMFragment;
import com.bruce.android.fragments.IRFragment;
import com.bruce.android.fragments.NRF24Fragment;
import com.bruce.android.fragments.OthersFragment;
import com.bruce.android.fragments.RFFragment;
import com.bruce.android.fragments.RFIDFragment;
import com.bruce.android.fragments.ScriptsFragment;
import com.bruce.android.fragments.WiFiFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new WiFiFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_wifi);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        int id = item.getItemId();
        if (id == R.id.nav_wifi) {
            selectedFragment = new WiFiFragment();
        } else if (id == R.id.nav_ble) {
            selectedFragment = new BLEFragment();
        } else if (id == R.id.nav_rf) {
            selectedFragment = new RFFragment();
        } else if (id == R.id.nav_rfid) {
            selectedFragment = new RFIDFragment();
        } else if (id == R.id.nav_ir) {
            selectedFragment = new IRFragment();
        } else if (id == R.id.nav_fm) {
            selectedFragment = new FMFragment();
        } else if (id == R.id.nav_nrf24) {
            selectedFragment = new NRF24Fragment();
        } else if (id == R.id.nav_scripts) {
            selectedFragment = new ScriptsFragment();
        } else if (id == R.id.nav_others) {
            selectedFragment = new OthersFragment();
        } else if (id == R.id.nav_clock) {
            selectedFragment = new ClockFragment();
        } else if (id == R.id.nav_connect) {
            selectedFragment = new ConnectFragment();
        } else if (id == R.id.nav_config) {
            selectedFragment = new ConfigFragment();
        }

        if (selectedFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, selectedFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
</write_to_file>