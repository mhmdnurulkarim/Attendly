package com.oci.presensi;

import static com.oci.presensi.util.Utils.getDate;
import static com.oci.presensi.util.Utils.getDateTime;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.oci.presensi.databinding.ActivityAbsensiBinding;
import com.oci.presensi.helper.DataHelper;
import com.oci.presensi.model.ModelAbsensi;
import com.oci.presensi.util.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class AbsensiActivity extends AppCompatActivity {

    ActivityAbsensiBinding binding;
    DataHelper dbHelper;
    List<ModelAbsensi> listAbsensi;
    List<ModelAbsensi> listAbsensiToday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAbsensiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DataHelper(this);
        listAbsensi = dbHelper.getAllAbsensi();
        listAbsensiToday = new ArrayList<>();

        for (ModelAbsensi absensi : listAbsensi) {
            if (absensi.getTimestamp().substring(0, 10).equalsIgnoreCase(getDate()) &&
                    absensi.getId_user() == PreferenceUtils.getIdAkun(getApplicationContext())) {
                listAbsensiToday.add(absensi);
            }
        }

        binding.txtTanggal.setText(getDateTime());
        binding.txtIDKaryawan.setText("ID Karyawan : " + PreferenceUtils.getIdAkun(getApplicationContext()));
        binding.txtNamaKaryawan.setText("Nama Karyawan : " + PreferenceUtils.getNama(getApplicationContext()));
        binding.txtNikKaryawan.setText("NIK Karyawan : " + PreferenceUtils.getNik(getApplicationContext()));
        binding.txtDivisiKaryawan.setText("Divisi Karyawan : " + PreferenceUtils.getDivisi(getApplicationContext()));
        binding.txtRoleKaryawan.setText("Role Karyawan : " + PreferenceUtils.getIdRole(getApplicationContext()));

        binding.btnDatang.setOnClickListener(v -> {
            boolean sudahabsendatang = false;
            for (ModelAbsensi absensi : listAbsensiToday) {
                if (absensi.getKeterangan().equalsIgnoreCase("DATANG")) {
                    Toast.makeText(this, "Anda sudah melakukan absensi datang!", Toast.LENGTH_SHORT).show();
                    sudahabsendatang = true;
                    break;
                }
            }
            if (!sudahabsendatang) {
                showAbsensiDatangDialog();
            }
        });

        binding.btnPulang.setOnClickListener(v -> {
            boolean sudahabsenpulang = false;
            for (ModelAbsensi absensi : listAbsensiToday) {
                if (absensi.getKeterangan().equalsIgnoreCase("PULANG")) {
                    Toast.makeText(this, "Anda sudah melakukan absensi pulang!", Toast.LENGTH_SHORT).show();
                    sudahabsenpulang = true;
                    break;
                }
            }
            if (!sudahabsenpulang) {
                showAbsensiPulangDialog();
            }
        });
    }


    private void showAbsensiDatangDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ABSEN DATANG");
        builder.setMessage("Lakukan absensi datang?");
        builder.setPositiveButton("YA", (dialog, which) -> {
            int id = dbHelper.getLastIdAbsensi() + 1; // Assuming id should be incremented
            String now = getDateTime();
            int idAkun = PreferenceUtils.getIdAkun(getApplicationContext());
            dbHelper.saveAttendance(id, now, idAkun, "DATANG");
            setdialogdatang();
            dialog.dismiss();
        });
        builder.setNegativeButton("TIDAK", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void setdialogdatang() {
        new AlertDialog.Builder(this)
                .setMessage("BERHASIL MELAKUKAN ABSENSI DATANG")
                .setCancelable(true)
                .setPositiveButton("OK", (dialog, id) -> {
                    Intent a = new Intent(AbsensiActivity.this, HomeActivity.class);
                    startActivity(a);
                    finish();
                }).create().show();
    }

    private void showAbsensiPulangDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ABSEN PULANG");
        builder.setMessage("Lakukan absensi pulang?");
        builder.setPositiveButton("YA", (dialog, which) -> {
            int id = dbHelper.getLastIdAbsensi() + 1; // Assuming id should be incremented
            String now = getDateTime();
            int idAkun = PreferenceUtils.getIdAkun(getApplicationContext());
            dbHelper.saveAttendance(id, now, idAkun, "PULANG");
            setdialogpulang();
            dialog.dismiss();
        });
        builder.setNegativeButton("TIDAK", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void setdialogpulang() {
        new AlertDialog.Builder(this)
                .setMessage("BERHASIL MELAKUKAN ABSENSI PULANG")
                .setCancelable(true)
                .setPositiveButton("OK", (dialog, id) -> {
                    Intent a = new Intent(AbsensiActivity.this, HomeActivity.class);
                    startActivity(a);
                    finish();
                }).create().show();
    }

    private void showBatalAbsensiDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("BATAL ABSENSI");
        builder.setMessage("Apakah anda yakin batal melakukan absensi?");
        builder.setPositiveButton("YA", (dialog, which) -> {
            Intent a = new Intent(AbsensiActivity.this, HomeActivity.class);
            startActivity(a);
            finish();
        });
        builder.setNegativeButton("TIDAK", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        showBatalAbsensiDialog();
    }
}