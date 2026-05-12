package com.example.praktikum_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    private List<Post> userPosts;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Post post);
    }

    public ProfileAdapter(List<Post> userPosts, OnItemClickListener listener) {
        this.userPosts = userPosts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = userPosts.get(position);
        if (post.getPostImage() != 0) {
            holder.imgPost.setImageResource(post.getPostImage());
        } else if (post.getImageUri() != null) {
            holder.imgPost.setImageURI(android.net.Uri.parse(post.getImageUri()));
        }
        holder.itemView.setOnClickListener(v -> listener.onItemClick(post));
    }

    @Override
    public int getItemCount() {
        return userPosts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPost;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPost = itemView.findViewById(R.id.imgGridPost);
        }
    }
}