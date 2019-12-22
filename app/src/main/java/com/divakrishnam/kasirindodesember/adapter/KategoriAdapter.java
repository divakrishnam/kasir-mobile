package com.divakrishnam.kasirindodesember.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.divakrishnam.kasirindodesember.R;
import com.divakrishnam.kasirindodesember.model.Kategori;

import java.util.List;

public class KategoriAdapter  extends RecyclerView.Adapter<KategoriAdapter.KategoriViewHolder>{

    private List<Kategori> mList;

    public KategoriAdapter(List<Kategori> list){
        mList = list;
    }

    @NonNull
    @Override
    public KategoriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kategori, parent, false);
        KategoriViewHolder mViewHolder = new KategoriViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull KategoriViewHolder holder, int position) {
        holder.tvId.setText(" : "+mList.get(position).getKategoriId());
        holder.tvNama.setText(" : "+mList.get(position).getKategoriNama());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class KategoriViewHolder extends RecyclerView.ViewHolder {

        TextView tvId, tvNama;

        public KategoriViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
        }
    }
}
