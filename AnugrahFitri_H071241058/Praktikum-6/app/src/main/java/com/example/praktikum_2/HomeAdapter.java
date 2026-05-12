package com.example.praktikum_2;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<Post> postList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onProfileClick(Post post);
    }

    public HomeAdapter(List<Post> postList, OnItemClickListener listener) {
        this.postList = postList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.tvUsername.setText(post.getUsername());
        holder.tvCaption.setText(post.getCaption());
        holder.imgProfile.setImageResource(post.getProfileImage());

        if (post.getPostImage() != 0) {
            holder.imgContent.setImageResource(post.getPostImage());
        } else if (post.getImageUri() != null) {
            holder.imgContent.setImageURI(Uri.parse(post.getImageUri()));
        }

        holder.imgProfile.setOnClickListener(v -> listener.onProfileClick(post));
        holder.tvUsername.setOnClickListener(v -> listener.onProfileClick(post));
    }

    @Override
    public int getItemCount() { return postList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProfile, imgContent;
        TextView tvUsername, tvCaption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfile = itemView.findViewById(R.id.imgFeedProfile);
            imgContent = itemView.findViewById(R.id.imgFeedContent);
            tvUsername = itemView.findViewById(R.id.tvFeedUsername);
            tvCaption = itemView.findViewById(R.id.tvFeedCaption);
        }
    }
}
