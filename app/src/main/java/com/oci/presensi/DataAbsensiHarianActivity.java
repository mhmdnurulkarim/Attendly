package com.oci.presensi;

import static com.oci.presensi.util.Utils.getDate;
import static com.oci.presensi.util.Utils.getDateTime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.oci.presensi.adapter.AdapterDataAbsensiHarian;
import com.oci.presensi.databinding.ActivityDataAbsensiHarianBinding;
import com.oci.presensi.helper.DataHelper;
import com.oci.presensi.model.ModelAbsensi;
import com.oci.presensi.util.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class DataAbsensiHarianActivity extends AppCompatActivity {

    ActivityDataAbsensiHarianBinding binding;
    DataHelper dbHelper;
    List<ModelAbsensi> listAbsensi;
    List<ModelAbsensi> listAbsensiToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataAbsensiHarianBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DataHelper(this);
        dbHelper.getAttendanceFromFirestore();
        listAbsensi = new ArrayList<>();
        listAbsensiToday = new ArrayList<>();

        fetchAttendanceData();

        binding.txtTanggal.setText(getDateTime());

        setupBackPressedHandler();
    }

    private void fetchAttendanceData() {
        int userId = PreferenceUtils.getIdAkun(getApplicationContext());
        dbHelper.fetchAttendanceData(userId, new DataHelper.FetchAttendanceCallback() {
            @Override
            public void onFetchComplete(List<ModelAbsensi> attendanceList) {
                displayAttendance(attendanceList);
            }

            @Override
            public void onFetchFailed(Exception e) {
                Toast.makeText(DataAbsensiHarianActivity.this, "Gagal mengambil data absensi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayAttendance(List<ModelAbsensi> attendanceList) {
        listAbsensi.clear();
        listAbsensi.addAll(attendanceList);

        listAbsensiToday.clear();
        if (PreferenceUtils.getIdRole(getApplicationContext()) == 2) {
            // semua karyawan
            for (ModelAbsensi absensi : listAbsensi) {
                if (absensi.getTimestamp().substring(0, 10).equalsIgnoreCase(getDate())) {
                    listAbsensiToday.add(absensi);
                }
            }
        } else {
            for (ModelAbsensi absensi : listAbsensi) {
                if (absensi.getTimestamp().substring(0, 10).equalsIgnoreCase(getDate())) {
                    if (absensi.getId_user() == PreferenceUtils.getIdAkun(getApplicationContext())) {
                        listAbsensiToday.add(absensi);
                    }
                }
            }
        }

        if (!listAbsensiToday.isEmpty()) {
            AdapterDataAbsensiHarian itemList = new AdapterDataAbsensiHarian(listAbsensiToday, dbHelper);
            binding.rvDataAbsensiHarian.setLayoutManager(new LinearLayoutManager(DataAbsensiHarianActivity.this));
            binding.rvDataAbsensiHarian.setAdapter(itemList);
        } else {
            Toast.makeText(this, "Anda belum memiliki data absensi harian", Toast.LENGTH_SHORT).show();
        }
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