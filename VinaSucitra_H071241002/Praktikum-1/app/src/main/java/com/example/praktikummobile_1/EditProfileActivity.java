package com.example.praktikummobile_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    EditText etName, etUsername, etBio;
    Button btnSave;
    ImageView ivEditProfile;
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUsername);
        etBio = findViewById(R.id.etBio);
        btnSave = findViewById(R.id.btnSave);
        ivEditProfile = findViewById(R.id.ivEditProfile);

        String oldName = getIntent().getStringExtra("current_name");
        String oldUsername = getIntent().getStringExtra("current_username");
        String oldBio = getIntent().getStringExtra("current_bio");

        etName.setText(oldName);
        etUsername.setText(oldUsername);
        etBio.setText(oldBio);

        ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        ivEditProfile.setImageURI(selectedImageUri);
                    }
                }
        );

        ivEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            pickImageLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> {
            String newName = etName.getText().toString();
            String newUsername = etUsername.getText().toString();
            String newBio = etBio.getText().toString();

            Intent intent = new Intent();
            intent.putExtra("name", newName.isEmpty() ? oldName : newName);
            intent.putExtra("username", newUsername.isEmpty() ? oldUsername : newUsername);
            intent.putExtra("bio", newBio.isEmpty() ? oldBio : newBio);

            if (selectedImageUri != null) {
                intent.putExtra("imageUri", selectedImageUri.toString());
            }

            setResult(RESULT_OK, intent);
            finish();
        });
    }
}