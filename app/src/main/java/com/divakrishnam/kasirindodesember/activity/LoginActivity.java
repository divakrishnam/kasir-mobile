package com.divakrishnam.kasirindodesember.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.divakrishnam.kasirindodesember.R;
import com.divakrishnam.kasirindodesember.handler.DBHandler;
import com.divakrishnam.kasirindodesember.model.Kasir;
import com.divakrishnam.kasirindodesember.util.SharedPrefManager;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegistrasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegistrasi = findViewById(R.id.btn_registrasi);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (!username.isEmpty() && !password.isEmpty()){
                    login(username, password);
                }else{
                    Toast.makeText(getApplicationContext(), "Username dan Password harus diisi", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegistrasiActivity.class));
            }
        });
    }

    public void login(String username, String password) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        Kasir kasir = dbHandler.loginHandler(username, password);
        if (kasir!=null){
            finish();
            SharedPrefManager.getInstance(getApplicationContext()).kasirLogin(kasir);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }else{
            Toast.makeText(getApplicationContext(), "Username dan Password salah", Toast.LENGTH_LONG).show();
        }
    }
}
