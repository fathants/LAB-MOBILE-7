package com.example.praktikummobile_1.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.praktikummobile_1.ProfileActivity;
import com.example.praktikummobile_1.R;
import com.example.praktikummobile_1.models.Post;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> posts;
    private Context context;

    public PostAdapter(List<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);

        holder.tvUsername.setText(post.getUsername());
        holder.ivProfile.setImageResource(post.getProfileImage());

        String username = post.getUsername();
        String caption = post.getCaption();
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(username);
        builder.setSpan(new StyleSpan(Typeface.BOLD), 0, username.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(" ");
        builder.append(caption);
        holder.tvCaption.setText(builder);
        
        if (post.getPostImage() != 0) {
            holder.ivFeed.setImageResource(post.getPostImage());
        } else if (post.getPostImageUri() != null) {
            holder.ivFeed.setImageURI(Uri.parse(post.getPostImageUri()));
        }

        View.OnClickListener toProfile = v -> {
            Intent intent = new Intent(context, ProfileActivity.class);
            context.startActivity(intent);
        };

        holder.ivProfile.setOnClickListener(toProfile);
        holder.tvUsername.setOnClickListener(toProfile);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile, ivFeed;
        TextView tvUsername, tvCaption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.iv_profile_post);
            ivFeed = itemView.findViewById(R.id.iv_feed_content);
            tvUsername = itemView.findViewById(R.id.tv_username_post);
            tvCaption = itemView.findViewById(R.id.tv_caption);
        }
    }
}
