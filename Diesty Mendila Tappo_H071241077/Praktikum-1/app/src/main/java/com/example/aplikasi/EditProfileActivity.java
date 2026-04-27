package com.example.aplikasi;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    EditText etName, etUsername, etBio;
    TextView tvCancel, tvDone, tvEditPhoto, tvGenderValue;
    ImageView ivEditProfilePic;
    Switch switchThreads;

    Uri selectedImageUri = null;
    String selectedGender = "Perempuan";
    String[] genderOptions = {"Laki-laki", "Perempuan"};

    ActivityResultLauncher<String> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    ivEditProfilePic.setImageURI(uri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etName           = findViewById(R.id.et_name);
        etUsername       = findViewById(R.id.et_username);
        etBio            = findViewById(R.id.et_bio);
        tvCancel         = findViewById(R.id.tv_cancel);
        tvDone           = findViewById(R.id.tv_done);
        ivEditProfilePic = findViewById(R.id.iv_edit_profile_pic);
        tvEditPhoto      = findViewById(R.id.tv_edit_photo);
        tvGenderValue    = findViewById(R.id.tv_gender_value);
        switchThreads    = findViewById(R.id.switch_threads);

        Intent intent = getIntent();
        if (intent != null) {
            etName.setText(intent.getStringExtra("name"));
            etUsername.setText(intent.getStringExtra("username"));
            etBio.setText(intent.getStringExtra("bio"));

            String gender = intent.getStringExtra("gender");
            if (gender != null && !gender.isEmpty()) selectedGender = gender;
            tvGenderValue.setText(selectedGender);

            String photoUri = intent.getStringExtra("photo_uri");
            if (photoUri != null) {
                selectedImageUri = Uri.parse(photoUri);
                ivEditProfilePic.setImageURI(selectedImageUri);
            }

            boolean showThreads = intent.getBooleanExtra("show_threads", true);
            switchThreads.setChecked(showThreads);
        }

        ivEditProfilePic.setOnClickListener(v -> pickImageLauncher.launch("image/*"));
        tvEditPhoto.setOnClickListener(v -> pickImageLauncher.launch("image/*"));
        findViewById(R.id.row_gender).setOnClickListener(v -> showGenderDialog());

        tvDone.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("name",         etName.getText().toString());
            resultIntent.putExtra("username",     etUsername.getText().toString());
            resultIntent.putExtra("bio",          etBio.getText().toString());
            resultIntent.putExtra("gender",       selectedGender);
            resultIntent.putExtra("show_threads", switchThreads.isChecked());

            if (selectedImageUri != null) {
                resultIntent.putExtra("photo_uri", selectedImageUri.toString());
            }

            setResult(RESULT_OK, resultIntent);
            finish();
        });

        tvCancel.setOnClickListener(v -> finish());
    }

    private void showGenderDialog() {
        int checkedItem = 0;
        for (int i = 0; i < genderOptions.length; i++) {
            if (genderOptions[i].equals(selectedGender)) { checkedItem = i; break; }
        }
        new AlertDialog.Builder(this)
                .setTitle("Pilih Jenis Kelamin")
                .setSingleChoiceItems(genderOptions, checkedItem, (dialog, which) -> {
                    selectedGender = genderOptions[which];
                    tvGenderValue.setText(selectedGender);
                    dialog.dismiss();
                })
                .setNegativeButton("Batal", null)
                .show();
    }
}