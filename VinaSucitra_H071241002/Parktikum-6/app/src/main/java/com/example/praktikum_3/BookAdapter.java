package com.example.praktikum_3;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> books;
    private OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(Book book);
        void onLikeClick(Book book, int position);
    }

    public BookAdapter(List<Book> books, OnBookClickListener listener) {
        this.books = books;
        this.listener = listener;
    }

    public void updateData(List<Book> newBooks) {
        this.books = newBooks;
        notifyDataSetChanged();
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvYear.setText(String.valueOf(book.getYear()));
        holder.ratingBar.setRating(book.getRating());

        Context context = holder.itemView.getContext();
        
        Object imageSource;
        int resId = context.getResources().getIdentifier(book.getImageUri(), "drawable", context.getPackageName());
        
        if (resId != 0) {
            imageSource = resId;
        } else {
            imageSource = book.getImageUri();
        }

        Glide.with(context)
                .load(imageSource)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(holder.ivCover);

        // Mengatur ikon dan warna bintang berdasarkan status like
        if (book.isLiked()) {
            holder.btnLike.setImageResource(android.R.drawable.btn_star_big_on);
            holder.btnLike.setColorFilter(Color.parseColor("#FFD700")); // Kuning Emas
        } else {
            holder.btnLike.setImageResource(android.R.drawable.btn_star_big_off);
            holder.btnLike.setColorFilter(Color.parseColor("#808080")); // Abu-abu
        }

        holder.itemView.setOnClickListener(v -> listener.onBookClick(book));
        holder.btnLike.setOnClickListener(v -> listener.onLikeClick(book, holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView ivCover;
        TextView tvTitle, tvAuthor, tvYear;
        RatingBar ratingBar;
        ImageButton btnLike;

        public BookViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.ivBookCover);
            tvTitle = itemView.findViewById(R.id.tvBookTitle);
            tvAuthor = itemView.findViewById(R.id.tvBookAuthor);
            tvYear = itemView.findViewById(R.id.tvBookYear);
            ratingBar = itemView.findViewById(R.id.ratingBarSmall);
            btnLike = itemView.findViewById(R.id.btnLike);
        }
    }
}
