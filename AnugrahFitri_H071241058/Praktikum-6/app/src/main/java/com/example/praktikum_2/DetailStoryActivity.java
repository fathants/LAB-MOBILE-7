package com.example.praktikum_2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;

public class DetailStoryActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ProgressBar progressBar;
    private View touchOverlay;
    private TextView tvStoryTitle;
    private final Handler handler = new Handler();
    private int currentPage = 0;
    private List<Integer> storyImages;
    private List<String> storyTitles;

    private final Runnable autoAdvanceRunnable = new Runnable() {
        @Override
        public void run() {
            if (currentPage < storyImages.size() - 1) {
                currentPage++;
                viewPager.setCurrentItem(currentPage, false);
                updateUI();
                handler.postDelayed(this, 5000);
            } else {
                finish();
            }
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);

        viewPager = findViewById(R.id.viewPagerStory);
        progressBar = findViewById(R.id.storyProgressBar);
        touchOverlay = findViewById(R.id.touchOverlay);
        tvStoryTitle = findViewById(R.id.tvStoryTitle);
        
        viewPager.setUserInputEnabled(false);

        storyImages = new ArrayList<>();
        storyImages.add(R.drawable.anime);
        storyImages.add(R.drawable.coding);
        storyImages.add(R.drawable.game);
        storyImages.add(R.drawable.travel);
        storyImages.add(R.drawable.food);
        storyImages.add(R.drawable.movie);
        storyImages.add(R.drawable.music);

        storyTitles = new ArrayList<>();
        storyTitles.add("anime");
        storyTitles.add("coding");
        storyTitles.add("game");
        storyTitles.add("travel");
        storyTitles.add("food");
        storyTitles.add("movie");
        storyTitles.add("music");

        StoryAdapter adapter = new StoryAdapter(storyImages);
        viewPager.setAdapter(adapter);

        String currentTitle = getIntent().getStringExtra("STORY_TITLE");
        if (currentTitle != null) {
            currentPage = storyTitles.indexOf(currentTitle);
            if (currentPage == -1) currentPage = 0;
        } else {
            currentPage = 0;
        }

        viewPager.setCurrentItem(currentPage, false);
        updateUI();

        touchOverlay.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                float x = event.getX();
                int width = v.getWidth();
                if (x < width / 3.0) {
                    if (currentPage > 0) {
                        currentPage--;
                        viewPager.setCurrentItem(currentPage, false);
                        updateUI();
                        resetAutoAdvance();
                    }
                } else {
                    if (currentPage < storyImages.size() - 1) {
                        currentPage++;
                        viewPager.setCurrentItem(currentPage, false);
                        updateUI();
                        resetAutoAdvance();
                    } else {
                        finish();
                    }
                }
            }
            return true;
        });

        startAutoAdvance();
    }

    private void updateUI() {
        if (progressBar != null) {
            progressBar.setMax(storyImages.size());
            progressBar.setProgress(currentPage + 1);
        }
        if (tvStoryTitle != null && currentPage < storyTitles.size()) {
            tvStoryTitle.setText(storyTitles.get(currentPage));
        }
    }

    private void startAutoAdvance() {
        handler.removeCallbacks(autoAdvanceRunnable);
        handler.postDelayed(autoAdvanceRunnable, 5000);
    }

    private void resetAutoAdvance() {
        startAutoAdvance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
