package com.example.tuprak5;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuprak5.databinding.ItemPostBinding;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postList;

    public PostAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.binding.tvUsername.setText(post.getUsername());
        holder.binding.tvContent.setText(post.getContent());
        holder.binding.tvTime.setText(post.getTime());

        // Cycle through dummy avatars
        if (position % 2 == 0) {
            holder.binding.ivAvatar.setImageResource(R.drawable.avatar1);
        } else {
            holder.binding.ivAvatar.setImageResource(R.drawable.avatar2);
        }

        // Show image only for some posts to make it look natural
        if (position % 3 == 0) {
            holder.binding.postImageCard.setVisibility(android.view.View.VISIBLE);
        } else {
            holder.binding.postImageCard.setVisibility(android.view.View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return postList != null ? postList.size() : 0;
    }

    public void setPosts(List<Post> newPosts) {
        this.postList = newPosts;
        notifyDataSetChanged();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        ItemPostBinding binding;

        public PostViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
