package com.divakrishnam.kasirindodesember.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.divakrishnam.kasirindodesember.R;
import com.divakrishnam.kasirindodesember.adapter.KasirAdapter;
import com.divakrishnam.kasirindodesember.handler.DBHandler;
import com.divakrishnam.kasirindodesember.model.Kasir;

import java.util.List;

public class KasirActivity extends AppCompatActivity {

    private ProgressBar pbKasir;
    private TextView tvPesan;
    private RecyclerView rvKasir;

    private KasirAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DBHandler dbHandler;

    private Button btnCari, btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasir);

        getSupportActionBar().setTitle("Kasir");

        pbKasir = findViewById(R.id.pb_kasir);
        tvPesan = findViewById(R.id.tv_pesan);
        rvKasir = findViewById(R.id.rv_kasir);
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
        List<Kasir> kasirs = dbHandler.loadKasirHandler();
        if (kasirs != null){
            showMessage(false, "");
            mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvKasir.setLayoutManager(mLayoutManager);
            mAdapter = new KasirAdapter(kasirs);
            rvKasir.setAdapter(mAdapter);
            rvKasir.setNestedScrollingEnabled(false);
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
            pbKasir.setVisibility(View.VISIBLE);
        } else {
            pbKasir.setVisibility(View.GONE);
        }
    }
}
