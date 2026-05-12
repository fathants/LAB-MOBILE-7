package com.example.praktikum_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView imgEditProfile;
    private String selectedImageUri = "";

    private final ActivityResultLauncher<String> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    try {
                        getContentResolver().takePersistableUriPermission(uri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }

                    imgEditProfile.setImageURI(uri);
                    selectedImageUri = uri.toString();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        imgEditProfile = findViewById(R.id.imgEditProfile);
        TextView tvChangePhoto = findViewById(R.id.tvChangePhoto);
        EditText etName = findViewById(R.id.etEditName);
        EditText etUser = findViewById(R.id.etEditUsername);
        EditText etBio = findViewById(R.id.etEditBio);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnBack = findViewById(R.id.btnBack);

        etName.setText(getIntent().getStringExtra("OLD_NAME"));
        etUser.setText(getIntent().getStringExtra("OLD_USERNAME"));
        etBio.setText(getIntent().getStringExtra("OLD_BIO"));

        tvChangePhoto.setOnClickListener(v -> pickImageLauncher.launch("image/*"));

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String user = etUser.getText().toString();
            String bio = etBio.getText().toString();

            if (name.isEmpty() || user.isEmpty()) {
                Toast.makeText(this, "Nama dan Username tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            } else {
                Intent resIntent = new Intent();
                resIntent.putExtra("NAME", name);
                resIntent.putExtra("USERNAME", user);
                resIntent.putExtra("BIO", bio);
                resIntent.putExtra("NEW_IMAGE", selectedImageUri);

                setResult(RESULT_OK, resIntent);
                finish();
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }
}