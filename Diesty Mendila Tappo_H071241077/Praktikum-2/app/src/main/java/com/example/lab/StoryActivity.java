package com.example.lab;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);

        int imageResId    = getIntent().getIntExtra("image", R.drawable.hl1);
        int profileResId  = getIntent().getIntExtra("profileResId", 0);
        String username   = getIntent().getStringExtra("username");

        ImageView imgStory = findViewById(R.id.img_detail_story);
        if (imgStory != null) {
            imgStory.setImageResource(imageResId);
        }

        ImageView ivProfile = findViewById(R.id.iv_story_profile);
        if (ivProfile != null && profileResId != 0) {
            ivProfile.setImageResource(profileResId);
        }

        TextView tvUsername = findViewById(R.id.tv_story_username);
        if (tvUsername != null && username != null) {
            tvUsername.setText(username);
        }

        // Tombol close
        findViewById(R.id.btn_close_story).setOnClickListener(v -> finish());
    }
}