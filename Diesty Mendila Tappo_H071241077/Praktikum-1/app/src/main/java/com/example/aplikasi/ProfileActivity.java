package com.example.aplikasi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    TextView tvName, tvUsername, tvBio, tvUsernameTop;
    ImageView ivProfilePic, ivBottomNavProfile, ivUsernameIcon;
    Button btnEdit;

    boolean showThreads = true;

    ActivityResultLauncher<Intent> editLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvName             = findViewById(R.id.tv_name);
        tvUsername         = findViewById(R.id.tv_username);
        tvBio              = findViewById(R.id.tv_bio);
        tvUsernameTop      = findViewById(R.id.tv_username_top);
        ivProfilePic       = findViewById(R.id.iv_profile_pic);
        ivBottomNavProfile = findViewById(R.id.iv_bottom_nav_profile);
        ivUsernameIcon     = findViewById(R.id.iv_username_icon);
        btnEdit            = findViewById(R.id.btn_edit_profile);

        editLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {

                            String name     = data.getStringExtra("name");
                            String username = data.getStringExtra("username");
                            String bio      = data.getStringExtra("bio");

                            if (name != null) tvName.setText(name);

                            if (username != null) {
                                tvUsername.setText(username);    // dekat logo Threads
                                tvUsernameTop.setText(username); // di top bar
                            }

                            if (bio != null) tvBio.setText(bio);

                            // Update foto profil
                            String photoUri = data.getStringExtra("photo_uri");
                            if (photoUri != null) {
                                Uri uri = Uri.parse(photoUri);
                                ivProfilePic.setImageURI(uri);
                                ivBottomNavProfile.setImageURI(uri);
                                ivProfilePic.setTag(photoUri);
                            }

                            // Toggle logo Threads
                            showThreads = data.getBooleanExtra("show_threads", true);
                            ivUsernameIcon.setVisibility(
                                    showThreads ? View.VISIBLE : View.GONE
                            );
                        }
                    }
                }
        );

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            intent.putExtra("name",         tvName.getText().toString());
            intent.putExtra("username",     tvUsername.getText().toString());
            intent.putExtra("bio",          tvBio.getText().toString());
            intent.putExtra("show_threads", showThreads);

            Object tag = ivProfilePic.getTag();
            if (tag != null) intent.putExtra("photo_uri", tag.toString());

            editLauncher.launch(intent);
        });
    }
}