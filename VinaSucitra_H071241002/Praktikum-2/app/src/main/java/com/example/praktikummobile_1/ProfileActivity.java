package com.example.praktikummobile_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.praktikummobile_1.adapters.ProfileFeedAdapter;
import com.example.praktikummobile_1.adapters.StoryAdapter;
import com.example.praktikummobile_1.models.Post;
import com.example.praktikummobile_1.models.Story;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName, tvUsername, tvBio, tvBio2, tvPostCount, tvFollowersCount, tvFriendsCount;
    private ImageView ivProfile, btnGoToHome, btnGoToPost, btnBack;
    private Button btnMengikuti, btnKirimPesan, btnTambahTeman;
    private RecyclerView rvHighlights, rvProfilePosts;
    private ProfileFeedAdapter feedAdapter;
    private List<Story> highlights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initViews();
        setupHighlights();
        setupProfileFeed();
        setupNavigation();
    }

    private void initViews() {
        tvName = findViewById(R.id.tvName);
        tvUsername = findViewById(R.id.tvUsername);
        tvBio = findViewById(R.id.tvBio);
        tvBio2 = findViewById(R.id.tvBio2);
        tvPostCount = findViewById(R.id.tvPostCount);
        tvFollowersCount = findViewById(R.id.tvFollowersCount);
        tvFriendsCount = findViewById(R.id.tvFriendsCount);
        ivProfile = findViewById(R.id.ivProfile);
        btnBack = findViewById(R.id.btnBack);
        btnMengikuti = findViewById(R.id.btnMengikuti);
        btnKirimPesan = findViewById(R.id.btnKirimPesan);
        btnTambahTeman = findViewById(R.id.btnTambahTeman);
        rvHighlights = findViewById(R.id.rv_highlights);
        rvProfilePosts = findViewById(R.id.rv_profile_posts);
        btnGoToHome = findViewById(R.id.btnGoToHome);
        btnGoToPost = findViewById(R.id.btnGoToPost);
        ivProfile.setImageResource(R.drawable.zar);
    }

    private void setupHighlights() {
        highlights = new ArrayList<>();
        highlights.add(new Story(R.drawable.ina, "me"));
        highlights.add(new Story(R.drawable.idda, "Cousin"));
        highlights.add(new Story(R.drawable.cintaku, "love"));
        highlights.add(new Story(R.drawable.duniaku, "World"));
        highlights.add(new Story(R.drawable.zar, "Zarah"));
        highlights.add(new Story(R.drawable.alya, "alya"));
        highlights.add(new Story(R.drawable.ucup, "ucup"));

        StoryAdapter storyAdapter = new StoryAdapter(highlights, false, story -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("title", "Story Highlight");
            intent.putExtra("title_highlight", story.getTitle());
            intent.putExtra("caption", "Melihat highlight: " + story.getTitle());
            intent.putExtra("imageRes", story.getStoryImage());
            startActivity(intent);
        });
        rvHighlights.setAdapter(storyAdapter);
    }

    private void setupProfileFeed() {
        if (Post.profilePosts.isEmpty()) {
            Post.profilePosts.add(new Post(R.drawable.vina, "vina_sucitra024", R.drawable.duniaku, "Sehat selalu my World..."));
            Post.profilePosts.add(new Post(R.drawable.vina, "vina_sucitra024", R.drawable.ucup, "yang selalu ada dan mau di repotkan "));
            Post.profilePosts.add(new Post(R.drawable.vina, "vina_sucitra024", R.drawable.idda, "Saudara tercinta kuuu"));
            Post.profilePosts.add(new Post(R.drawable.vina, "vina_sucitra024", R.drawable.zar, "Manusia nyebelin tapi kusayang"));
            Post.profilePosts.add(new Post(R.drawable.vina, "vina_sucitra024", R.drawable.alya, "Anak baikkk"));
        }

        updatePostCount();

        feedAdapter = new ProfileFeedAdapter(Post.profilePosts, post -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("title", "Detail Post");
            intent.putExtra("caption", post.getCaption());
            if (post.getPostImage() != 0) {
                intent.putExtra("imageRes", post.getPostImage());
            } else {
                intent.putExtra("imageUri", post.getPostImageUri());
            }
            startActivity(intent);
        });
        rvProfilePosts.setAdapter(feedAdapter);
    }

    private void updatePostCount() {
        if (tvPostCount != null) {
            tvPostCount.setText(String.valueOf(Post.profilePosts.size()));
        }
    }

    private void setupNavigation() {
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
        if (btnGoToHome != null) btnGoToHome.setOnClickListener(v -> finish());
        if (btnGoToPost != null) {
            btnGoToPost.setOnClickListener(v -> {
                Intent intent = new Intent(this, PostActivity.class);
                startActivity(intent);
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (feedAdapter != null) {
            feedAdapter.notifyDataSetChanged();
            updatePostCount();
        }
    }
}
