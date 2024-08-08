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

public class AdapterDataPegawai extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ModelAkun> itemList;


    public AdapterDataPegawai(List<ModelAkun> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_list_pegawai, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Penampung) holder).nama.setText(itemList.get(position).getNama());
        ((Penampung) holder).id.setText("ID : " + itemList.get(position).getIdUser());
        ((Penampung) holder).nik.setText("NIK : " + itemList.get(position).getNik());
        ((Penampung) holder).divisi.setText("Divisi : " + itemList.get(position).getDivisi());
        // set if dengan id role masing2
        // ((Penampung)holder).role.setText("Role : " + itemList.get(position).getId_role());
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nama, id, nik, divisi;

        public Penampung(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            id = itemView.findViewById(R.id.id);
            nik = itemView.findViewById(R.id.nik);
            divisi = itemView.findViewById(R.id.divisi);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
