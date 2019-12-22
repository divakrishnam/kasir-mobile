package com.divakrishnam.kasirindodesember.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.divakrishnam.kasirindodesember.R;
import com.divakrishnam.kasirindodesember.adapter.TransaksiAdapter;
import com.divakrishnam.kasirindodesember.handler.DBHandler;
import com.divakrishnam.kasirindodesember.model.Transaksi;

import java.util.List;

public class TransaksiActivity extends AppCompatActivity {

    private ProgressBar pbTransaksi;
    private TextView tvPesan;
    private RecyclerView rvTransaksi;

    private TransaksiAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DBHandler dbHandler;

    private Button btnCari, btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        getSupportActionBar().setTitle("Transaksi");

        pbTransaksi = findViewById(R.id.pb_transaksi);
        tvPesan = findViewById(R.id.tv_pesan);
        rvTransaksi = findViewById(R.id.rv_transaksi);
        btnCari = findViewById(R.id.btn_cari);
        btnRefresh = findViewById(R.id.btn_refresh);

        dbHandler = new DBHandler(this, null, null, 1);

        loadData();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });
    }

    private void loadData() {
        showLoading(true);
        List<Transaksi> transaksis = dbHandler.loadTransaksiHandler();
        if (transaksis != null){
            showMessage(false, "");
            mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvTransaksi.setLayoutManager(mLayoutManager);
            mAdapter = new TransaksiAdapter(transaksis);
            rvTransaksi.setAdapter(mAdapter);
            rvTransaksi.setNestedScrollingEnabled(false);
        }else{
            showMessage(true, "Data tidak ada");
        }

        showLoading(false);
    }

    private void showMessage(Boolean state, String message){
        if (state) {
            tvPesan.setText(message);
            tvPesan.setVisibility(View.VISIBLE);
        } else {
            tvPesan.setVisibility(View.GONE);
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            pbTransaksi.setVisibility(View.VISIBLE);
        } else {
            pbTransaksi.setVisibility(View.GONE);
        }
    }
}
