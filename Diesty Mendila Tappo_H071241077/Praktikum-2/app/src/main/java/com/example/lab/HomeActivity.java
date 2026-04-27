package com.example.lab;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity {
    private HomeFeedAdapter feedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DataStorage.initHomeFeedIfEmpty();
        DataStorage.initHighlightIfEmpty(); // agar story bar di home juga terisi

        // Setup RecyclerView
        RecyclerView rv = findViewById(R.id.rv_home_feed);
        rv.setLayoutManager(new LinearLayoutManager(this));
        feedAdapter = new HomeFeedAdapter(
                DataStorage.homeFeedList,
                DataStorage.homeFeedList,
                this
        );
        rv.setAdapter(feedAdapter);

        // Bottom nav
        findViewById(R.id.btn_nav_add_post).setOnClickListener(v ->
                startActivity(new Intent(this, AddPostActivity.class)));

        findViewById(R.id.btn_nav_profile).setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (feedAdapter != null) feedAdapter.notifyDataSetChanged();
    }
}