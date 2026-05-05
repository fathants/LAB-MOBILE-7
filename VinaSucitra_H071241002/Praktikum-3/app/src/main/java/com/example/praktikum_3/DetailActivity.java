package com.example.praktikum_3;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class DetailActivity extends AppCompatActivity {

    private ShapeableImageView ivCover;
    private TextView tvTitle, tvAuthor, tvYear, tvGenre, tvBlurb;
    private RatingBar ratingBar;
    private MaterialButton btnLike;
    private ImageButton btnBack;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivCover = findViewById(R.id.ivDetailCover);
        tvTitle = findViewById(R.id.tvDetailTitle);
        tvAuthor = findViewById(R.id.tvDetailAuthor);
        tvYear = findViewById(R.id.tvDetailYear);
        tvGenre = findViewById(R.id.tvDetailGenre);
        tvBlurb = findViewById(R.id.tvDetailBlurb);
        ratingBar = findViewById(R.id.ratingBarDetail);
        btnLike = findViewById(R.id.btnLikeDetail);
        btnBack = findViewById(R.id.btnBack);

        book = getIntent().getParcelableExtra("BOOK_EXTRA");

        if (book != null) {
            displayBookDetails();
        }

        btnBack.setOnClickListener(v -> finish());

        btnLike.setOnClickListener(v -> {
            boolean newStatus = !book.isLiked();
            book.setLiked(newStatus);
            // Simpan perubahan ke DataSource
            DataSource.updateBookLikeStatus(book.getId(), newStatus);
            updateLikeButton();
        });
    }

    private void displayBookDetails() {
        tvTitle.setText(book.getTitle());
        tvAuthor.setText(book.getAuthor());
        tvYear.setText(String.valueOf(book.getYear()));
        tvGenre.setText(book.getGenre());
        tvBlurb.setText(book.getBlurb());
        ratingBar.setRating(book.getRating());

        // Logika untuk menangani gambar dari Drawable atau URI
        Object imageSource;
        int resId = getResources().getIdentifier(book.getImageUri(), "drawable", getPackageName());
        
        if (resId != 0) {
            imageSource = resId;
        } else {
            imageSource = book.getImageUri();
        }

        Glide.with(this)
                .load(imageSource)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(ivCover);

        updateLikeButton();
    }

    private void updateLikeButton() {
        if (book.isLiked()) {
            btnLike.setText("Remove from Favorites");
            btnLike.setIconResource(android.R.drawable.btn_star_big_on);
        } else {
            btnLike.setText("Add to Favorites");
            btnLike.setIconResource(android.R.drawable.btn_star_big_off);
        }
    }
}
