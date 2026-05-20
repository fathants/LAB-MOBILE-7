package com.example.BukaMata;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int bookId = getIntent().getIntExtra("book_id", -1);
        book = BookRepository.getInstance().getBookById(bookId);

        if (book == null) {
            finish();
            return;
        }

        ImageView ivCover = findViewById(R.id.ivDetailCover);
        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvAuthor = findViewById(R.id.tvDetailAuthor);
        TextView tvYear = findViewById(R.id.tvDetailYear);
        TextView tvGenre = findViewById(R.id.tvDetailGenre);
        TextView tvBlurb = findViewById(R.id.tvDetailBlurb);
        TextView tvReview = findViewById(R.id.tvDetailReview);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        TextView tvRatingValue = findViewById(R.id.tvRatingValue);
        Button btnLike = findViewById(R.id.btnLike);
        ImageView ivBack = findViewById(R.id.ivBack);

        tvTitle.setText(book.getTitle());
        tvAuthor.setText("by " + book.getAuthor());
        tvYear.setText("Published: " + book.getYear());
        tvGenre.setText(book.getGenre());
        tvBlurb.setText(book.getBlurb());
        tvReview.setText(book.getReview());
        ratingBar.setRating(book.getRating());
        tvRatingValue.setText(String.format("%.1f / 5.0", book.getRating()));

        if (book.hasCustomCover()) {
            ivCover.setImageURI(book.getCoverUri());
        } else {
            ivCover.setImageResource(book.getCoverResId());
        }

        updateLikeButton(btnLike);

        btnLike.setOnClickListener(v -> {
            book.setLiked(!book.isLiked());
            updateLikeButton(btnLike);
            String msg = book.isLiked() ? "Added to Favorites!" : "Removed from Favorites";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        ivBack.setOnClickListener(v -> finish());
    }

    private void updateLikeButton(Button btn) {
        if (book.isLiked()) {
            btn.setText("♥ Remove from Favorites");
            btn.setBackgroundTintList(
                    getColorStateList(R.color.like_active));
        } else {
            btn.setText("♡ Add to Favorites");
            btn.setBackgroundTintList(
                    getColorStateList(R.color.like_inactive));
        }
    }
}
