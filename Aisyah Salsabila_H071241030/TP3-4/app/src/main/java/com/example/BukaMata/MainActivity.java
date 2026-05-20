package com.example.BukaMata;

import android.content.res.ColorStateList;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) loadFragment(new HomeFragment());

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        int[][] states = new int[][]{ new int[]{android.R.attr.state_checked}, new int[]{} };
        int[] colors = new int[]{
                ContextCompat.getColor(this, R.color.blue_primary),
                ContextCompat.getColor(this, R.color.text_secondary)
        };
        ColorStateList colorList = new ColorStateList(states, colors);
        bottomNav.setItemIconTintList(colorList);
        bottomNav.setItemTextColor(colorList);

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selected = null;
            int id = item.getItemId();
            if (id == R.id.nav_home) selected = new HomeFragment();
            else if (id == R.id.nav_favorites) selected = new FavoritesFragment();
            else if (id == R.id.nav_add) selected = new AddBookFragment();
            return loadFragment(selected);
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;
    }
}