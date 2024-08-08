package com.oci.presensi;

import static com.oci.presensi.util.Utils.getDate;
import static com.oci.presensi.util.Utils.getDateTime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.oci.presensi.adapter.AdapterDataAbsensiHarian;
import com.oci.presensi.databinding.DataAbsensiHarianBinding;
import com.oci.presensi.helper.DataHelper;
import com.oci.presensi.model.ModelAbsensi;
import com.oci.presensi.util.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class DataAbsensiHarian extends AppCompatActivity {

    DataAbsensiHarianBinding binding;
    DataHelper dbHelper;
    List<ModelAbsensi> listAbsensi;
    List<ModelAbsensi> listAbsensiToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.data_absensi_harian);

        dbHelper = new DataHelper(this);
        listAbsensi = new ArrayList<>();
        listAbsensiToday = new ArrayList<>();

        fetchAttendanceData();

        binding.txtTanggal.setText(getDateTime());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(DataAbsensiHarian.this, HomeActivity.class);
        startActivity(a);
        finish();
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
                Toast.makeText(DataAbsensiHarian.this, "Gagal mengambil data absensi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

        if (listAbsensiToday.size() > 0) {
            AdapterDataAbsensiHarian itemList = new AdapterDataAbsensiHarian(listAbsensiToday, dbHelper);
            binding.rvDataAbsensiHarian.setLayoutManager(new LinearLayoutManager(DataAbsensiHarian.this));
            binding.rvDataAbsensiHarian.setAdapter(itemList);
        } else {
            Toast.makeText(this, "Anda belum memiliki data absensi harian", Toast.LENGTH_SHORT).show();
        }
    }
}