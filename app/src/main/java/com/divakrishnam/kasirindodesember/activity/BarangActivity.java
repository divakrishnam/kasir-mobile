package com.divakrishnam.kasirindodesember.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.divakrishnam.kasirindodesember.R;
import com.divakrishnam.kasirindodesember.adapter.BarangAdapter;
import com.divakrishnam.kasirindodesember.dialog.BarangDialog;
import com.divakrishnam.kasirindodesember.handler.DBHandler;
import com.divakrishnam.kasirindodesember.model.Barang;
import com.divakrishnam.kasirindodesember.model.Kategori;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.Result;

import java.util.HashMap;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarangActivity extends AppCompatActivity implements BarangDialog.BarangDialogListener {

    private ProgressBar pbBarang;
    private TextView tvPesan;
    private FloatingActionButton fabTambahBarang;
    private RecyclerView rvBarang;

    private BarangAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DBHandler dbHandler;

    private Button btnCari, btnRefresh;

    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);

        getSupportActionBar().setTitle("Barang");

        pbBarang = findViewById(R.id.pb_barang);
        tvPesan = findViewById(R.id.tv_pesan);
        fabTambahBarang = findViewById(R.id.fab_tambahbarang);
        rvBarang = findViewById(R.id.rv_barang);
        btnCari = findViewById(R.id.btn_cari);
        btnRefresh = findViewById(R.id.btn_refresh);


        showLoading(true);

        dbHandler = new DBHandler(this, null, null, 1);

        loadData();

        fabTambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBarang();
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });
    }

    public void loadData() {
        List<Barang> barangs = dbHandler.loadBarangHandler();
        if (barangs != null){
            showMessage(false, "");
            mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvBarang.setLayoutManager(mLayoutManager);
            mAdapter = new BarangAdapter(barangs);
            rvBarang.setAdapter(mAdapter);
        }else{
            showMessage(true, "Data tidak ada");
        }

        showLoading(false);
    }

    private void showDialogBarang(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        BarangDialog barangDialog = BarangDialog.newInstance("Tambah Barang");
        barangDialog.show(fragmentManager, "dialog_barang");
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
            pbBarang.setVisibility(View.VISIBLE);
        } else {
            pbBarang.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onFinishBarangDialog() {
        loadData();
    }
}
