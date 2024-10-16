package com.oci.presensi;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.oci.presensi.databinding.ActivityLoginBinding;
import com.oci.presensi.helper.DataHelper;
import com.oci.presensi.model.ModelAkun;
import com.oci.presensi.util.PreferenceUtils;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private DataHelper dbHelper;
    private List<ModelAkun> listAkun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseApp.initializeApp(this);
        dbHelper = new DataHelper(this);
        dbHelper.getAkunFromFirebase(this::init);
    }

    private void init() {
        listAkun = dbHelper.getAllAkun();

        setupLoginButton();
        setupBackPressedHandler();
    }

    private void setupLoginButton() {
        binding.btnLogin.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String username = binding.inputUsername.getText().toString();
        String password = binding.inputPassword.getText().toString();

        if (!isValidInput(username, password)) {
            return;
        }

        if (listAkun.isEmpty()) {
            showNoAccountMessage();
            return;
        }

        ModelAkun akun = findAkun(username, password);
        if (akun != null) {
            saveAkunToPreferences(akun);
            goToPage(HomeActivity.class);
        } else {
            showInvalidAccountMessage();
        }
    }

    private boolean isValidInput(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            Snackbar.make(binding.getRoot(), "Username dan Password harus diisi", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showNoAccountMessage() {
        Snackbar.make(binding.getRoot(), "Anda belum memiliki data akun", Snackbar.LENGTH_SHORT).show();
    }

    private void showInvalidAccountMessage() {
        Snackbar.make(binding.getRoot(), "Akun Belum Terdaftar", Snackbar.LENGTH_SHORT).show();
    }

    private ModelAkun findAkun(String username, String password) {
        return listAkun.stream()
                .filter(akun -> akun.getUsername().equalsIgnoreCase(username) &&
                        akun.getPassword().equalsIgnoreCase(password))
                .findFirst()
                .orElse(null);
    }

    private void saveAkunToPreferences(ModelAkun akun) {
        PreferenceUtils.saveIdAkun(akun.getIdUser(), getApplicationContext());
        PreferenceUtils.saveIdRole(akun.getId_role(), getApplicationContext());
        PreferenceUtils.saveNama(akun.getNama(), getApplicationContext());
        PreferenceUtils.saveDivisi(akun.getDivisi(), getApplicationContext());
        PreferenceUtils.saveNik(akun.getNik(), getApplicationContext());
    }

    private void setupBackPressedHandler() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showKeluarDialog();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void showKeluarDialog() {
        new AlertDialog.Builder(this)
                .setTitle("KELUAR")
                .setMessage("Apakah anda yakin ingin keluar?")
                .setPositiveButton("YA", (dialog, which) -> {
                    finish();
                })
                .setNegativeButton("TIDAK", (dialog, which) -> dialog.dismiss())
                .create().show();
    }

    private void goToPage(Class<?> destinationActivity) {
        Intent intent = new Intent(LoginActivity.this, destinationActivity);
        startActivity(intent);
        finish();
    }
}