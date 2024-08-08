package com.oci.presensi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oci.presensi.R;
import com.oci.presensi.model.ModelAkun;

import java.util.List;

public class AdapterDataAbsensi extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ModelAkun> itemList;


    public AdapterDataAbsensi(List<ModelAkun> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_list_absensi, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Penampung) holder).nama.setText(itemList.get(position).getNama());
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nama;

        public Penampung(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
