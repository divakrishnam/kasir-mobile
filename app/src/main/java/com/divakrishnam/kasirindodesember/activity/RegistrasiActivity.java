package com.divakrishnam.kasirindodesember.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.divakrishnam.kasirindodesember.R;
import com.divakrishnam.kasirindodesember.handler.DBHandler;
import com.divakrishnam.kasirindodesember.model.Kasir;

public class RegistrasiActivity extends AppCompatActivity {

    private EditText etIdKasir, etNama, etUsername, etPassword, etCPassword;
    private Button btnRegistrasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        etIdKasir = findViewById(R.id.et_idkasir);
        etNama = findViewById(R.id.et_nama);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etCPassword = findViewById(R.id.et_cpassword);
        btnRegistrasi = findViewById(R.id.btn_registrasi);

        btnRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idKasir = etIdKasir.getText().toString();
                String nama = etNama.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String cPassword = etCPassword.getText().toString();

                if (!idKasir.isEmpty() && !nama.isEmpty() && !username.isEmpty() && !password.isEmpty()){
                    if (password.equals(cPassword)){
                        registrasi(idKasir, nama, username, password);
                    }else{
                        Toast.makeText(getApplicationContext(), "Password harus sama", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Username dan Password harus diisi", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void registrasi(String idKasir, String nama, String username, String password) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        Kasir kasir= new Kasir(idKasir, nama, username, password);
        dbHandler.registrasiHandler(kasir);
        finish();
        Toast.makeText(getApplicationContext(), "Berhasil registrasi", Toast.LENGTH_LONG).show();
    }
}
