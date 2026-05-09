package com.example.Tuprak2LAB;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private ProfileGridAdapter gridAdapter;
    private TextView tvPostCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String  username     = getIntent().getStringExtra("username");
        boolean isOwnProfile = (username == null || username.equals("vaniaakaroma"));

        // Inisialisasi semua data
        DataStorage.initHighlightIfEmpty();
        DataStorage.initProfileFeedIfEmpty();
        DataStorage.initHomeFeedIfEmpty();
        DataStorage.initUserHighlightsIfEmpty();
        DataStorage.initUserFeedsIfEmpty();

        ModelPost targetUser = null;
        if (!isOwnProfile) {
            for (ModelPost p : DataStorage.homeFeedList) {
                if (p.getUsername().equals(username)) {
                    targetUser = p;
                    break;
                }
            }
        }

        // ── Toolbar username ──────────────────────────────────────────────────
        TextView tvToolbarName = findViewById(R.id.tv_toolbar_username);
        if (tvToolbarName != null)
            tvToolbarName.setText(isOwnProfile ? "vaniaakaroma" : username);

        // ── Nama lengkap ──────────────────────────────────────────────────────
        TextView tvFullName = findViewById(R.id.tv_full_name);
        if (tvFullName != null)
            tvFullName.setText(isOwnProfile ? "Trivania Buli Karoma"
                    : (targetUser != null ? targetUser.getFullName() : username));

        // ── Foto profil ───────────────────────────────────────────────────────
        ImageView ivProfilePhoto = findViewById(R.id.iv_profile_photo);
        if (ivProfilePhoto != null) {
            if (isOwnProfile) {
                ivProfilePhoto.setImageResource(R.drawable.foto_profil);
            } else if (targetUser != null && targetUser.getProfileResId() != 0) {
                ivProfilePhoto.setImageResource(targetUser.getProfileResId());
            }
        }

        // ── Pengikut & Mengikuti ──────────────────────────────────────────────
        TextView tvFollowers = findViewById(R.id.tv_followers_count);
        TextView tvFollowing = findViewById(R.id.tv_following_count);
        if (isOwnProfile) {
            if (tvFollowers != null) tvFollowers.setText("5.800");
            if (tvFollowing != null) tvFollowing.setText("1.131");
        } else if (targetUser != null) {
            if (tvFollowers != null) tvFollowers.setText(targetUser.getFollowers());
            if (tvFollowing != null) tvFollowing.setText(targetUser.getFollowing());
        }

        // ── Teks "Diikuti oleh" ───────────────────────────────────────────────
        TextView tvFollowedBy = findViewById(R.id.tv_followed_by);
        if (tvFollowedBy != null) {
            if (isOwnProfile) {
                tvFollowedBy.setText(
                        "Diikuti oleh real__pcy, goyounjung, dan 20 lainnya");
            } else if (targetUser != null) {
                tvFollowedBy.setText(targetUser.getFollowedBy());
            }
        }

        // ── Navigasi ke Home (Compass) ────────────────────────────────────────
        ImageView btnNavHome = findViewById(R.id.btn_nav_home);
        if (btnNavHome != null) {
            btnNavHome.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            });
        }

        android.view.View btnAddPostMiddle = findViewById(R.id.btn_add_post);
        android.view.View btnNavAddPost = findViewById(R.id.btn_nav_add_post);

        android.view.View.OnClickListener addPostListener = v -> {
            startActivity(new Intent(ProfileActivity.this, AddPostActivity.class));
        };

        if (btnAddPostMiddle != null) {
            if (isOwnProfile) {
                btnAddPostMiddle.setVisibility(android.view.View.VISIBLE);
                btnAddPostMiddle.setOnClickListener(addPostListener);
            } else {
                btnAddPostMiddle.setVisibility(android.view.View.INVISIBLE);
            }
        }

        if (btnNavAddPost != null) {
            btnNavAddPost.setOnClickListener(addPostListener);
        }

        // ── Highlight RecyclerView ────────────────────────────────────────────
        RecyclerView rvHighlight = findViewById(R.id.rv_highlight);
        if (rvHighlight != null) {
            rvHighlight.setLayoutManager(
                    new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            ArrayList<ModelPost> hlToShow;

            if (isOwnProfile) {
                hlToShow = DataStorage.highlightList;
                rvHighlight.setAdapter(new HighlightAdapter(
                        hlToShow,
                        this,
                        "vaniaakaroma",
                        R.drawable.foto_profil
                ));
            } else {
                hlToShow = DataStorage.userHighlightMap.get(username);
                if (hlToShow == null) hlToShow = new ArrayList<>();

                int ownerResId = (targetUser != null) ? targetUser.getProfileResId() : 0;
                rvHighlight.setAdapter(new HighlightAdapter(
                        hlToShow,
                        this,
                        username,
                        ownerResId
                ));
            }
        }
        // ── Grid feed ─────────────────────────────────────────────────────────
        tvPostCount = findViewById(R.id.tv_post_count);
        RecyclerView rvGrid = findViewById(R.id.rv_profile_grid);
        if (rvGrid != null) {
            rvGrid.setLayoutManager(new GridLayoutManager(this, 3));

            java.util.List<ModelPost> feedToShow;
            if (isOwnProfile) {
                feedToShow = DataStorage.profileFeedList;
            } else {
                feedToShow = DataStorage.userFeedMap.get(username);
                if (feedToShow == null) feedToShow = new ArrayList<>();
            }

            gridAdapter = new ProfileGridAdapter(feedToShow, this);
            rvGrid.setAdapter(gridAdapter);
            updatePostCount(feedToShow.size());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (gridAdapter != null) gridAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gridAdapter != null) {
            gridAdapter.notifyDataSetChanged();

            String username = getIntent().getStringExtra("username");
            boolean isOwnProfile = (username == null || username.equals("vaniaakaroma"));

            android.widget.Button btnEdit = findViewById(R.id.btn_edit_profile);
            android.widget.Button btnShareOrMessage = findViewById(R.id.btn_share_profile);

            if (btnEdit != null) {
                btnEdit.setText(isOwnProfile ? "Edit profil" : "Mengikuti");
            }

            if (btnShareOrMessage != null) {
                btnShareOrMessage.setText(isOwnProfile ? "Bagikan profil" : "Kirim pesan");
            }
        }
    }

    private void updatePostCount(int count) {
        if (tvPostCount != null) tvPostCount.setText(String.valueOf(count));
    }
}