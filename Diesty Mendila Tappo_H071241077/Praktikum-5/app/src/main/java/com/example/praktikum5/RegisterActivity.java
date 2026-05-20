package com.example.praktikum5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etNim, etPassword;
    private MaterialButton btnRegister;
    private MaterialTextView tvGoLogin;

    private SharedPreferences authPrefs;

    private static final String AUTH_PREF     = "auth_pref";
    private static final String SETTINGS_PREF = "settings_pref";
    private static final String KEY_DARK_MODE = "dark_mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settingsPrefs = getSharedPreferences(SETTINGS_PREF, MODE_PRIVATE);
        boolean isDark = settingsPrefs.getBoolean(KEY_DARK_MODE, false);
        AppCompatDelegate.setDefaultNightMode(
                isDark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        setContentView(R.layout.activity_register);

        authPrefs   = getSharedPreferences(AUTH_PREF, MODE_PRIVATE);
        etNim       = findViewById(R.id.etNim);
        etPassword  = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvGoLogin   = findViewById(R.id.tvGoLogin);

        btnRegister.setOnClickListener(v -> handleRegister());

        tvGoLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void handleRegister() {
        String nim  = etNim.getText()      != null ? etNim.getText().toString().trim() : "";
        String pass = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        if (nim.isEmpty()) {
            etNim.setError("NIM tidak boleh kosong");
            return;
        }
        if (pass.isEmpty()) {
            etPassword.setError("Password tidak boleh kosong");
            return;
        }
        if (pass.length() < 4) {
            etPassword.setError("Password minimal 4 karakter");
            return;
        }

        String existingPass = authPrefs.getString("pass_" + nim, "");
        if (!existingPass.isEmpty()) {
            Toast.makeText(this, "NIM sudah terdaftar!", Toast.LENGTH_SHORT).show();
            return;
        }

        authPrefs.edit()
                .putString("pass_" + nim, pass)
                .apply();

        Toast.makeText(this, "Registrasi berhasil! Silakan login.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}