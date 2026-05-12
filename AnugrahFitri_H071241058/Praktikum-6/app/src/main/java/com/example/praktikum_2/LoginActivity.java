package com.example.praktikum_2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences themePrefs = getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE);
        boolean isDarkMode = themePrefs.getBoolean("isDarkMode", true);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoToRegister = findViewById(R.id.tvGoToRegister);

        SharedPreferences userPrefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        if (!userPrefs.contains("ira.fitri4343_password")) {
            SharedPreferences.Editor editor = userPrefs.edit();
            editor.putString("ira.fitri4343_fullName", "Anugrah Fitri Novanda");
            editor.putString("ira.fitri4343_password", "ira123");
            editor.putString("ira.fitri4343_bio", "Universitas Hasanuddin\nSistem Informasi 2024");
            editor.apply();
        }

        etUsername.setText("ira.fitri4343");
        etPassword.setText("ira123");

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
            } else {
                String registeredPass = userPrefs.getString(username + "_password", null);

                if (registeredPass != null && password.equals(registeredPass)) {
                    userPrefs.edit()
                            .putBoolean("isLoggedIn", true)
                            .putString("currentUser", username)
                            .apply();
                    
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Username atau Password salah", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}