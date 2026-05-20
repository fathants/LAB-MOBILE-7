package com.example.praktikum5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import com.example.praktikum5.fragment.AddBookFragment;
import com.example.praktikum5.fragment.FavoritesFragment;
import com.example.praktikum5.fragment.HomeFragment;
import com.example.praktikum5.fragment.SettingsFragment;
import com.example.praktikum5.model.BookRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String SETTINGS_PREF = "settings_pref";
    private static final String AUTH_PREF     = "auth_pref";
    private static final String KEY_DARK_MODE = "dark_mode";
    private static final String KEY_LOGGED_IN = "is_logged_in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences settingsPrefs = getSharedPreferences(SETTINGS_PREF, MODE_PRIVATE);
        boolean isDark = settingsPrefs.getBoolean(KEY_DARK_MODE, false);
        AppCompatDelegate.setDefaultNightMode(
                isDark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        super.onCreate(savedInstanceState);

        SharedPreferences authPrefs = getSharedPreferences(AUTH_PREF, MODE_PRIVATE);
        if (!authPrefs.getBoolean(KEY_LOGGED_IN, false)) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        String nim = authPrefs.getString("logged_nim", "");
        BookRepository.getInstance().init(this, nim);

        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);

        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment;
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                fragment = new HomeFragment();
            } else if (id == R.id.nav_favorites) {
                fragment = new FavoritesFragment();
            } else if (id == R.id.nav_add) {
                fragment = new AddBookFragment();
            } else if (id == R.id.nav_settings) {
                fragment = new SettingsFragment();
            } else {
                return false;
            }
            loadFragment(fragment);
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}