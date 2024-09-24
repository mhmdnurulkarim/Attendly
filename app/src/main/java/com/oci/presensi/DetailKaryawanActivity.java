package com.oci.presensi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.oci.presensi.databinding.ActivityDetailKaryawanBinding;
import com.oci.presensi.helper.DataHelper;
import com.oci.presensi.model.ModelAkun;

public class DetailKaryawanActivity extends AppCompatActivity {

    private ActivityDetailKaryawanBinding binding;
    private DataHelper dbHelper;
    private ModelAkun akun;
    private String idUserString, username, password, idRoleString, nama, nik, divisi;
    private int idUser, idRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailKaryawanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int id = getIntent().getIntExtra("idUser", 0);

        dbHelper = new DataHelper(this);
//        dbHelper.getAttendanceFromFirestore();

        if (id != 0) {
            binding.btnTambah.setVisibility(View.GONE);
            binding.lLDelete.setVisibility(View.VISIBLE);

            akun = dbHelper.getAkun(id);
            setText(akun);
        } else {
            binding.btnTambah.setVisibility(View.VISIBLE);
            binding.lLDelete.setVisibility(View.GONE);
            clearText();
        }

        binding.btnTambah.setOnClickListener(v -> tambahKaryawan());

        binding.btnUbah.setOnClickListener(v -> ubahKaryawan(akun));

        binding.btnHapus.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Hapus Data Karyawan")
                    .setMessage("Apakah anda yakin ingin Hapus Data Karyawan ini?")
                    .setPositiveButton("YA", (dialog, which) -> {
                        hapusKaryawan(akun);
                    })
                    .setNegativeButton("TIDAK", (dialog, which) -> dialog.dismiss())
                    .create().show();
        });
    }

    private void tambahKaryawan() {
        getText();

        ModelAkun akun = new ModelAkun(idUser, username, password, idRole, nama, nik, divisi);
        dbHelper.addNewAkun(akun);

        clearText();
        back();
    }

    private void ubahKaryawan(ModelAkun akun) {
        getText();

        akun.setIdUser(idUser);
        akun.setUsername(username);
        akun.setPassword(password);
        akun.setId_role(idRole);
        akun.setNama(nama);
        akun.setNik(nik);
        akun.setDivisi(divisi);

        dbHelper.updateAkun(akun);
        clearText();
        back();
    }

    private void hapusKaryawan(ModelAkun akun) {
        dbHelper.deleteAkun(akun.getIdUser());
        clearText();
        back();
    }

    private void setText(ModelAkun akun) {
        binding.etId.setText(String.valueOf(akun.getIdUser()));
        binding.etUsername.setText(akun.getUsername());
        binding.etPassword.setText(akun.getPassword());
        binding.etIdRole.setText(String.valueOf(akun.getId_role()));
        binding.etNama.setText(akun.getNama());
        binding.etNik.setText(akun.getNik());
        binding.etDivisi.setText(akun.getDivisi());
    }

    private void getText() {
        idUserString = binding.etId.getText().toString();
        username = binding.etUsername.getText().toString();
        password = binding.etPassword.getText().toString();
        idRoleString = binding.etIdRole.getText().toString();
        nama = binding.etNama.getText().toString();
        nik = binding.etNik.getText().toString();
        divisi = binding.etDivisi.getText().toString();

        idUser = Integer.parseInt(idUserString);
        idRole = Integer.parseInt(idRoleString);
    }

    private void clearText() {
        binding.etNama.setText("");
        binding.etNik.setText("");
        binding.etDivisi.setText("");
    }

    private void back(){
        startActivity(new Intent(this, DataKaryawanActivity.class));
        finish();
    }
}