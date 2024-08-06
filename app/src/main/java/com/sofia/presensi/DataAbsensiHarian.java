package com.sofia.presensi;

import static com.sofia.presensi.util.Utils.getDate;
import static com.sofia.presensi.util.Utils.getDateTime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.sofia.presensi.adapter.AdapterDataAbsensiHarian;
import com.sofia.presensi.databinding.DataAbsensiHarianBinding;
import com.sofia.presensi.helper.DataHelper;
import com.sofia.presensi.model.ModelAbsensi;
import com.sofia.presensi.util.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class DataAbsensiHarian extends AppCompatActivity {

    DataAbsensiHarianBinding binding;
    DataHelper dbHelper;
    List<ModelAbsensi> listAbsensi;
    List<ModelAbsensi> listAbsensiToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.data_absensi_harian);

        dbHelper =  new DataHelper(this);
        listAbsensi = dbHelper.getAllAbsensi();

        listAbsensiToday = new ArrayList<ModelAbsensi>();

        if (PreferenceUtils.getIdRole(getApplicationContext())==2){
            // semua karyawan
            for (int x=0; x<listAbsensi.size(); x++){
                if (listAbsensi.get(x).getTimestamp().substring(0,10).equalsIgnoreCase(getDate())){
                    listAbsensiToday.add(listAbsensi.get(x));
                }
            }
            if (listAbsensiToday.size()>0) {
                AdapterDataAbsensiHarian itemList = new AdapterDataAbsensiHarian(listAbsensiToday, dbHelper);
                binding.rvDataAbsensiHarian.setLayoutManager(new LinearLayoutManager(DataAbsensiHarian.this));
                binding.rvDataAbsensiHarian.setAdapter(itemList);
            } else {
                Toast.makeText(this, "anda belum memiliki data absensi harian", Toast.LENGTH_SHORT).show();
            }
        } else {
            for (int x=0; x<listAbsensi.size(); x++){
                if (listAbsensi.get(x).getTimestamp().substring(0,10).equalsIgnoreCase(getDate())){
                    if(listAbsensi.get(x).getId_user()==PreferenceUtils.getIdAkun(getApplicationContext())){
                        listAbsensiToday.add(listAbsensi.get(x));
                    }
                }
            }
            if (listAbsensiToday.size()>0) {
                AdapterDataAbsensiHarian itemList = new AdapterDataAbsensiHarian(listAbsensiToday, dbHelper);
                binding.rvDataAbsensiHarian.setLayoutManager(new LinearLayoutManager(DataAbsensiHarian.this));
                binding.rvDataAbsensiHarian.setAdapter(itemList);
            } else {
                Toast.makeText(this, "anda belum memiliki data absensi harian", Toast.LENGTH_SHORT).show();
            }
        }


        binding.txtTanggal.setText(getDateTime());



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(DataAbsensiHarian.this, Home.class);
        startActivity(a);
        finish();
    }
}