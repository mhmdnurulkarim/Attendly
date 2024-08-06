package com.sofia.presensi;

import static com.sofia.presensi.util.Utils.getDate;
import static com.sofia.presensi.util.Utils.getDateTime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.sofia.presensi.databinding.AbsensiBinding;
import com.sofia.presensi.helper.DataHelper;
import com.sofia.presensi.model.ModelAbsensi;
import com.sofia.presensi.util.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class Absensi extends AppCompatActivity {

    AbsensiBinding binding;
    DataHelper dbHelper;
    List<ModelAbsensi> listAbsensi;
    List<ModelAbsensi> listAbsensiToday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.absensi);

        dbHelper =  new DataHelper(this);
        listAbsensi = dbHelper.getAllAbsensi();
        listAbsensiToday = new ArrayList<ModelAbsensi>();

        if (listAbsensi.size()>0){
            for (int x=0; x<listAbsensi.size(); x++){
                if (listAbsensi.get(x).getTimestamp().substring(0,10).equalsIgnoreCase(getDate())){
                    if(listAbsensi.get(x).getId_user()==PreferenceUtils.getIdAkun(getApplicationContext())){
                        listAbsensiToday.add(listAbsensi.get(x));
                    }
                }
            }
        }

        binding.txtTanggal.setText(getDateTime());
        binding.txtIDKaryawan.setText("ID Karyawan : " + PreferenceUtils.getIdAkun(getApplicationContext()));
        binding.txtNamaKaryawan.setText("Nama Karyawan : " + PreferenceUtils.getNama(getApplicationContext()));
        binding.txtNikKaryawan.setText("NIK Karyawan : " + PreferenceUtils.getNik(getApplicationContext()));
        binding.txtDivisiKaryawan.setText("Divisi Karyawan : " + PreferenceUtils.getDivisi(getApplicationContext()));
        binding.txtRoleKaryawan.setText("Role Karyawan : " + PreferenceUtils.getIdRole(getApplicationContext()));

        binding.btnDatang.setOnClickListener(v->{
            boolean sudahabsendatang = false;
            if (listAbsensiToday.size()>0){
                for (int x=0; x<listAbsensiToday.size(); x++){
                    if (listAbsensiToday.get(x).getKeterangan().equalsIgnoreCase("DATANG")){
                        Toast.makeText(this, "anda sudah melakukan absensi datang!", Toast.LENGTH_SHORT).show();
                        sudahabsendatang = true;
                        break;
                    }
                }
                if (sudahabsendatang==false){
                    showAbsensiDatangDialog();
                }
            } else {
                showAbsensiDatangDialog();
            }
        });

        binding.btnPulang.setOnClickListener(v->{
            boolean sudahabsenpulang = false;
            if (listAbsensiToday.size()>0){
                for (int x=0; x<listAbsensiToday.size(); x++){
                    if (listAbsensiToday.get(x).getKeterangan().equalsIgnoreCase("PULANG")){
                        Toast.makeText(this, "anda sudah melakukan absensi pulang!", Toast.LENGTH_SHORT).show();
                        sudahabsenpulang = true;
                        break;
                    }
                }
                if (sudahabsenpulang==false){
                    showAbsensiPulangDialog();
                }
            } else {
                showAbsensiPulangDialog();
            }
        });
    }


    private void showAbsensiDatangDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ABSEN DATANG");
        builder.setMessage("Lakukan absensi datang ?");

        builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int id = dbHelper.getLastIdAbsensi();
                String now = getDateTime();
                int idAkun = PreferenceUtils.getIdAkun(getApplicationContext());
                dbHelper.addNewAbsensi(id, now, idAkun, "DATANG");
                //Toast.makeText(Absensi.this, "absen datang !", Toast.LENGTH_SHORT).show();
                setdialogdatang();
                dialog.dismiss();
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

    private void setdialogdatang(){
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setMessage("BERHASIL MELAKUKAN ABSENSI DATANG")
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent a = new Intent(Absensi.this, Home.class);
                        startActivity(a);
                        finish();
                    }
                });
        AlertDialog alert = builder2.create();
        alert.show();
    }

    private void showAbsensiPulangDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ABSEN PULANG");
        builder.setMessage("Lakukan absensi pulang ?");

        builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int id = dbHelper.getLastIdAbsensi();
                String now = getDateTime();
                int idAkun = PreferenceUtils.getIdAkun(getApplicationContext());
                dbHelper.addNewAbsensi(id, now, idAkun, "PULANG");
                //Toast.makeText(Absensi.this, "absen pulang!", Toast.LENGTH_SHORT).show();
                setdialogpulang();
                dialog.dismiss();
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

    private void setdialogpulang(){
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setMessage("BERHASIL MELAKUKAN ABSENSI PULANG")
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent a = new Intent(Absensi.this, Home.class);
                        startActivity(a);
                        finish();
                    }
                });
        AlertDialog alert = builder2.create();
        alert.show();

    }

    private void showBatalAbsensiDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("BATAL ABSENSI");
        builder.setMessage("Apakah anda yakin batal melakukan absensi ?");

        builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent a = new Intent(Absensi.this, Home.class);
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

    @Override
    public void onBackPressed() {
        showBatalAbsensiDialog();
    }

}