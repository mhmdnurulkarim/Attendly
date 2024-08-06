package com.sofia.presensi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sofia.presensi.adapter.AdapterDataPegawai;
import com.sofia.presensi.databinding.DataKaryawanBinding;
import com.sofia.presensi.helper.DataHelper;
import com.sofia.presensi.model.ModelAkun;
import com.sofia.presensi.util.RecyclerItemClickListener;

import java.util.List;

public class DataKaryawan extends AppCompatActivity {

    DataKaryawanBinding binding;
    DataHelper dbHelper;
    List<ModelAkun> listPegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.data_karyawan);

        dbHelper =  new DataHelper(this);
        listPegawai = dbHelper.getAllPegawai(3);

        if (listPegawai.size()>0) {
            AdapterDataPegawai itemList = new AdapterDataPegawai(listPegawai);
            binding.rvDataKaryawan.setLayoutManager(new LinearLayoutManager(DataKaryawan.this));
            binding.rvDataKaryawan.setAdapter(itemList);
        } else {
            Toast.makeText(this, "anda belum memiliki data karyawan", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(DataKaryawan.this, Home.class);
        startActivity(a);
        finish();
    }
}