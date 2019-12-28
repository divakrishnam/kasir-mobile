package com.divakrishnam.kasirindodesember.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.divakrishnam.kasirindodesember.R;
import com.divakrishnam.kasirindodesember.adapter.DetailTransaksiAdapter;
import com.divakrishnam.kasirindodesember.dialog.DetailTransaksiDialog;
import com.divakrishnam.kasirindodesember.handler.DBHandler;
import com.divakrishnam.kasirindodesember.model.DetailTransaksi;
import com.divakrishnam.kasirindodesember.model.Transaksi;
import com.divakrishnam.kasirindodesember.util.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DetailTransaksiDialog.DetailTransaksiDialogListener, View.OnClickListener {

    private ProgressBar pbTransaksi;
    private TextView tvPesan;
    private FloatingActionButton fabTambah, fabSimpan;
    private RecyclerView rvTransaksi;

    private DetailTransaksiAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DBHandler dbHandler;

    List<DetailTransaksi> detailTransaksisSet;

    SharedPrefManager pref;

    TextView tvBon, tvKasir, tvTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        getSupportActionBar().setTitle("Kasir");

        pbTransaksi = findViewById(R.id.pb_transaksi);
        tvPesan = findViewById(R.id.tv_pesan);
        fabTambah = findViewById(R.id.fab_tambahtransaksi);
        fabSimpan = findViewById(R.id.fab_simpantransaksi);
        rvTransaksi = findViewById(R.id.rv_transaksi);

        tvBon = findViewById(R.id.tv_bon);
        tvKasir = findViewById(R.id.tv_kasir);
        tvTotal = findViewById(R.id.tv_total);

        pref = SharedPrefManager.getInstance(getApplicationContext());

        dbHandler = new DBHandler(this, null, null, 1);

        detailTransaksisSet = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvTransaksi.setLayoutManager(mLayoutManager);

        loadData();

        fabTambah.setOnClickListener(this);
        fabSimpan.setOnClickListener(this);
    }

    private void loadData() {
        showLoading(true);

        List<DetailTransaksi> detailTransaksisGet = pref.getDetailTransaksi();

        int total = 0;
        if (detailTransaksisGet != null){
            showMessage(false, "");
            mAdapter = new DetailTransaksiAdapter(detailTransaksisGet);
            rvTransaksi.setAdapter(mAdapter);
            for (int i = 0; i < detailTransaksisGet.size(); i++){
                total += Integer.valueOf(detailTransaksisGet.get(i).getDetailTransaksiTotalHarga());
            }
            tvTotal.setText(String.valueOf(total));
        }else{
            pref.resetDetailTransaksi();
            detailTransaksisGet = new ArrayList<>();
            detailTransaksisGet.add(new DetailTransaksi(0, "Tidak ada data", null, 0, null));        //showMessage(true, "Data tidak ada")
            mAdapter = new DetailTransaksiAdapter(detailTransaksisGet);
            rvTransaksi.setAdapter(mAdapter);
        }

        showLoading(false);
    }

    private void showDialogDetailTransaksi(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        DetailTransaksiDialog detailTransaksiDialog = DetailTransaksiDialog.newInstance("Tambah Detail Transaksi");
        detailTransaksiDialog.show(fragmentManager, "dialog_detail_transaksi");
    }

    private void simpanTransaksi(){
        List<DetailTransaksi> detailTransaksis = pref.getDetailTransaksi();
        if(detailTransaksis != null){
           for (int i = 0; i < detailTransaksis.size(); i++){
               dbHandler.addDetailTransaksiHandler(detailTransaksis.get(i));
               Log.d("lala", detailTransaksis.get(i).getDetailTransaksiBarang());
           }
           dbHandler.addTransaksiHandler(new Transaksi(dbHandler.setIdTransaksiHandler(), pref.getKasir().getKasirId(), "02-02-2019 18:00:00"));
           pref.resetDetailTransaksi();
           loadData();
        }else {

        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.barang){
            startActivity(new Intent(getApplicationContext(), BarangActivity.class));
        }else if (item.getItemId() == R.id.kategori){
            startActivity(new Intent(getApplicationContext(), KategoriActivity.class));
        }else if (item.getItemId() == R.id.kasir){
            startActivity(new Intent(getApplicationContext(), KasirActivity.class));
        }else if (item.getItemId() == R.id.transaksi){
            startActivity(new Intent(getApplicationContext(), TransaksiActivity.class));
        }else if (item.getItemId() == R.id.profil){
            startActivity(new Intent(getApplicationContext(), ProfilActivity.class));
        }else if (item.getItemId() == R.id.logout){
            SharedPrefManager.getInstance(getApplicationContext()).logout();
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        return true;
    }

    @Override
    public void onFinishDetailTransaksiDialog() {
        loadData();
    }

    @Override
    public void onClick(View view) {
        if (view == fabTambah){
            showDialogDetailTransaksi();
        }else if(view == fabSimpan){
            simpanTransaksi();
        }
    }
}
