package com.example.praktikummobile_1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvName, tvUsername, tvBio, tvBio2;
    ImageView ivProfile;
    Button btnEdit;

    ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tvName);
        tvUsername = findViewById(R.id.tvUsername);
        tvBio = findViewById(R.id.tvBio);
        tvBio2 = findViewById(R.id.tvBio2);
        ivProfile = findViewById(R.id.ivProfile);
        btnEdit = findViewById(R.id.btnEdit);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String name = data.getStringExtra("name");
                            String username = data.getStringExtra("username");
                            String fullBio = data.getStringExtra("bio"); // Menerima bio lengkap
                            String imageUriStr = data.getStringExtra("imageUri");

                            tvName.setText(name);
                            tvUsername.setText(username);

                            // Memecah kembali Bio menjadi 2 baris jika ada enter (\n)
                            if (fullBio != null) {
                                String[] parts = fullBio.split("\n", 2);
                                tvBio.setText(parts[0]); // Baris pertama
                                if (parts.length > 1) {
                                    tvBio2.setText(parts[1]); // Baris kedua
                                    tvBio2.setVisibility(android.view.View.VISIBLE);
                                } else {
                                    tvBio2.setText("");
                                    tvBio2.setVisibility(android.view.View.GONE);
                                }
                            }

                            if (imageUriStr != null) {
                                ivProfile.setImageURI(Uri.parse(imageUriStr));
                            }
                        }
                    }
                }
        );

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);

            // Menggabungkan dua baris bio untuk dikirim ke satu kotak edit
            String combinedBio = tvBio.getText().toString() + "\n" + tvBio2.getText().toString();

            intent.putExtra("current_name", tvName.getText().toString());
            intent.putExtra("current_username", tvUsername.getText().toString());
            intent.putExtra("current_bio", combinedBio.trim());

            launcher.launch(intent);
        });
    }
}