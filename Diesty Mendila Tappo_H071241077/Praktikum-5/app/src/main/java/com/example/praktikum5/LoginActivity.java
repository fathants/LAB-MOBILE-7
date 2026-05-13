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

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etNim, etPassword;
    private MaterialButton btnLogin;
    private MaterialTextView tvGoRegister;

    private SharedPreferences authPrefs;

    private static final String AUTH_PREF     = "auth_pref";
    private static final String SETTINGS_PREF = "settings_pref";
    private static final String KEY_DARK_MODE = "dark_mode";
    private static final String KEY_LOGGED_IN = "is_logged_in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settingsPrefs = getSharedPreferences(SETTINGS_PREF, MODE_PRIVATE);
        boolean isDark = settingsPrefs.getBoolean(KEY_DARK_MODE, false);
        AppCompatDelegate.setDefaultNightMode(
                isDark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        setContentView(R.layout.activity_login);

        authPrefs    = getSharedPreferences(AUTH_PREF, MODE_PRIVATE);
        etNim        = findViewById(R.id.etNim);
        etPassword   = findViewById(R.id.etPassword);
        btnLogin     = findViewById(R.id.btnLogin);
        tvGoRegister = findViewById(R.id.tvGoRegister);

        btnLogin.setOnClickListener(v -> handleLogin());

        tvGoRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
    }

    private void handleLogin() {
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

        String savedPass = authPrefs.getString("pass_" + nim, "");

        if (savedPass.isEmpty()) {
            Toast.makeText(this, "NIM tidak terdaftar!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!savedPass.equals(pass)) {
            etPassword.setError("Password salah");
            Toast.makeText(this, "Password salah!", Toast.LENGTH_SHORT).show();
            return;
        }

        authPrefs.edit()
                .putBoolean(KEY_LOGGED_IN, true)
                .putString("logged_nim", nim)
                .apply();

        Toast.makeText(this, "Login berhasil! Selamat datang, " + nim, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}