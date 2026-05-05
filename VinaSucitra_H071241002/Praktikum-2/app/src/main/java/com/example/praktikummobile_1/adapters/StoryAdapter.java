package com.example.praktikummobile_1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.praktikummobile_1.R;
import com.example.praktikummobile_1.models.Story;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    private List<Story> stories;
    private OnItemClickListener listener;
    private boolean showBorder;

    public interface OnItemClickListener {
        void onItemClick(Story story);
    }

    public StoryAdapter(List<Story> stories, boolean showBorder, OnItemClickListener listener) {
        this.stories = stories;
        this.showBorder = showBorder;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Story story = stories.get(position);
        holder.ivStory.setImageResource(story.getStoryImage());
        holder.tvTitle.setText(story.getTitle());

        if (showBorder) {
            float density = holder.itemView.getContext().getResources().getDisplayMetrics().density;
            holder.ivStory.setBorderWidth((int) (2 * density));
        } else {
            holder.ivStory.setBorderWidth(0);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(story));
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ivStory;
        TextView tvTitle;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStory = itemView.findViewById(R.id.iv_story);
            tvTitle = itemView.findViewById(R.id.tv_story_title);
        }
    }
}
