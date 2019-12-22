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
import com.divakrishnam.kasirindodesember.adapter.KategoriAdapter;
import com.divakrishnam.kasirindodesember.handler.DBHandler;
import com.divakrishnam.kasirindodesember.model.Kategori;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class KategoriActivity extends AppCompatActivity {

    private ProgressBar pbKategori;
    private TextView tvPesan;
    private FloatingActionButton fabTambah;
    private RecyclerView rvKategori;

    private KategoriAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DBHandler dbHandler;

    private Button btnCari, btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        getSupportActionBar().setTitle("Kategori");

        pbKategori = findViewById(R.id.pb_kategori);
        tvPesan = findViewById(R.id.tv_pesan);
        fabTambah = findViewById(R.id.fab_tambahkategori);
        rvKategori = findViewById(R.id.rv_kategori);
        btnCari = findViewById(R.id.btn_cari);
        btnRefresh = findViewById(R.id.btn_refresh);

        dbHandler = new DBHandler(this, null, null, 1);

        loadData();

        fabTambah.setOnClickListener(new View.OnClickListener() {
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
        showLoading(true);
        List<Kategori> kategoris = dbHandler.loadKategoriHandler();
        if (kategoris != null){
            showMessage(false, "");
            mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvKategori.setLayoutManager(mLayoutManager);
            mAdapter = new KategoriAdapter(kategoris);
            rvKategori.setAdapter(mAdapter);
            rvKategori.setNestedScrollingEnabled(false);
        }else{
            showMessage(true, "Data tidak ada");
        }

        showLoading(false);
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Barang");
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_tambah_kategori, null);
        builder.setView(dialogView);
        final EditText etId = dialogView.findViewById(R.id.et_id);
        final EditText etNama = dialogView.findViewById(R.id.et_nama);
        builder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id = etId.getText().toString();
                String nama = etNama.getText().toString();

                if (!id.isEmpty() && !nama.isEmpty()){
                    Kategori kategori = new Kategori(id, nama);
                    dbHandler.addKategoriHandler(kategori);
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
            pbKategori.setVisibility(View.VISIBLE);
        } else {
            pbKategori.setVisibility(View.GONE);
        }
    }
}
