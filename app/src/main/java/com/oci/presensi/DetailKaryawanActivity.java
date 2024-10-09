package com.oci.presensi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.oci.presensi.databinding.ActivityDetailKaryawanBinding;
import com.oci.presensi.helper.DataHelper;
import com.oci.presensi.model.ModelAkun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailKaryawanActivity extends AppCompatActivity {

    private ActivityDetailKaryawanBinding binding;
    private DataHelper dbHelper;
    private ModelAkun akun;
    private String username, password, nama, nik, divisi;
    private int idUser, idRole;
    private Map<Integer, String> divisiOptions = new HashMap<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailKaryawanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        int id = getIntent().getIntExtra("idUser", 0);
        divisiOptions.put(1, "Manager");
        divisiOptions.put(2, "Admin Koordinator");
        divisiOptions.put(3, "Pekerja Gudang");

        dbHelper = new DataHelper(this);
        dbHelper.getAkunFromFirebase(() -> {});

        setupDivisiDropdown();

        if (id != 0) {
            setupEditMode(id);
        } else {
            setupAddMode();
        }

        setupButtonListeners();
        setupBackPressedHandler();
    }

    private void setupDivisiDropdown() {
        List<String> divisiList = new ArrayList<>(divisiOptions.values());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, divisiList);
        binding.inputDivisi.setAdapter(adapter);

        binding.inputDivisi.setOnItemClickListener((parent, view, position, id) -> {
            divisi = adapter.getItem(position);
            idRole = getIdFromDivisi(divisi);
        });
    }

    // Mendapatkan idRole dari nama divisi
    private int getIdFromDivisi(String divisi) {
        for (Map.Entry<Integer, String> entry : divisiOptions.entrySet()) {
            if (entry.getValue().equals(divisi)) {
                return entry.getKey();
            }
        }
        return 0;
    }

    private void setupEditMode(int id) {
        binding.btnTambah.setVisibility(View.GONE);
        binding.lLDelete.setVisibility(View.VISIBLE);

        akun = dbHelper.getAkun(id);
        setText(akun);
    }

    private void setupAddMode() {
        binding.btnTambah.setVisibility(View.VISIBLE);
        binding.lLDelete.setVisibility(View.GONE);
        clearText();
    }

    private void setupButtonListeners() {
        binding.btnTambah.setOnClickListener(v -> tambahKaryawan());
        binding.btnUbah.setOnClickListener(v -> ubahKaryawan(akun));
        binding.btnHapus.setOnClickListener(v -> showDeleteConfirmationDialog(akun));
    }

    private void tambahKaryawan() {
        validateInput();

        idUser = dbHelper.getLastIdAkun();

        ModelAkun akun = new ModelAkun(idUser, username, password, idRole, nama, nik, divisi);
        dbHelper.addNewAkun(akun);

        clearText();
        move();
        Snackbar.make(binding.getRoot(), "Akun berhasil ditambahkan", Snackbar.LENGTH_SHORT).show();
    }

    private void ubahKaryawan(ModelAkun akun) {
        validateInput();

        akun.setUsername(username);
        akun.setPassword(password);
        akun.setId_role(idRole);
        akun.setNama(nama);
        akun.setNik(nik);
        akun.setDivisi(divisi);

        dbHelper.updateAkun(akun);
        clearText();
        move();
        Snackbar.make(binding.getRoot(), "Data akun berhasil diupdate", Snackbar.LENGTH_SHORT).show();
    }

    private void hapusKaryawan(ModelAkun akun) {
        dbHelper.deleteAkun(akun.getIdUser());
        clearText();
        move();
        Snackbar.make(binding.getRoot(), "Akun berhasil dihapus", Snackbar.LENGTH_SHORT).show();
    }

    private void setText(ModelAkun akun) {
        binding.inputUsername.setText(akun.getUsername());
        binding.inputPassword.setText(akun.getPassword());
        binding.inputNama.setText(akun.getNama());
        binding.inputNik.setText(akun.getNik());

        binding.inputDivisi.setText(akun.getDivisi(), false);
        divisi = akun.getDivisi();
        idRole = getIdFromDivisi(divisi);
    }

    private void getText() {
        username = binding.inputUsername.getText().toString();
        password = binding.inputPassword.getText().toString();
        nama = binding.inputNama.getText().toString();
        nik = binding.inputNik.getText().toString();
    }

    private void validateInput() {
        getText();

        if (username.isEmpty()) {
            binding.inputUsername.setError("Username tidak boleh kosong");
            binding.inputUsername.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            binding.inputPassword.setError("Password tidak boleh kosong");
            binding.inputPassword.requestFocus();
            return;
        }

        if (nama.isEmpty()) {
            binding.inputNama.setError("Nama tidak boleh kosong");
            binding.inputNama.requestFocus();
            return;
        }

        if (nik.isEmpty()) {
            binding.inputNik.setError("NIK tidak boleh kosong");
            binding.inputNik.requestFocus();
        }

    }

    private void clearText() {
        binding.inputUsername.setText("");
        binding.inputPassword.setText("");
        binding.inputNama.setText("");
        binding.inputNik.setText("");
        binding.inputDivisi.setText("");
    }

    private void showDeleteConfirmationDialog(ModelAkun akun) {
        new AlertDialog.Builder(this)
                .setTitle("Hapus Data Karyawan")
                .setMessage("Apakah anda yakin ingin Hapus Data Karyawan ini?")
                .setPositiveButton("YA", (dialog, which) -> hapusKaryawan(akun))
                .setNegativeButton("TIDAK", (dialog, which) -> dialog.dismiss())
                .create().show();
    }

    private void move(){
        startActivity(new Intent(this, DataKaryawanActivity.class));
        finish();
    }

    private void setupBackPressedHandler() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(binding.getRoot().getContext(), DataKaryawanActivity.class));
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}