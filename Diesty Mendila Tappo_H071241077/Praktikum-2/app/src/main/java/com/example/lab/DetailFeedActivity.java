package com.example.lab;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_feed);

        ImageView img = findViewById(R.id.img_detail_feed);
        TextView caption = findViewById(R.id.txt_detail_caption);
        TextView tvUsername = findViewById(R.id.tv_detail_username);

        // Ambil data dari intent
        int imageRes = getIntent().getIntExtra("image", 0);
        String cap = getIntent().getStringExtra("caption");
        String username = getIntent().getStringExtra("username");
        String uriString = getIntent().getStringExtra("imageUri");

        // Tampilkan gambar: prioritaskan Uri, fallback ke resource
        if (uriString != null) {
            img.setImageURI(Uri.parse(uriString));
        } else if (imageRes != 0) {
            img.setImageResource(imageRes);
        }

        caption.setText(cap != null ? cap : "");
        if (tvUsername != null) {
            tvUsername.setText(username != null ? username : "user");
        }

        // Tombol kembali
        findViewById(R.id.btn_back_detail).setOnClickListener(v -> finish());
    }
}