package com.divakrishnam.kasirindodesember.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.divakrishnam.kasirindodesember.R;
import com.divakrishnam.kasirindodesember.model.DetailTransaksi;

import java.util.List;

public class DetailTransaksiAdapter extends RecyclerView.Adapter<DetailTransaksiAdapter.DetailTransaksiViewHolder>{

    private List<DetailTransaksi> mList;

    public DetailTransaksiAdapter(List<DetailTransaksi> list){
        mList = list;
    }

    @NonNull
    @Override
    public DetailTransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_transaksi, parent, false);
        DetailTransaksiViewHolder mViewHolder = new DetailTransaksiViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailTransaksiViewHolder holder, int position) {

        DetailTransaksi detailTransaksi = mList.get(position);

        if(detailTransaksi.getDetailTransaksiTotalHarga() != null){
            String harga = String.valueOf(Integer.valueOf(detailTransaksi.getDetailTransaksiTotalHarga())/detailTransaksi.getDetailTransaksiJumlahBarang());

            holder.tvBarang.setText(detailTransaksi.getDetailTransaksiBarang());
            holder.tvJumlah.setText(String.valueOf(detailTransaksi.getDetailTransaksiJumlahBarang()));
            holder.tvHarga.setText(harga);
            holder.tvTotal.setText(detailTransaksi.getDetailTransaksiTotalHarga());
        }else{
            holder.tvBarang.setText(detailTransaksi.getDetailTransaksiBarang());
            holder.tvJumlah.setText("");
            holder.tvHarga.setText("");
            holder.tvTotal.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class DetailTransaksiViewHolder extends RecyclerView.ViewHolder {
        TextView tvBarang, tvJumlah, tvHarga, tvTotal;

        public DetailTransaksiViewHolder(@NonNull View itemView) {
            super(itemView);

            tvBarang = itemView.findViewById(R.id.tv_barang);
            tvJumlah = itemView.findViewById(R.id.tv_jumlah);
            tvHarga = itemView.findViewById(R.id.tv_harga);
            tvTotal = itemView.findViewById(R.id.tv_total);
        }
    }
}
