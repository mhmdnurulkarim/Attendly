package com.sofia.presensi.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sofia.presensi.R;
import com.sofia.presensi.helper.DataHelper;
import com.sofia.presensi.model.ModelAbsensi;
import com.sofia.presensi.model.ModelAkun;

import java.util.List;

public class AdapterDataAbsensiHarian extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<ModelAbsensi> itemList;
    DataHelper dataHelper;


    public AdapterDataAbsensiHarian(List<ModelAbsensi> itemList, DataHelper dataHelper) {
        this.itemList = itemList;
        this.dataHelper = dataHelper;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_list_absensi_harian, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Penampung)holder).nama.setText(dataHelper.getNamaUser(itemList.get(position).getId_user()));
        ((Penampung)holder).ket.setText(itemList.get(position).getKeterangan());
        if (itemList.get(position).getKeterangan().equalsIgnoreCase("DATANG")){
            ((Penampung)holder).ket.setTextColor(Color.parseColor("#047E38"));
            ((Penampung)holder).tgl.setTextColor(Color.parseColor("#047E38"));
        } else {
            ((Penampung)holder).ket.setTextColor(Color.parseColor("#EE4446"));
            ((Penampung)holder).tgl.setTextColor(Color.parseColor("#EE4446"));
        }
        ((Penampung)holder).tgl.setText(itemList.get(position).getTimestamp());

    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nama, ket, tgl;
        public Penampung(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            ket = itemView.findViewById(R.id.ket);
            tgl = itemView.findViewById(R.id.tgl);
        }
        @Override
        public void onClick(View v) {

        }
    }
}
