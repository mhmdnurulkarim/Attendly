package com.oci.presensi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oci.presensi.DataRekapAbsensiDetailActivity;
import com.oci.presensi.R;
import com.oci.presensi.model.ModelAkun;

import java.util.List;

public class AdapterDataRekapAbsensi extends RecyclerView.Adapter<AdapterDataRekapAbsensi.Penampung> {

    private final List<ModelAkun> itemList;
    private final Context context;

    public AdapterDataRekapAbsensi(Context context, List<ModelAkun> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public Penampung onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_absensi, parent, false);
        return new Penampung(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Penampung holder, int position) {
        ModelAkun akun = itemList.get(position);
        holder.nama.setText(akun.getNama());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DataRekapAbsensiDetailActivity.class);
            intent.putExtra("idUser", akun.getIdUser());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    static class Penampung extends RecyclerView.ViewHolder {
        public TextView nama;

        public Penampung(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
        }
    }
}
