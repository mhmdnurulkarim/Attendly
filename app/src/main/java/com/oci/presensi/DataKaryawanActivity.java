package com.oci.presensi;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.oci.presensi.adapter.AdapterDataKaryawan;
import com.oci.presensi.databinding.ActivityDataKaryawanBinding;
import com.oci.presensi.helper.DataHelper;
import com.oci.presensi.model.ModelAkun;

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

        init();
    }

    private void init() {
        dbHelper = new DataHelper(this);
        dbHelper.getAkunFromFirebase(() -> {});
        listPegawai = dbHelper.getAllAkun();

        setupRecyclerView();
        setupBackPressedHandler();
        setupFabAdd();
    }

    private void setupRecyclerView() {
        if (listPegawai.isEmpty()) {
            showNoDataMessage();
            return;
        }

        AdapterDataKaryawan itemList = new AdapterDataKaryawan(this, listPegawai);
        binding.rvDataKaryawan.setLayoutManager(new LinearLayoutManager(this));
        binding.rvDataKaryawan.setAdapter(itemList);
    }

    private void showNoDataMessage() {
        Snackbar.make(binding.getRoot(), "Anda belum memiliki data karyawan", Snackbar.LENGTH_SHORT).show();
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

    private void setupFabAdd() {
        binding.fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, DetailKaryawanActivity.class));
            finish();
        });
    }
}