package com.oci.presensi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oci.presensi.R;
import com.oci.presensi.helper.DataHelper;
import com.oci.presensi.model.ModelAbsensi;

import java.util.List;

public class AdapterDataAbsensiHarian extends RecyclerView.Adapter<AdapterDataAbsensiHarian.ViewHolder> {

    private final List<ModelAbsensi> itemList;
    private final DataHelper dataHelper;

    public AdapterDataAbsensiHarian(List<ModelAbsensi> itemList, DataHelper dataHelper) {
        this.itemList = itemList;
        this.dataHelper = dataHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_absensi_harian, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelAbsensi absensi = itemList.get(position);

        holder.nama.setText(dataHelper.getNamaUser(absensi.getId_user()));
        holder.ket.setText(absensi.getKeterangan());
        holder.tgl.setText(absensi.getTimestamp());

        int color = absensi.getKeterangan().equalsIgnoreCase("DATANG") ?
                holder.itemView.getContext().getColor(R.color.greenText) :
                holder.itemView.getContext().getColor(R.color.redText);

        holder.ket.setTextColor(color);
        holder.tgl.setTextColor(color);
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nama;
        private final TextView ket;
        private final TextView tgl;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            ket = itemView.findViewById(R.id.ket);
            tgl = itemView.findViewById(R.id.tgl);
        }
    }
}