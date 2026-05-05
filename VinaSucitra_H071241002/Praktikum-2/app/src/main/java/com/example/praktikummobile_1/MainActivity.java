package com.example.praktikummobile_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praktikummobile_1.adapters.PostAdapter;
import com.example.praktikummobile_1.adapters.StoryAdapter;
import com.example.praktikummobile_1.models.Post;
import com.example.praktikummobile_1.models.Story;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvPosts, rvStories;
    private PostAdapter postAdapter;
    private StoryAdapter storyAdapter;
    private List<Post> postList;
    private List<Story> storyList;
    private ImageView btnGoToProfile, btnGoToPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvStories = findViewById(R.id.rv_stories);
        rvStories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        storyList = new ArrayList<>();
        storyList.add(new Story(R.drawable.ina, "Cerita Anda"));
        storyList.add(new Story(R.drawable.vrda, "vrda.yuandani_"));
        storyList.add(new Story(R.drawable.trio, "arcipp"));
        storyList.add(new Story(R.drawable.vna, "vnaspam_"));
        storyList.add(new Story(R.drawable.tree, "rasti___"));
        storyList.add(new Story(R.drawable.duniaku, "ariff"));
        storyList.add(new Story(R.drawable.ucup, "yusufsetiawna"));

        storyAdapter = new StoryAdapter(storyList, true, story -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("title", "Story");
            intent.putExtra("imageRes", story.getStoryImage());
            startActivity(intent);
        });
        rvStories.setAdapter(storyAdapter);

        rvPosts = findViewById(R.id.rv_posts);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        postList = new ArrayList<>();
        postList.add(new Post(R.drawable.user_home_1, "ahmad_syah", R.drawable.home_feed_kopi, "Ngopi pagi dulu..."));
        postList.add(new Post(R.drawable.user_home_2, "sari_puspita", R.drawable.home_feed_pantai, "Rindu liburan 🌊"));
        postList.add(new Post(R.drawable.user_home_3, "budi_setiawan", R.drawable.home_feed_gunung, "Puncak yang indah!"));
        postList.add(new Post(R.drawable.user_home_4, "cat_lover", R.drawable.home_feed_kucing, "Meow! 🐈"));
        postList.add(new Post(R.drawable.user_home_5, "kebun_bunga", R.drawable.home_feed_bunga, "Mekar di musim semi."));
        postList.add(new Post(R.drawable.user_home_6, "sunset_hunter", R.drawable.home_feed_senja, "Warna langit sore ini."));
        postList.add(new Post(R.drawable.user_home_7, "foodie", R.drawable.home_feed_makanan, "Enak sekali! 🍔"));
        postList.add(new Post(R.drawable.user_home_8, "nature_fan", R.drawable.home_feed_hutan, "Sejuknya hutan pinus."));
        postList.add(new Post(R.drawable.user_home_9, "sky_high", R.drawable.home_feed_langit, "Awan yang cantik."));
        postList.add(new Post(R.drawable.user_home_10, "city_light", R.drawable.home_feed_kota, "Lampu kota di malam hari."));

        postAdapter = new PostAdapter(postList, this);
        rvPosts.setAdapter(postAdapter);

        btnGoToProfile = findViewById(R.id.btnGoToProfile);
        btnGoToProfile.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        });

        btnGoToPost = findViewById(R.id.btnGoToPost);
        btnGoToPost.setOnClickListener(v -> {
            Intent intent = new Intent(this, PostActivity.class);
            startActivity(intent);
        });
    }
}
