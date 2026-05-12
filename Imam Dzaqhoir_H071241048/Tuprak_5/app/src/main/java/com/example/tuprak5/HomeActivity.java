package com.example.tuprak5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tuprak5.databinding.ActivityHomeBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private SharedPrefsManager prefs;
    private PostAdapter adapter;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = new SharedPrefsManager(this);
        prefs.initDummyData();

        adapter = new PostAdapter(new ArrayList<>());
        binding.rvPosts.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPosts.setAdapter(adapter);

        binding.btnCreate.setOnClickListener(v -> 
            startActivity(new Intent(this, CreatePostActivity.class)));
        
        binding.btnProfile.setOnClickListener(v -> 
            startActivity(new Intent(this, ProfileActivity.class)));
        
        binding.btnSettings.setOnClickListener(v -> 
            startActivity(new Intent(this, SettingsActivity.class)));
        
        binding.btnHome.setOnClickListener(v -> 
            binding.rvPosts.smoothScrollToPosition(0));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPosts();
    }

    private void loadPosts() {
        String postsJson = prefs.getPostsData();
        List<Post> postList = new ArrayList<>();
        
        if (postsJson != null && !postsJson.equals("[]") && !postsJson.isEmpty()) {
            Type type = new TypeToken<List<Post>>(){}.getType();
            postList = gson.fromJson(postsJson, type);
            Collections.reverse(postList); // Newest first
        }

        if (postList.isEmpty()) {
            binding.emptyState.setVisibility(View.VISIBLE);
            binding.rvPosts.setVisibility(View.GONE);
        } else {
            binding.emptyState.setVisibility(View.GONE);
            binding.rvPosts.setVisibility(View.VISIBLE);
            adapter.setPosts(postList);
        }
    }
}
