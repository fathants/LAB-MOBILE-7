package com.example.tuprak5;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tuprak5.databinding.ActivityProfileBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    private SharedPrefsManager prefs;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = new SharedPrefsManager(this);

        binding.toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        String username = prefs.getUsername();
        binding.tvUsername.setText(username);
        binding.tvHandle.setText("@" + username.toLowerCase().replace(" ", ""));

        String postsJson = prefs.getPostsData();
        List<Post> postList = new ArrayList<>();
        if (!postsJson.equals("[]") && !postsJson.isEmpty()) {
            Type type = new TypeToken<List<Post>>(){}.getType();
            postList = gson.fromJson(postsJson, type);
        }

        int count = 0;
        for (Post p : postList) {
            if (p.getUsername().equals(username)) {
                count++;
            }
        }

        binding.tvPostCount.setText("Jumlah post: " + count);
    }
}
