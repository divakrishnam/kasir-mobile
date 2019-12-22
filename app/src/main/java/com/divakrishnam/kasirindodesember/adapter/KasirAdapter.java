package com.divakrishnam.kasirindodesember.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.divakrishnam.kasirindodesember.R;
import com.divakrishnam.kasirindodesember.model.Kasir;

import java.util.List;

public class KasirAdapter  extends RecyclerView.Adapter<KasirAdapter.KasirViewHolder>{

    private List<Kasir> mList;

    public KasirAdapter(List<Kasir> list){
        mList = list;
    }

    @NonNull
    @Override
    public KasirViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kasir, parent, false);
        KasirViewHolder mViewHolder = new KasirViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull KasirViewHolder holder, int position) {
        holder.tvId.setText(" : "+mList.get(position).getKasirId());
        holder.tvNama.setText(" : "+mList.get(position).getKasirNama());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class KasirViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvNama;

        public KasirViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
        }
    }
}
