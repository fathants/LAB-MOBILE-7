package com.example.Tuprak2LAB;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProfileGridAdapter extends RecyclerView.Adapter<ProfileGridAdapter.ViewHolder> {
    private final List<ModelPost> list;
    private final Context context;

    public ProfileGridAdapter(List<ModelPost> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelPost post = list.get(position);

        if (post.getImageUri() != null) {
            try {
                holder.imgGrid.setImageURI(null);
                holder.imgGrid.setImageURI(post.getImageUri());
            } catch (Exception e) {
                holder.imgGrid.setImageResource(R.drawable.feed1);
            }
        } else {
            holder.imgGrid.setImageResource(post.getImageResId());
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailFeedActivity.class);
            intent.putExtra("image", post.getImageResId());
            intent.putExtra("caption", post.getCaption());
            intent.putExtra("username", post.getUsername());
            if (post.getImageUri() != null) {
                intent.putExtra("imageUri", post.getImageUri().toString());
            }
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGrid;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGrid = itemView.findViewById(R.id.iv_item_grid);
        }
    }
}