package com.oci.presensi;

import static com.oci.presensi.util.Utils.getPeriode;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.oci.presensi.adapter.AdapterDataAbsensiHarian;
import com.oci.presensi.databinding.DataRekapAbsensiDetailBinding;
import com.oci.presensi.helper.DataHelper;
import com.oci.presensi.model.ModelAbsensi;
import com.oci.presensi.model.ModelAkun;

import java.util.List;

public class DataRekapAbsensiDetail extends AppCompatActivity {

    DataRekapAbsensiDetailBinding binding;
    DataHelper dbHelper;
    List<ModelAbsensi> listAbsensi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.data_rekap_absensi_detail);

        Intent intent = getIntent();
        int id = intent.getIntExtra("idUser", 0);
        dbHelper = new DataHelper(this);
        listAbsensi = dbHelper.getAllAbsensiByIdUser(id);
        ModelAkun akun = dbHelper.getAkun(id);

        binding.txtPeriode.setText("Periode " + getPeriode());
        binding.txtIDKaryawan.setText("ID Karyawan : " + akun.getId_role());
        binding.txtNamaKaryawan.setText("Nama Karyawan : " + akun.getNama());
        binding.txtNikKaryawan.setText("NIK Karyawan : " + akun.getNik());
        binding.txtDivisiKaryawan.setText("Divisi Karyawan : " + akun.getDivisi());
        binding.txtRoleKaryawan.setText("Role Karyawan : " + akun.getId_role());

        if (listAbsensi.size() > 0) {
            AdapterDataAbsensiHarian itemList = new AdapterDataAbsensiHarian(listAbsensi, dbHelper);
            binding.rvDataRekapAbsensiDetail.setLayoutManager(new LinearLayoutManager(DataRekapAbsensiDetail.this));
            binding.rvDataRekapAbsensiDetail.setAdapter(itemList);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(DataRekapAbsensiDetail.this, DataRekapAbsensi.class);
        startActivity(a);
        finish();
    }
}