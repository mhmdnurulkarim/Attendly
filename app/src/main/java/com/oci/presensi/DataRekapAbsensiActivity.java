package com.oci.presensi;

import static com.oci.presensi.util.Utils.getPeriode;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.oci.presensi.adapter.AdapterDataRekapAbsensi;
import com.oci.presensi.databinding.ActivityDataRekapAbsensiBinding;
import com.oci.presensi.helper.DataHelper;
import com.oci.presensi.model.ModelAkun;

import java.util.List;

public class DataRekapAbsensiActivity extends AppCompatActivity {

    private ActivityDataRekapAbsensiBinding binding;
    private DataHelper dbHelper;
    private List<ModelAkun> listPegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataRekapAbsensiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        dbHelper = new DataHelper(this);
        dbHelper.getAkunFromFirebase(() -> {});
        dbHelper.getAttendanceFromFirestore();
        listPegawai = dbHelper.getAllAkun();

        setupUI();
        setupRecyclerView();
        setupBackPressedHandler();
    }

    private void setupUI() {
        binding.textTitle.setText("DATA REKAP ABSENSI");
        binding.txtPeriode.setText("Periode " + getPeriode());

        if (listPegawai.isEmpty()) {
            showNoDataMessage();
        }
    }

    private void setupRecyclerView() {
        if (!listPegawai.isEmpty()) {
            AdapterDataRekapAbsensi itemList = new AdapterDataRekapAbsensi(this, listPegawai);
            binding.rvDataRekapAbsensi.setLayoutManager(new LinearLayoutManager(this));
            binding.rvDataRekapAbsensi.setAdapter(itemList);
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

    private void showNoDataMessage() {
        Snackbar.make(binding.getRoot(), "Anda belum memiliki data absensi", Snackbar.LENGTH_SHORT).show();
    }
}