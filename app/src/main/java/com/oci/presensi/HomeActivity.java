package com.oci.presensi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.oci.presensi.databinding.ActivityHomeBinding;
import com.oci.presensi.helper.DataHelper;
import com.oci.presensi.util.PreferenceUtils;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private DataHelper dbHelper;
    private int userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        dbHelper = new DataHelper(this);
        dbHelper.getAkunFromFirebase(() -> {});
        dbHelper.getAttendanceFromFirestore();

        userRole = PreferenceUtils.getIdRole(getApplicationContext());

        setupUI();
        setupBackPressedHandler();
    }

    private void setupUI() {
        setupUserRole();
        setupButtonListeners();
    }

    private void setupUserRole() {
        if (userRole == 1) {
            hideButton(binding.btnDataKaryawan);
        } else if (userRole == 3) {
            hideButton(binding.btnDataKaryawan);
            hideButton(binding.btnDataRekapAbsensi);
        }

        binding.txtIDKaryawan.setText("ID Karyawan : " + userRole);
        binding.txtNamaKaryawan.setText("Nama Karyawan : " + PreferenceUtils.getNama(getApplicationContext()));
    }

    private void setupButtonListeners() {
        binding.btnAbsensi.setOnClickListener(v -> goToPage(AbsensiActivity.class));
        binding.btnDataKaryawan.setOnClickListener(v -> goToPage(DataKaryawanActivity.class));
        binding.btnDataAbsensiHarian.setOnClickListener(v -> goToPage(DataAbsensiHarianActivity.class));
        binding.btnDataRekapAbsensi.setOnClickListener(v -> goToPage(DataRekapAbsensiActivity.class));
        binding.btnLogOut.setOnClickListener(v -> showLogoutDialog());
    }

    private void hideButton(Button button) {
        button.setBackgroundColor(getResources().getColor(R.color.greyText));
        button.setVisibility(View.GONE);
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("LOG OUT")
                .setMessage("Apakah anda yakin ingin Log Out?")
                .setPositiveButton("YA", (dialog, which) -> {
                    clearPreferences();
                    goToLoginPage();
                })
                .setNegativeButton("TIDAK", (dialog, which) -> dialog.dismiss())
                .create().show();
    }

    private void clearPreferences() {
        PreferenceUtils.saveIdAkun(0, getApplicationContext());
        PreferenceUtils.saveIdRole(0, getApplicationContext());
        PreferenceUtils.saveNama("", getApplicationContext());
        PreferenceUtils.saveDivisi("", getApplicationContext());
        PreferenceUtils.saveNik("", getApplicationContext());
    }

    private void setupBackPressedHandler() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showKeluarDialog();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void showKeluarDialog() {
        new AlertDialog.Builder(this)
                .setTitle("KELUAR")
                .setMessage("Apakah anda yakin ingin keluar?")
                .setPositiveButton("YA", (dialog, which) -> finishAffinity())
                .setNegativeButton("TIDAK", (dialog, which) -> dialog.dismiss())
                .create().show();
    }

    private void goToPage(Class<?> destinationActivity) {
        Intent intent = new Intent(HomeActivity.this, destinationActivity);
        startActivity(intent);
    }

    private void goToLoginPage() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}