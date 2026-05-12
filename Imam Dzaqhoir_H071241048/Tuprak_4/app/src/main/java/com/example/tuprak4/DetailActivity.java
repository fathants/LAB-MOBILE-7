package com.example.tuprak4;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imgCover = findViewById(R.id.img_detail_cover);
        TextView tvTitle = findViewById(R.id.tv_detail_title);
        TextView tvAuthor = findViewById(R.id.tv_detail_author);
        TextView tvYear = findViewById(R.id.tv_detail_year);
        TextView tvBlurb = findViewById(R.id.tv_detail_blurb);

        ImageView btnLike = findViewById(R.id.btn_detail_like);

        // Receive Data
        int index = getIntent().getIntExtra("INDEX", -1);
        String title = getIntent().getStringExtra("TITLE");
        String author = getIntent().getStringExtra("AUTHOR");
        int year = getIntent().getIntExtra("YEAR", 0);
        String blurb = getIntent().getStringExtra("BLURB");
        String imageUrl = getIntent().getStringExtra("IMAGE");

        // Set Data to Views
        tvTitle.setText(title);
        tvAuthor.setText(author);
        tvYear.setText(String.valueOf(year));
        tvBlurb.setText(blurb);

        // Load Image
        Glide.with(this)
             .load(imageUrl)
             .into(imgCover);

        // Handle Like Data
        if (index != -1 && index < DataDummy.getBooks().size()) {
            BookModel book = DataDummy.getBooks().get(index);
            
            if (book.isFavorite()) {
                btnLike.setImageResource(R.drawable.ic_liked);
            } else {
                btnLike.setImageResource(R.drawable.ic_unliked);
            }

            btnLike.setOnClickListener(v -> {
                boolean current = book.isFavorite();
                book.setFavorite(!current);
                
                if (book.isFavorite()) {
                    btnLike.setImageResource(R.drawable.ic_liked);
                } else {
                    btnLike.setImageResource(R.drawable.ic_unliked);
                }
            });
        }
    }
}
