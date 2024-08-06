package com.sofia.presensi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.sofia.presensi.databinding.LoginBinding;
import com.sofia.presensi.helper.DataHelper;
import com.sofia.presensi.model.ModelAkun;
import com.sofia.presensi.util.PreferenceUtils;

import java.util.List;

public class Login extends AppCompatActivity {

    LoginBinding binding;
    DataHelper dbHelper;
    List<ModelAkun> listAkun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.login);

        dbHelper =  new DataHelper(this);
        listAkun = dbHelper.getAllAkun();

        if (PreferenceUtils.getIdAkun(this) == 0){
            // do nothing
            Log.i("LOGIN", "gaada id akun kesimpen");
        } else {
            goToHome();
        }

        binding.btnLogin.setOnClickListener(v->{
            String username = binding.inputUsername.getText().toString();
            String password = binding.inputPassword.getText().toString();
            if (listAkun.size()>0){
                Log.i("LOGIN", "list akun diatas 0");
                checkAkun(username, password);
            } else {
                Toast.makeText(this, "anda belum memiliki data akun", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkAkun(String un, String pw){
        int correct = 0;
        Log.i("LOGIN", "masuk check akun");
        try {
            for (int i = 0; i < listAkun.size(); i++) {
                String username = listAkun.get(i).getUsername();
                String password = listAkun.get(i).getPassword();
                if (username.equalsIgnoreCase(un) && password.equalsIgnoreCase(pw)) {
                    correct = 1;
                    Log.i("LOGIN", "correct = " + correct);
                    int id = listAkun.get(i).getIdUser();
                    ModelAkun akun = dbHelper.getAkun(id);
                    if (akun!=null){
                        Log.i("LOGIN", "akun gak null");
                        PreferenceUtils.saveIdAkun(akun.getIdUser(), getApplicationContext());
                        PreferenceUtils.saveIdRole(akun.getId_role(), getApplicationContext());
                        PreferenceUtils.saveNama(akun.getNama(), getApplicationContext());
                        PreferenceUtils.saveDivisi(akun.getDivisi(), getApplicationContext());
                        PreferenceUtils.saveNik(akun.getNik(), getApplicationContext());
                        goToHome();
                    } else {
                        Toast.makeText(Login.this, "Akun Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        } catch (Exception e){ }

        if (correct!=1){
            Toast.makeText(Login.this, "Akun Belum Terdaftar", Toast.LENGTH_SHORT).show();
        }
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

    private void goToHome(){
        Intent a = new Intent(Login.this, Home.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showKeluarDialog();
    }

}