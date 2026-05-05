package com.example.praktikummobile_1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private View layoutFeed, layoutStory;
    private ImageView ivProfileFeed, ivMainImageFeed, ivProfileStory, ivMainImageStory;
    private TextView tvUsernameFeed, tvCaptionFeed, tvStoryName, tvCaptionStory;
    private ImageView btnBackFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();
        displayData();
    }

    private void initViews() {
        layoutFeed = findViewById(R.id.layout_feed_detail);
        layoutStory = findViewById(R.id.layout_story_detail);
        ivProfileFeed = findViewById(R.id.ivProfileFeed);
        ivMainImageFeed = findViewById(R.id.ivMainImageFeed);
        tvUsernameFeed = findViewById(R.id.tvUsernameFeed);
        tvCaptionFeed = findViewById(R.id.tvCaptionFeed);
        btnBackFeed = findViewById(R.id.btnBackFeed);
        ivProfileStory = findViewById(R.id.ivProfileStory);
        ivMainImageStory = findViewById(R.id.ivMainImageStory);
        tvStoryName = findViewById(R.id.tvStoryName);
        tvCaptionStory = findViewById(R.id.tvCaptionStory);
    }

    private void displayData() {
        String title = getIntent().getStringExtra("title");
        String caption = getIntent().getStringExtra("caption");
        int imageRes = getIntent().getIntExtra("imageRes", 0);
        String imageUri = getIntent().getStringExtra("imageUri");

        if (title != null && title.contains("Post")) {
            layoutFeed.setVisibility(View.VISIBLE);
            layoutStory.setVisibility(View.GONE);
            tvUsernameFeed.setText("vina_sucitra024");
            tvCaptionFeed.setText(caption);
            if (imageRes != 0) {
                ivMainImageFeed.setImageResource(imageRes);
            } else if (imageUri != null) {
                ivMainImageFeed.setImageURI(android.net.Uri.parse(imageUri));
            }
            btnBackFeed.setOnClickListener(v -> finish());
        } else {
            layoutFeed.setVisibility(View.GONE);
            layoutStory.setVisibility(View.VISIBLE);
            tvStoryName.setText(getIntent().getStringExtra("title_highlight") != null ? 
                    getIntent().getStringExtra("title_highlight") : "Story");
            tvCaptionStory.setText(caption);
            if (imageRes != 0) {
                ivMainImageStory.setImageResource(imageRes);
            }
            layoutStory.setOnClickListener(v -> finish());
        }
    }
}
