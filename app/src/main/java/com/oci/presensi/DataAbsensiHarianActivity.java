package com.oci.presensi;

import static com.oci.presensi.util.Utils.getDate;
import static com.oci.presensi.util.Utils.getDateTime;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.oci.presensi.adapter.AdapterDataAbsensiHarian;
import com.oci.presensi.databinding.ActivityDataAbsensiHarianBinding;
import com.oci.presensi.helper.DataHelper;
import com.oci.presensi.model.ModelAbsensi;
import com.oci.presensi.util.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class DataAbsensiHarianActivity extends AppCompatActivity {

    private ActivityDataAbsensiHarianBinding binding;
    private DataHelper dbHelper;
    private List<ModelAbsensi> listAbsensi;
    private List<ModelAbsensi> listAbsensiToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataAbsensiHarianBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        dbHelper = new DataHelper(this);
        dbHelper.getAttendanceFromFirestore();
        listAbsensi = new ArrayList<>();
        listAbsensiToday = new ArrayList<>();

        setupUI();
        fetchAttendanceData();
        setupBackPressedHandler();
    }

    private void setupUI() {
        binding.txtTanggal.setText(getDateTime());
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
                showFetchFailedMessage(e.getMessage());
            }
        });
    }

    private void displayAttendance(List<ModelAbsensi> attendanceList) {
        listAbsensi.clear();
        listAbsensi.addAll(attendanceList);

        listAbsensiToday.clear();
        listAbsensiToday.addAll(filterAbsensiToday(attendanceList));

        if (!listAbsensiToday.isEmpty()) {
            setupRecyclerView(listAbsensiToday);
        } else {
            showNoDataMessage();
        }
    }

    private List<ModelAbsensi> filterAbsensiToday(List<ModelAbsensi> attendanceList) {
        List<ModelAbsensi> filteredList = new ArrayList<>();
        int userId = PreferenceUtils.getIdAkun(getApplicationContext());
        int userRole = PreferenceUtils.getIdRole(getApplicationContext());

        for (ModelAbsensi absensi : attendanceList) {
            if (absensi.getTimestamp().substring(0, 10).equalsIgnoreCase(getDate())) {
                if (userRole == 2 || absensi.getId_user() == userId) {
                    filteredList.add(absensi);
                }
            }
        }

        return filteredList;
    }

    private void setupRecyclerView(List<ModelAbsensi> attendanceList) {
        AdapterDataAbsensiHarian itemList = new AdapterDataAbsensiHarian(attendanceList, dbHelper);
        binding.rvDataAbsensiHarian.setLayoutManager(new LinearLayoutManager(this));
        binding.rvDataAbsensiHarian.setAdapter(itemList);
    }

    private void showFetchFailedMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), "Gagal mengambil data absensi: " + message, Snackbar.LENGTH_SHORT).show();
    }

    private void showNoDataMessage() {
        Snackbar.make(findViewById(android.R.id.content), "Anda belum memiliki data absensi harian", Snackbar.LENGTH_SHORT).show();
    }

    private void setupBackPressedHandler() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }
}