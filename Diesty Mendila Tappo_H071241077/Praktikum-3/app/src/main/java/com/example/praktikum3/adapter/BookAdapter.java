package com.example.praktikum3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.praktikum3.R;
import com.example.praktikum3.model.Book;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context context;
    private List<Book> books;
    private OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    public BookAdapter(Context context, List<Book> books, OnBookClickListener listener) {
        this.context = context;
        this.books = books;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bind(books.get(position));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void updateList(List<Book> newBooks) {
        this.books = newBooks;
        notifyDataSetChanged();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCover, imgLike;
        TextView tvTitle, tvAuthor, tvYear, tvGenre, tvRating;

        BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCover = itemView.findViewById(R.id.imgCover);
            imgLike  = itemView.findViewById(R.id.imgLike);
            tvTitle  = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvYear   = itemView.findViewById(R.id.tvYear);
            tvGenre  = itemView.findViewById(R.id.tvGenre);
            tvRating = itemView.findViewById(R.id.tvRating);
        }

        void bind(Book book) {
            tvTitle.setText(book.getTitle());
            tvAuthor.setText(book.getAuthor());
            tvYear.setText(String.valueOf(book.getYear()));
            tvGenre.setText(book.getGenre());

            if (book.hasUserRating()) {
                tvRating.setText(String.format("★ %.1f", book.getUserRating()));
                tvRating.setVisibility(View.VISIBLE);
            } else {
                tvRating.setText("★ Belum dirating");
                tvRating.setVisibility(View.VISIBLE);
            }

            if (book.hasCustomCover()) {
                imgCover.clearColorFilter();
                imgCover.setImageURI(book.getCoverUri());
            } else if (book.getCoverResId() != 0) {
                imgCover.clearColorFilter();
                imgCover.setImageResource(book.getCoverResId());
            } else {
                imgCover.setImageResource(R.drawable.bg_image_placeholder);
                imgCover.setColorFilter(context.getColor(R.color.text_secondary));
            }

            imgLike.setImageResource(book.isLiked()
                ? R.drawable.ic_heart_filled
                : R.drawable.ic_heart_outline);

            itemView.setOnClickListener(v -> listener.onBookClick(book));
        }
    }
}
