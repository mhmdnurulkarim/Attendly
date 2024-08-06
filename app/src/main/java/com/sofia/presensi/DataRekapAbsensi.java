package com.sofia.presensi;

import static com.sofia.presensi.util.Utils.getPeriode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sofia.presensi.adapter.AdapterDataAbsensi;
import com.sofia.presensi.adapter.AdapterDataPegawai;
import com.sofia.presensi.databinding.DataRekapAbsensiBinding;
import com.sofia.presensi.helper.DataHelper;
import com.sofia.presensi.model.ModelAkun;
import com.sofia.presensi.util.RecyclerItemClickListener;

import java.util.Calendar;
import java.util.List;

public class DataRekapAbsensi extends AppCompatActivity {

    DataRekapAbsensiBinding binding;
    DataHelper dbHelper;
    List<ModelAkun> listPegawai;
    Calendar calendar;
    int date, month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.data_rekap_absensi);

        calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        dbHelper =  new DataHelper(this);
        listPegawai = dbHelper.getAllAkun();

        binding.textTitle.setText("DATA REKAP ABSENSI");
        binding.txtPeriode.setText("Periode " + getPeriode());

        if (listPegawai.size()>0) {
            AdapterDataAbsensi itemList = new AdapterDataAbsensi(listPegawai);
            binding.rvDataRekapAbsensi.setLayoutManager(new LinearLayoutManager(DataRekapAbsensi.this));
            binding.rvDataRekapAbsensi.setAdapter(itemList);
            binding.rvDataRekapAbsensi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvDataRekapAbsensi,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(DataRekapAbsensi.this, DataRekapAbsensiDetail.class);
                            intent.putExtra("idUser", listPegawai.get(position).getIdUser());
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
        } else {
            Toast.makeText(this, "anda belum memiliki data absensi", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(DataRekapAbsensi.this, Home.class);
        startActivity(a);
        finish();
    }
}