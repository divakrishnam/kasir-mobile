package com.divakrishnam.kasirindodesember.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.divakrishnam.kasirindodesember.R;
import com.divakrishnam.kasirindodesember.adapter.BarangAdapter;
import com.divakrishnam.kasirindodesember.handler.DBHandler;
import com.divakrishnam.kasirindodesember.model.Barang;
import com.divakrishnam.kasirindodesember.model.Kategori;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;

public class BarangActivity extends AppCompatActivity {

    private ProgressBar pbBarang;
    private TextView tvPesan;
    private FloatingActionButton fabTambahBarang;
    private RecyclerView rvBarang;

    private BarangAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DBHandler dbHandler;
    private Spinner spKategori;
    private String[] keys;

    private Button btnCari, btnRefresh;

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
                showCustomDialog();
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });
    }

    private void loadData() {
        List<Barang> barangs = dbHandler.loadBarangHandler();
        if (barangs != null){
            showMessage(false, "");
            mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvBarang.setLayoutManager(mLayoutManager);
            mAdapter = new BarangAdapter(barangs);
            rvBarang.setAdapter(mAdapter);
            rvBarang.setNestedScrollingEnabled(false);
        }else{
            showMessage(true, "Data tidak ada");
        }

        showLoading(false);
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Barang");
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_tambah_barang, null);
        builder.setView(dialogView);
        final EditText etId = dialogView.findViewById(R.id.et_id);
        final EditText etNama = dialogView.findViewById(R.id.et_nama);
        final EditText etStok = dialogView.findViewById(R.id.et_stok);
        final EditText etHarga = dialogView.findViewById(R.id.et_harga);
        spKategori = dialogView.findViewById(R.id.sp_kategori);
        if(dbHandler.loadKategoriHandler() != null){
            spinnerKategori();
        }
        builder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id = etId.getText().toString();
                String nama = etNama.getText().toString();
                String stok = etStok.getText().toString();
                String harga = etHarga.getText().toString();
                String kategori = keys[(int)spKategori.getSelectedItemId()];

                if (!id.isEmpty() && !nama.isEmpty() && !stok.isEmpty() && !harga.isEmpty()){
                    Barang barang = new Barang(id, nama, kategori, stok, harga);
                    dbHandler.addBarangHandler(barang);
                }
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void spinnerKategori() {
        int size = dbHandler.loadKategoriHandler().size();
        String[] values = new String[size];
        keys = new String[size];
        HashMap<String, String> map;
        int i = 0;
        map = new HashMap<>();
        for (Kategori kategori : dbHandler.loadKategoriHandler() ) {
            values[i] = kategori.getKategoriNama();
            keys[i] = kategori.getKategoriId();
            map.put(keys[i], values[i]);
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKategori.setAdapter(adapter);
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
}
