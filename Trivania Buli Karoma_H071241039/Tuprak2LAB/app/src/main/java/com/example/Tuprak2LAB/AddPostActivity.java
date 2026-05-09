package com.example.Tuprak2LAB;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class  AddPostActivity extends AppCompatActivity {

    private Uri selectedImageUri = null;
    private ImageView ivPreview;
    private LinearLayout tvPickHint;

    private final ActivityResultLauncher<String> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    try {
                        getContentResolver().takePersistableUriPermission(
                                uri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        );
                    } catch (Exception e) {
                    }
                    selectedImageUri = uri;
                    ivPreview.setImageURI(uri);
                    ivPreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    if (tvPickHint != null) tvPickHint.setVisibility(android.view.View.GONE);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        ivPreview  = findViewById(R.id.iv_upload_preview);
        tvPickHint = findViewById(R.id.tv_pick_hint);
        EditText etCaption = findViewById(R.id.et_caption_post);

        ivPreview.setOnClickListener(v -> pickImageLauncher.launch("image/*"));

        // Tombol kembali
        findViewById(R.id.btn_back_add_post).setOnClickListener(v -> finish());

        // Tombol Bagikan
        findViewById(R.id.btn_submit_post).setOnClickListener(v -> {
            String caption = etCaption.getText().toString().trim();

            if (selectedImageUri == null) {
                Toast.makeText(this, "Pilih gambar terlebih dahulu!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (caption.isEmpty()) {
                Toast.makeText(this, "Tulis keterangan postingan!", Toast.LENGTH_SHORT).show();
                return;
            }

            ModelPost newPost = new ModelPost(
                    "vaniaakaroma",
                    R.drawable.foto_profil,
                    selectedImageUri,
                    caption
            );
            DataStorage.profileFeedList.add(0, newPost);
            DataStorage.homeFeedList.add(0, newPost);

            Toast.makeText(this, "Postingan berhasil dibagikan!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}