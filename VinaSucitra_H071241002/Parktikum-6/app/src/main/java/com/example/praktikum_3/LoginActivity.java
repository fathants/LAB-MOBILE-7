package com.example.praktikum_3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword;
    private MaterialButton btnLogin;
    private TextView tvGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoToRegister = findViewById(R.id.tvGoToRegister);

        btnLogin.setOnClickListener(v -> {
            String inputName = etUsername.getText().toString().trim();
            String inputPassword = etPassword.getText().toString().trim();

            if (inputName.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Please enter name and password", Toast.LENGTH_SHORT).show();
            } else {
                // Mengambil data dari SharedPreferences
                SharedPreferences sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                String savedName = sharedPref.getString("saved_name", null);
                String savedPassword = sharedPref.getString("saved_password", null);

                // Validasi: Cek apakah user sudah register dan apakah input cocok
                if (savedName != null && savedName.equalsIgnoreCase(inputName) && savedPassword.equals(inputPassword)) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Invalid Name or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
