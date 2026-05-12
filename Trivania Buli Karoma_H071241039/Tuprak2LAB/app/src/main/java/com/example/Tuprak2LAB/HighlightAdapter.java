package com.example.Tuprak2LAB;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.ViewHolder> {
    private final List<ModelPost> list;
    private final Context context;
    private final String profileOwnerUsername;
    private final int profileOwnerResId;

    public HighlightAdapter(List<ModelPost> list, Context context,
                            String profileOwnerUsername, int profileOwnerResId) {
        this.list = list;
        this.context = context;
        this.profileOwnerUsername = profileOwnerUsername;
        this.profileOwnerResId = profileOwnerResId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_highlight, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelPost post = list.get(position);

        holder.tvName.setText(post.getUsername());

        // Gambar highlight dari post
        if (post.getImageResId() != 0) {
            holder.imgHighlight.setImageResource(post.getImageResId());
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, StoryActivity.class);
            intent.putExtra("image", post.getImageResId());
            intent.putExtra("username", profileOwnerUsername);   // ← nama pemilik profil
            intent.putExtra("profileResId", profileOwnerResId);  // ← foto pemilik profil
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHighlight;
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHighlight = itemView.findViewById(R.id.iv_highlight);
            tvName       = itemView.findViewById(R.id.tv_highlight_name);
        }
    }
}