package com.example.praktikum_2;

import com.example.praktikum_2.R;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetailFeedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_feed);

        RecyclerView rvDetail = findViewById(R.id.rvDetailFeed);
        rvDetail.setLayoutManager(new LinearLayoutManager(this));

        int position = getIntent().getIntExtra("POSITION", 0);

        HomeAdapter adapter = new HomeAdapter(DataRepository.userPosts, post -> {});
        rvDetail.setAdapter(adapter);
        rvDetail.scrollToPosition(position);
    }
}