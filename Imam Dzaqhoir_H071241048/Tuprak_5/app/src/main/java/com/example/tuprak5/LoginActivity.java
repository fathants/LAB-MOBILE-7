package com.example.tuprak5;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tuprak5.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private SharedPrefsManager prefs;
    private boolean isLoginMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = new SharedPrefsManager(this);

        binding.btnLogin.setOnClickListener(v -> handleAction());

        binding.btnRegister.setOnClickListener(v -> toggleMode());
    }

    private void toggleMode() {
        isLoginMode = !isLoginMode;
        
        android.view.animation.Animation anim = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.fade_in_slide_up);
        binding.tvTitle.startAnimation(anim);
        binding.tvSubtitle.startAnimation(anim);
        binding.btnLogin.startAnimation(anim);
        binding.btnRegister.startAnimation(anim);

        if (isLoginMode) {
            binding.tvTitle.setText("Threads");
            binding.tvSubtitle.setText("Masuk atau buat akun baru");
            binding.btnLogin.setText("Masuk");
            binding.btnRegister.setText("Belum punya akun? Daftar");
        } else {
            binding.tvTitle.setText("Daftar");
            binding.tvSubtitle.setText("Buat akun Threads baru");
            binding.btnLogin.setText("Daftar");
            binding.btnRegister.setText("Sudah punya akun? Masuk");
        }
    }

    private void handleAction() {
        String username = binding.etUsername.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Mohon isi semua field", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.btnLogin.setEnabled(false);
        binding.btnLogin.setText("Memproses...");

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (isLoginMode) {
                if (prefs.checkUser(username, password)) {
                    performLogin(username);
                } else {
                    Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                    resetButton();
                }
            } else {
                if (prefs.isUserExists(username)) {
                    Toast.makeText(this, "Username sudah digunakan", Toast.LENGTH_SHORT).show();
                    resetButton();
                } else {
                    prefs.saveUser(username, password);
                    Toast.makeText(this, "Registrasi berhasil! Silakan login", Toast.LENGTH_SHORT).show();
                    toggleMode();
                    resetButton();
                }
            }
        }, 800);
    }

    private void resetButton() {
        binding.btnLogin.setEnabled(true);
        binding.btnLogin.setText(isLoginMode ? "Masuk" : "Daftar");
    }

    private void performLogin(String username) {
        prefs.setLoggedIn(true);
        prefs.setUsername(username);
        Toast.makeText(this, "Selamat datang, " + username, Toast.LENGTH_SHORT).show();
        
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
