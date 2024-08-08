package com.oci.presensi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
        listAkun = dbHelper.getAllAkun();

        if (PreferenceUtils.getIdAkun(this) == 0) {
            // Jika tidak ada ID akun yang tersimpan, tidak melakukan apa-apa
        } else {
            goToHome();
        }

        binding.btnLogin.setOnClickListener(v -> {
            String username = binding.inputUsername.getText().toString();
            String password = binding.inputPassword.getText().toString();
            if (!listAkun.isEmpty()) {
                checkAkun(username, password);
            } else {
                Toast.makeText(this, "Anda belum memiliki data akun", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkAkun(String username, String password) {
        boolean isCorrect = false;
        Log.i("LOGIN", "Memeriksa akun");

        try {
            for (ModelAkun akun : listAkun) {
                if (akun.getUsername().equalsIgnoreCase(username) && akun.getPassword().equalsIgnoreCase(password)) {
                    isCorrect = true;
                    Log.i("LOGIN", "Akun ditemukan");

                    int id = akun.getIdUser();
                    ModelAkun akunDetail = dbHelper.getAkun(id);
                    if (akunDetail != null) {
                        Log.i("LOGIN", "Detail akun tidak null");
                        saveAkunToPreferences(akunDetail);
                        goToHome();
                    } else {
                        Toast.makeText(LoginActivity.this, "Akun Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        } catch (Exception e) {
            Log.e("LOGIN", "Error saat memeriksa akun", e);
        }

        if (!isCorrect) {
            Toast.makeText(LoginActivity.this, "Akun Belum Terdaftar", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveAkunToPreferences(ModelAkun akun) {
        PreferenceUtils.saveIdAkun(akun.getIdUser(), getApplicationContext());
        PreferenceUtils.saveIdRole(akun.getId_role(), getApplicationContext());
        PreferenceUtils.saveNama(akun.getNama(), getApplicationContext());
        PreferenceUtils.saveDivisi(akun.getDivisi(), getApplicationContext());
        PreferenceUtils.saveNik(akun.getNik(), getApplicationContext());
    }

    private void showKeluarDialog() {
        new AlertDialog.Builder(this)
                .setTitle("KELUAR")
                .setMessage("Apakah anda yakin ingin keluar?")
                .setPositiveButton("YA", (dialog, which) -> {
                    finish();
                    finishAffinity();
                })
                .setNegativeButton("TIDAK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void goToHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        showKeluarDialog();
    }
}