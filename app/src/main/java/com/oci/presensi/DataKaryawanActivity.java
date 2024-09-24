package com.oci.presensi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.oci.presensi.adapter.AdapterDataKaryawan;
import com.oci.presensi.databinding.ActivityDataKaryawanBinding;
import com.oci.presensi.helper.DataHelper;
import com.oci.presensi.model.ModelAkun;
import com.oci.presensi.util.PreferenceUtils;

import java.util.List;

public class DataKaryawanActivity extends AppCompatActivity {

    private ActivityDataKaryawanBinding binding;
    private DataHelper dbHelper;
    private List<ModelAkun> listPegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataKaryawanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DataHelper(this);
        dbHelper.getAttendanceFromFirestore();
        listPegawai = dbHelper.getAllPegawai(3);

        setupRecyclerView();
        setupBackPressedHandler();

        binding.fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, DetailKaryawanActivity.class));
        });
    }

    private void setupRecyclerView() {
        if (listPegawai.isEmpty()) {
            Toast.makeText(this, "Anda belum memiliki data karyawan", Toast.LENGTH_SHORT).show();
            return;
        }

        AdapterDataKaryawan itemList = new AdapterDataKaryawan(this, listPegawai);
        binding.rvDataKaryawan.setLayoutManager(new LinearLayoutManager(this));
        binding.rvDataKaryawan.setAdapter(itemList);
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