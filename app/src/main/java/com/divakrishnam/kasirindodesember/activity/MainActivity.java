package com.divakrishnam.kasirindodesember.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.divakrishnam.kasirindodesember.R;
import com.divakrishnam.kasirindodesember.util.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
}
