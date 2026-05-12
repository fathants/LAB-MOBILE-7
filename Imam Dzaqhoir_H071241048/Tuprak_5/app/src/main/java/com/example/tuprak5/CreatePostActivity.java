package com.example.tuprak5;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tuprak5.databinding.ActivityCreatePostBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreatePostActivity extends AppCompatActivity {
    private ActivityCreatePostBinding binding;
    private SharedPrefsManager prefs;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = new SharedPrefsManager(this);
        binding.tvUsername.setText(prefs.getUsername());

        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnPost.setEnabled(false);
        binding.btnPost.setAlpha(0.5f);

        binding.etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    binding.btnPost.setEnabled(false);
                    binding.btnPost.setAlpha(0.5f);
                } else {
                    binding.btnPost.setEnabled(true);
                    binding.btnPost.setAlpha(1.0f);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.btnPost.setOnClickListener(v -> {
            String content = binding.etContent.getText().toString().trim();
            if (!content.isEmpty()) {
                savePost(content);
                Toast.makeText(this, "Thread berhasil diposting", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void savePost(String content) {
        String postsJson = prefs.getPostsData();
        List<Post> postList;

        if (!postsJson.equals("[]") && !postsJson.isEmpty()) {
            Type type = new TypeToken<List<Post>>(){}.getType();
            postList = gson.fromJson(postsJson, type);
        } else {
            postList = new ArrayList<>();
        }

        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        Post newPost = new Post(prefs.getUsername(), content, currentTime);
        postList.add(newPost);

        String newJson = gson.toJson(postList);
        prefs.setPostsData(newJson);
    }
}
