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

public class AdapterDataKaryawan extends RecyclerView.Adapter<AdapterDataKaryawan.ViewHolder> {

    private final List<ModelAkun> itemList;

    public AdapterDataKaryawan(List<ModelAkun> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_karyawan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelAkun model = itemList.get(position);
        holder.bindData(model);
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nama;
        private final TextView id;
        private final TextView nik;
        private final TextView divisi;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            id = itemView.findViewById(R.id.id);
            nik = itemView.findViewById(R.id.nik);
            divisi = itemView.findViewById(R.id.divisi);
        }

        public void bindData(ModelAkun model) {
            nama.setText(model.getNama());
            id.setText("ID : " + model.getIdUser());
            nik.setText("NIK : " + model.getNik());
            divisi.setText("Divisi : " + model.getDivisi());
            // role.setText("Role : " + model.getId_role());
        }
    }
}