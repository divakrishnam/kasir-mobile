package com.divakrishnam.kasirindodesember.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.divakrishnam.kasirindodesember.R;
import com.divakrishnam.kasirindodesember.model.Transaksi;

import java.util.List;

public class TransaksiAdapter  extends RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder>{

    private List<Transaksi> mList;

    public TransaksiAdapter(List<Transaksi> list){
        mList = list;
    }

    @NonNull
    @Override
    public TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kategori, parent, false);
        TransaksiViewHolder mViewHolder = new TransaksiViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiViewHolder holder, int position) {
        holder.tvId.setText(" : "+mList.get(position).getTransaksiId());
        holder.tvKasir.setText(" : "+mList.get(position).getTransaksiKasir());
        holder.tvWaktu.setText(" : "+mList.get(position).getTransaksiWaktu());
        holder.tvTotal.setText(" : "+mList.get(position).getTransaksiTotalBelanja());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TransaksiViewHolder extends RecyclerView.ViewHolder {

        TextView tvId, tvKasir, tvWaktu, tvTotal;

        public TransaksiViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvKasir = itemView.findViewById(R.id.tv_kasir);
            tvWaktu = itemView.findViewById(R.id.tv_waktu);
            tvTotal = itemView.findViewById(R.id.tv_total);
        }
    }
}
