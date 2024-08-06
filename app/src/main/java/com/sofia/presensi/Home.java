package com.sofia.presensi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.sofia.presensi.databinding.HomeBinding;
import com.sofia.presensi.util.PreferenceUtils;

public class Home extends AppCompatActivity {

    HomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.home);

        if (PreferenceUtils.getIdRole(getApplicationContext())==3){
            binding.btnDataKaryawan.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rect_gray_radius_smaller));
            binding.btnDataRekapAbsensi.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rect_gray_radius_smaller));
        }

        binding.txtIDKaryawan.setText("ID Karyawan : " + PreferenceUtils.getIdRole(getApplicationContext()));
        binding.txtNamaKaryawan.setText("Nama Karyawan : " + PreferenceUtils.getNama(getApplicationContext()));

        binding.btnAbsensi.setOnClickListener(v->{
            Intent a = new Intent(Home.this, Absensi.class);
            startActivity(a);
            finish();
        });

        binding.btnDataKaryawan.setOnClickListener(v->{
            if (PreferenceUtils.getIdRole(getApplicationContext())==3){ } else {
                Intent a = new Intent(Home.this, DataKaryawan.class);
                startActivity(a);
                finish();
            }
        });

        binding.btnDataAbsensiHarian.setOnClickListener(v->{
            Intent a = new Intent(Home.this, DataAbsensiHarian.class);
            startActivity(a);
            finish();
        });

        binding.btnDataRekapAbsensi.setOnClickListener(v->{
            if (PreferenceUtils.getIdRole(getApplicationContext())==3){ } else {
                Intent a = new Intent(Home.this, DataRekapAbsensi.class);
                startActivity(a);
                finish();
            }
        });

        binding.btnLogOut.setOnClickListener(v->{
            showLogoutDialog();
        });
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("LOG OUT");
        builder.setMessage("Apakah anda yakin ingin Log Out ?");

        builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PreferenceUtils.saveIdAkun(0, getApplicationContext());
                PreferenceUtils.saveIdRole(0, getApplicationContext());
                PreferenceUtils.saveNama("", getApplicationContext());
                PreferenceUtils.saveDivisi("", getApplicationContext());
                PreferenceUtils.saveNik(0, getApplicationContext());
                Intent a = new Intent(Home.this, Login.class);
                startActivity(a);
                finish();
            }
        });

        builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showKeluarDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("KELUAR");
        builder.setMessage("Apakah anda yakin ingin keluar ?");

        builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                finishAffinity();
            }
        });

        builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showKeluarDialog();
    }

}