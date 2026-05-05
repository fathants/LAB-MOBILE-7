package com.example.praktikummobile_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.praktikummobile_1.models.Post;

public class PostActivity extends AppCompatActivity {

    private ImageView ivPostImage, ivClose;
    private EditText etCaption;
    private Button btnUpload;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ivPostImage = findViewById(R.id.iv_post_image);
        ivClose = findViewById(R.id.ivClose);
        etCaption = findViewById(R.id.et_caption);
        btnUpload = findViewById(R.id.btn_upload);

        ivPostImage.setOnClickListener(v -> openGallery());
        ivClose.setOnClickListener(v -> finish());

        btnUpload.setOnClickListener(v -> {
            String caption = etCaption.getText().toString();
            if (imageUri != null && !caption.isEmpty()) {
                Post.profilePosts.add(0, new Post(R.drawable.vina, "vina_sucitra024", imageUri.toString(), caption));
                Toast.makeText(this, "Berhasil dibagikan!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Pilih gambar dan isi caption!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    ivPostImage.setImageURI(imageUri);
                    final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                    getContentResolver().takePersistableUriPermission(imageUri, takeFlags);
                }
            }
    );
}
