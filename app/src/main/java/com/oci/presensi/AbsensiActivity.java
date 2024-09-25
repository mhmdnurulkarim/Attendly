package com.oci.presensi;

import static com.oci.presensi.util.Utils.getDate;
import static com.oci.presensi.util.Utils.getDateTime;
import static com.oci.presensi.util.Utils.getTime;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.oci.presensi.databinding.ActivityAbsensiBinding;
import com.oci.presensi.helper.DataHelper;
import com.oci.presensi.model.ModelAbsensi;
import com.oci.presensi.util.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class AbsensiActivity extends AppCompatActivity {

    private ActivityAbsensiBinding binding;
    private DataHelper dbHelper;
    private List<ModelAbsensi> listAbsensiToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAbsensiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        dbHelper = new DataHelper(this);
        dbHelper.getAttendanceFromFirestore();
        initializeAbsensiList();

        setupKaryawanInfo();
        setupButtonListeners();

        setupBackPressedHandler();
    }

    private void initializeAbsensiList() {
        List<ModelAbsensi> listAbsensi = dbHelper.getAllAbsensi();
        listAbsensiToday = new ArrayList<>();

        String todayDate = getDate();
        int userId = PreferenceUtils.getIdAkun(getApplicationContext());

        for (ModelAbsensi absensi : listAbsensi) {
            if (absensi.getTimestamp().startsWith(todayDate) && absensi.getId_user() == userId) {
                listAbsensiToday.add(absensi);
            }
        }
    }

    private void setupKaryawanInfo() {
        binding.txtTanggal.setText(getDateTime());
        binding.txtIDKaryawan.setText("ID Karyawan : " + PreferenceUtils.getIdAkun(getApplicationContext()));
        binding.txtNamaKaryawan.setText("Nama Karyawan : " + PreferenceUtils.getNama(getApplicationContext()));
        binding.txtNikKaryawan.setText("NIK Karyawan : " + PreferenceUtils.getNik(getApplicationContext()));
        binding.txtDivisiKaryawan.setText("Divisi Karyawan : " + PreferenceUtils.getDivisi(getApplicationContext()));
        binding.txtRoleKaryawan.setText("Role Karyawan : " + PreferenceUtils.getIdRole(getApplicationContext()));
    }

    private void setupButtonListeners() {
        String now = getTime();

        if (isDatangTime(now)) {
            setupDatangButton();
        } else if (isPulangTime(now)) {
            setupPulangButton();
        } else {
            disableButtons();
        }
    }

    private boolean isDatangTime(String now) {
        return now.compareTo("01:00") >= 0 && now.compareTo("08:00") <= 0;
    }

    private boolean isPulangTime(String now) {
        return now.compareTo("16:00") >= 0 && now.compareTo("23:00") <= 0;
    }

    private void setupDatangButton() {
        binding.btnDatang.setEnabled(true);
        binding.btnDatang.setBackgroundColor(ContextCompat.getColor(this, R.color.greenText));
        binding.btnPulang.setEnabled(false);
        binding.btnPulang.setBackgroundColor(ContextCompat.getColor(this, R.color.greyText));
        binding.btnDatang.setOnClickListener(v -> handleAbsensi("DATANG"));
    }

    private void setupPulangButton() {
        binding.btnDatang.setEnabled(false);
        binding.btnDatang.setBackgroundColor(ContextCompat.getColor(this, R.color.greyText));
        binding.btnPulang.setEnabled(true);
        binding.btnPulang.setBackgroundColor(ContextCompat.getColor(this, R.color.redText));
        binding.btnPulang.setOnClickListener(v -> handleAbsensi("PULANG"));
    }

    private void disableButtons() {
        binding.btnDatang.setEnabled(false);
        binding.btnDatang.setBackgroundColor(ContextCompat.getColor(this, R.color.greyText));
        binding.btnPulang.setEnabled(false);
        binding.btnPulang.setBackgroundColor(ContextCompat.getColor(this, R.color.greyText));
    }

    private void handleAbsensi(String keterangan) {
        if (isAlreadyAbsensi(keterangan)) {
            String message = "Anda sudah melakukan absensi " + keterangan.toLowerCase() + "!";
            Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
        } else {
            showAbsensiDialog(keterangan);
        }
    }

    private boolean isAlreadyAbsensi(String keterangan) {
        for (ModelAbsensi absensi : listAbsensiToday) {
            if (absensi.getKeterangan().equalsIgnoreCase(keterangan)) {
                return true;
            }
        }
        return false;
    }

    private void showAbsensiDialog(String keterangan) {
        String title = "ABSEN " + keterangan;
        String message = "Lakukan absensi " + keterangan.toLowerCase() + "?";

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("YA", (dialog, which) -> {
                    saveAbsensi(keterangan);
                    showSuccessDialog(keterangan);
                })
                .setNegativeButton("TIDAK", (dialog, which) -> dialog.dismiss())
                .create().show();
    }

    private void saveAbsensi(String keterangan) {
        int id = dbHelper.getLastIdAbsensi();
        String now = getDateTime();
        int idAkun = PreferenceUtils.getIdAkun(getApplicationContext());
        dbHelper.addNewAbsensi(id, now, idAkun, keterangan);
    }

    private void showSuccessDialog(String keterangan) {
        String message = "BERHASIL MELAKUKAN ABSENSI " + keterangan;
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("OK", (dialog, id) -> finish())
                .create().show();
    }

    private void setupBackPressedHandler() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}