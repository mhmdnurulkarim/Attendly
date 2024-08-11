package com.oci.presensi;

import static com.oci.presensi.util.Utils.getPeriode;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.oci.presensi.adapter.AdapterDataRekapAbsensi;
import com.oci.presensi.databinding.ActivityDataRekapAbsensiBinding;
import com.oci.presensi.helper.DataHelper;
import com.oci.presensi.model.ModelAkun;

import java.util.List;

public class DataRekapAbsensiActivity extends AppCompatActivity {

    ActivityDataRekapAbsensiBinding binding;
    DataHelper dbHelper;
    List<ModelAkun> listPegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataRekapAbsensiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DataHelper(this);
        listPegawai = dbHelper.getAllAkun();

        binding.textTitle.setText("DATA REKAP ABSENSI");
        binding.txtPeriode.setText("Periode " + getPeriode());

        if (!listPegawai.isEmpty()) {
            setupRecyclerView();
        } else {
            Toast.makeText(this, "Anda belum memiliki data absensi", Toast.LENGTH_SHORT).show();
        }

        setupBackPressedHandler();
    }

    private void setupRecyclerView() {
        AdapterDataRekapAbsensi itemList = new AdapterDataRekapAbsensi(this, listPegawai);
        binding.rvDataRekapAbsensi.setLayoutManager(new LinearLayoutManager(this));
        binding.rvDataRekapAbsensi.setAdapter(itemList);
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