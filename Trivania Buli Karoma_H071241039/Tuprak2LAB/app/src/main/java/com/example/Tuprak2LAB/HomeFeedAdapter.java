package com.example.Tuprak2LAB;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HomeFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_STORY_BAR = 0;
    private static final int TYPE_FEED      = 1;

    private final List<ModelPost> feedList;
    private final List<ModelPost> storyList;
    private final Context context;

    public HomeFeedAdapter(List<ModelPost> feedList, List<ModelPost> storyList, Context context) {
        this.feedList  = feedList;
        this.storyList = storyList;
        this.context   = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_STORY_BAR : TYPE_FEED;
    }

    @Override
    public int getItemCount() {
        return feedList.size() + 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(context);
        if (viewType == TYPE_STORY_BAR) {
            View v = inf.inflate(R.layout.item_story_bar, parent, false);
            return new StoryBarVH(v);
        } else {
            View v = inf.inflate(R.layout.item_home_feed, parent, false);
            return new FeedVH(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StoryBarVH) {
            StoryBarVH h = (StoryBarVH) holder;
            h.rvStory.setLayoutManager(
                    new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            h.rvStory.setAdapter(new StoryBarAdapter(storyList, context));

        } else if (holder instanceof FeedVH) {
            int idx = position - 1;
            ModelPost post = feedList.get(idx);
            FeedVH h = (FeedVH) holder;

            h.tvUsername.setText(post.getUsername());

            h.ivProfile.setImageResource(
                    post.getProfileResId() != 0
                            ? post.getProfileResId()
                            : R.drawable.foto_profil);

            if (post.getImageUri() != null) {
                try {
                    h.ivFeed.setImageURI(null); // reset dulu
                    h.ivFeed.setImageURI(post.getImageUri());
                } catch (Exception e) {
                    h.ivFeed.setImageResource(R.drawable.feed1);
                }
            } else {
                h.ivFeed.setImageResource(post.getImageResId());
            }

            h.tvCaption.setText(post.getUsername() + "  " + post.getCaption());

            View.OnClickListener toProfile = v -> {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("username", post.getUsername());
                context.startActivity(intent);
            };
            h.ivProfile.setOnClickListener(toProfile);
            h.tvUsername.setOnClickListener(toProfile);

            h.ivFeed.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailFeedActivity.class);
                intent.putExtra("username", post.getUsername());
                intent.putExtra("caption", post.getCaption());
                if (post.getImageUri() != null) {
                    intent.putExtra("imageUri", post.getImageUri().toString());
                } else {
                    intent.putExtra("image", post.getImageResId());
                }
                context.startActivity(intent);
            });
        }
    }

    static class StoryBarVH extends RecyclerView.ViewHolder {
        RecyclerView rvStory;
        StoryBarVH(@NonNull View v) {
            super(v);
            rvStory = v.findViewById(R.id.rv_story_bar);
        }
    }

    static class FeedVH extends RecyclerView.ViewHolder {
        ImageView ivProfile, ivFeed;
        TextView tvUsername, tvCaption;
        FeedVH(@NonNull View v) {
            super(v);
            ivProfile  = v.findViewById(R.id.iv_profile_home);
            ivFeed     = v.findViewById(R.id.iv_feed_home);
            tvUsername = v.findViewById(R.id.tv_username_home);
            tvCaption  = v.findViewById(R.id.tv_caption_home);
        }
    }
}