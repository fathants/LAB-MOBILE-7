package com.example.praktikum3;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.praktikum3.model.Book;
import com.example.praktikum3.model.BookRepository;
import com.google.android.material.textfield.TextInputEditText;

public class DetailActivity extends AppCompatActivity {

    private Book book;

    // Views header
    private ImageView imgCover, imgBack;
    private TextView tvTitle, tvAuthor, tvYear, tvGenre, tvBlurb;

    // Views Like
    private LinearLayout btnLike;
    private ImageView imgLikeBtn;
    private TextView tvLikeText;

    // Views Rating
    private RatingBar ratingBarUser;
    private TextView tvUserRatingValue;

    // Views Review
    private TextInputEditText etUserReview;
    private Button btnSaveReview;
    private CardView cardSavedReview;
    private TextView tvSavedReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int bookId = getIntent().getIntExtra("book_id", -1);
        book = BookRepository.getInstance().findById(bookId);
        if (book == null) {
            finish();
            return;
        }

        initViews();
        bindBook();
        setupListeners();
    }

    private void initViews() {
        imgCover         = findViewById(R.id.imgCover);
        imgBack          = findViewById(R.id.imgBack);
        tvTitle          = findViewById(R.id.tvTitle);
        tvAuthor         = findViewById(R.id.tvAuthor);
        tvYear           = findViewById(R.id.tvYear);
        tvGenre          = findViewById(R.id.tvGenre);
        tvBlurb          = findViewById(R.id.tvBlurb);
        btnLike          = findViewById(R.id.btnLike);
        imgLikeBtn       = findViewById(R.id.imgLikeBtn);
        tvLikeText       = findViewById(R.id.tvLikeText);
        ratingBarUser    = findViewById(R.id.ratingBarUser);
        tvUserRatingValue = findViewById(R.id.tvUserRatingValue);
        etUserReview     = findViewById(R.id.etUserReview);
        btnSaveReview    = findViewById(R.id.btnSaveReview);
        cardSavedReview  = findViewById(R.id.cardSavedReview);
        tvSavedReview    = findViewById(R.id.tvSavedReview);
    }

    private void bindBook() {
        tvTitle.setText(book.getTitle());
        tvAuthor.setText("oleh " + book.getAuthor());
        tvYear.setText("Tahun: " + book.getYear());
        tvGenre.setText(book.getGenre());
        tvBlurb.setText(book.getBlurb());

        if (book.hasCustomCover()) {
            imgCover.clearColorFilter();
            imgCover.setImageURI(book.getCoverUri());
        } else if (book.getCoverResId() != 0) {
            imgCover.clearColorFilter();
            imgCover.setImageResource(book.getCoverResId());
        } else {
            imgCover.setImageResource(R.drawable.bg_image_placeholder);
            imgCover.setColorFilter(getColor(R.color.text_secondary));
        }

        if (book.hasUserRating()) {
            ratingBarUser.setRating(book.getUserRating());
            updateRatingLabel(book.getUserRating());
        }

        if (book.hasUserReview()) {
            etUserReview.setText(book.getUserReview());
            showSavedReview(book.getUserReview());
        }

        updateLikeButton();
    }

    private void setupListeners() {
        imgBack.setOnClickListener(v -> onBackPressed());

        btnLike.setOnClickListener(v -> toggleLike());

        ratingBarUser.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                book.setUserRating(rating);
                updateRatingLabel(rating);
                Toast.makeText(this,
                    "Rating " + rating + " tersimpan!", Toast.LENGTH_SHORT).show();
            }
        });

        btnSaveReview.setOnClickListener(v -> saveReview());
    }

    private void updateRatingLabel(float rating) {
        String label;
        if (rating <= 1)      label = "😞 Mengecewakan";
        else if (rating <= 2) label = "😐 Biasa Saja";
        else if (rating <= 3) label = "🙂 Cukup Bagus";
        else if (rating <= 4) label = "😊 Bagus!";
        else                  label = "🤩 Luar Biasa!";

        tvUserRatingValue.setText(String.format("%.1f / 5.0  —  %s", rating, label));
        tvUserRatingValue.setTextColor(getColor(R.color.primary));
    }

    private void saveReview() {
        String review = etUserReview.getText() != null
            ? etUserReview.getText().toString().trim() : "";

        if (review.isEmpty()) {
            etUserReview.setError("Review tidak boleh kosong!");
            return;
        }

        book.setUserReview(review);
        showSavedReview(review);

        etUserReview.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etUserReview.getWindowToken(), 0);

        Toast.makeText(this, "Review berhasil disimpan!", Toast.LENGTH_SHORT).show();
    }

    private void showSavedReview(String review) {
        cardSavedReview.setVisibility(View.VISIBLE);
        tvSavedReview.setText(review);
    }

    private void toggleLike() {
        book.setLiked(!book.isLiked());
        updateLikeButton();
        String msg = book.isLiked()
            ? "Ditambahkan ke Favorit!"
            : "Dihapus dari Favorit";
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void updateLikeButton() {
        if (book.isLiked()) {
            imgLikeBtn.setImageResource(R.drawable.ic_heart_filled);
            tvLikeText.setText("Hapus dari Favorit");
            btnLike.setBackgroundResource(R.drawable.bg_button_liked);
        } else {
            imgLikeBtn.setImageResource(R.drawable.ic_heart_outline);
            tvLikeText.setText("Tambah ke Favorit");
            btnLike.setBackgroundResource(R.drawable.bg_button_like);
        }
    }
}
