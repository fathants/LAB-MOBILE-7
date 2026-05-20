package com.example.praktikum5.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import com.example.praktikum5.LoginActivity;
import com.example.praktikum5.R;
import com.example.praktikum5.model.BookRepository;

public class SettingsFragment extends Fragment {

    private SharedPreferences settingsPrefs;
    private SharedPreferences authPrefs;

    private static final String SETTINGS_PREF = "settings_pref";
    private static final String AUTH_PREF     = "auth_pref";
    private static final String KEY_DARK_MODE = "dark_mode";
    private static final String KEY_LOGGED_IN = "is_logged_in";
    private static final String KEY_NIM       = "logged_nim";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        settingsPrefs = requireContext().getSharedPreferences(SETTINGS_PREF, 0);
        authPrefs     = requireContext().getSharedPreferences(AUTH_PREF, 0);

        TextView tvNim = view.findViewById(R.id.tvNimValue);
        String nim = authPrefs.getString(KEY_NIM, "-");
        tvNim.setText(nim);

        Switch switchDark = view.findViewById(R.id.switchDarkMode);
        boolean isDark = settingsPrefs.getBoolean(KEY_DARK_MODE, false);
        switchDark.setChecked(isDark);

        switchDark.setOnCheckedChangeListener((btn, isChecked) -> {
            settingsPrefs.edit().putBoolean(KEY_DARK_MODE, isChecked).apply();
            AppCompatDelegate.setDefaultNightMode(
                    isChecked
                            ? AppCompatDelegate.MODE_NIGHT_YES
                            : AppCompatDelegate.MODE_NIGHT_NO
            );
            requireActivity().recreate();
        });

        Button btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> showLogoutDialog());

        return view;
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Yakin ingin keluar dari akun?")
                .setPositiveButton("Logout", (dialog, which) -> doLogout())
                .setNegativeButton("Batal", null)
                .show();
    }

    private void doLogout() {
        BookRepository.getInstance().reset();

        authPrefs.edit()
                .putBoolean(KEY_LOGGED_IN, false)
                .remove(KEY_NIM)
                .apply();

        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}