package com.example.lab;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class StoryBarAdapter extends RecyclerView.Adapter<StoryBarAdapter.ViewHolder> {
    private final List<ModelPost> list;
    private final Context context;

    private static final int POSITION_OWN_STORY = 0;

    public StoryBarAdapter(List<ModelPost> list, Context context) {
        this.list    = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_home_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // ── Posisi 0 = Story milik sendiri (dies_) ──────────────────────────
        if (position == POSITION_OWN_STORY) {
            holder.tvName.setText("dies_");

            holder.ivStory.setImageResource(R.drawable.foto_profil);

            holder.itemView.setOnClickListener(v -> {
                DataStorage.initHighlightIfEmpty();
                int imageRes = DataStorage.highlightList.isEmpty()
                        ? R.drawable.foto_profil
                        : DataStorage.highlightList.get(0).getImageResId();

                Intent intent = new Intent(context, StoryActivity.class);
                intent.putExtra("image",        imageRes);
                intent.putExtra("username",     "dies_");
                intent.putExtra("profileResId", R.drawable.foto_profil);
                context.startActivity(intent);
            });

            // ── Posisi 1-10 = Akun dari homeFeedList ─────────────────────────────
        } else {
            ModelPost post = list.get(position - 1);

            holder.tvName.setText(post.getUsername());

            if (post.getProfileResId() != 0) {
                holder.ivStory.setImageResource(post.getProfileResId());
            } else {
                holder.ivStory.setImageResource(R.drawable.foto_profil);
            }

            holder.itemView.setOnClickListener(v -> {
                DataStorage.initUserFeedsIfEmpty();
                ArrayList<ModelPost> userFeed =
                        DataStorage.userFeedMap.get(post.getUsername());

                int storyImage = (userFeed != null && !userFeed.isEmpty())
                        ? userFeed.get(0).getImageResId()
                        : post.getProfileResId();

                Intent intent = new Intent(context, StoryActivity.class);
                intent.putExtra("image",        storyImage);
                intent.putExtra("username",     post.getUsername());
                intent.putExtra("profileResId", post.getProfileResId());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivStory;
        TextView  tvName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStory = itemView.findViewById(R.id.iv_story_image);
            tvName  = itemView.findViewById(R.id.tv_story_name);
        }
    }
}