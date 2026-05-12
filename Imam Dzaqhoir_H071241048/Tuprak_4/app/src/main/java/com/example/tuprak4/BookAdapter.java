package com.example.tuprak4;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context context;
    private ArrayList<BookModel> bookList;

    public BookAdapter(Context context, ArrayList<BookModel> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookModel book = bookList.get(position);

        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());

        Glide.with(context)
             .load(book.getImageUrl())
             .into(holder.imgCover);

        if (book.isFavorite()) {
            holder.btnLike.setImageResource(R.drawable.ic_liked);
        } else {
            holder.btnLike.setImageResource(R.drawable.ic_unliked);
        }

        holder.btnLike.setOnClickListener(v -> {
            boolean current = book.isFavorite();
            book.setFavorite(!current);
            notifyItemChanged(position);
        });

        holder.itemView.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                BookModel clickedBook = bookList.get(currentPosition);
                int originalIndex = DataDummy.getBooks().indexOf(clickedBook);

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("INDEX", originalIndex);
                intent.putExtra("TITLE", clickedBook.getTitle());
                intent.putExtra("AUTHOR", clickedBook.getAuthor());
                intent.putExtra("YEAR", clickedBook.getYear());
                intent.putExtra("BLURB", clickedBook.getBlurb());
                intent.putExtra("IMAGE", clickedBook.getImageUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCover;
        TextView tvTitle;
        TextView tvAuthor;
        ImageView btnLike;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCover = itemView.findViewById(R.id.img_item_cover);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvAuthor = itemView.findViewById(R.id.tv_item_author);
            btnLike = itemView.findViewById(R.id.btn_item_like);
        }
    }
}
