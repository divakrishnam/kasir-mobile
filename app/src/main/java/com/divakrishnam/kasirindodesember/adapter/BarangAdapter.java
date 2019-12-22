package com.divakrishnam.kasirindodesember.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.divakrishnam.kasirindodesember.R;
import com.divakrishnam.kasirindodesember.model.Barang;

import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.BarangViewHolder>{

    private List<Barang> mList;

    public BarangAdapter(List<Barang> list){
        mList = list;
    }

    @NonNull
    @Override
    public BarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        BarangViewHolder mViewHolder = new BarangViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BarangViewHolder holder, int position) {
        holder.tvIdBarang.setText(" : "+mList.get(position).getBarangId());
        holder.tvNama.setText(" : "+mList.get(position).getBarangNama());
        holder.tvKategori.setText(" : "+mList.get(position).getBarangKategori());
        holder.tvStok.setText(" : "+mList.get(position).getBarangStok());
        holder.tvHarga.setText(" : "+mList.get(position).getBarangHarga());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class BarangViewHolder extends RecyclerView.ViewHolder {

        TextView tvIdBarang, tvNama, tvKategori, tvStok, tvHarga;

        public BarangViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIdBarang = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvKategori = itemView.findViewById(R.id.tv_kategori);
            tvStok = itemView.findViewById(R.id.tv_stok);
            tvHarga = itemView.findViewById(R.id.tv_harga);
        }
    }
}
