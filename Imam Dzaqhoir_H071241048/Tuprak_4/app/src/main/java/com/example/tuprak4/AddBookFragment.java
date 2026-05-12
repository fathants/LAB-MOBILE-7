package com.example.tuprak4;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddBookFragment extends Fragment {

    private ImageView imgPreview;
    private EditText etTitle, etAuthor, etYear, etBlurb;
    private Button btnAdd;
    private Uri selectedImageUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        imgPreview = view.findViewById(R.id.img_add_preview);
        etTitle = view.findViewById(R.id.et_add_title);
        etAuthor = view.findViewById(R.id.et_add_author);
        etYear = view.findViewById(R.id.et_add_year);
        etBlurb = view.findViewById(R.id.et_add_blurb);
        btnAdd = view.findViewById(R.id.btn_add_book);

        // Image Picker Logic
        imgPreview.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        });

        // Add Button Logic
        btnAdd.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String author = etAuthor.getText().toString().trim();
            String yearStr = etYear.getText().toString().trim();
            String blurb = etBlurb.getText().toString().trim();

            if (title.isEmpty() || author.isEmpty() || yearStr.isEmpty() || blurb.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int year;
            try {
                year = Integer.parseInt(yearStr);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid year", Toast.LENGTH_SHORT).show();
                return;
            }

            String image = selectedImageUri != null 
                ? selectedImageUri.toString()
                : "https://picsum.photos/200?random=" + System.currentTimeMillis();

            BookModel newBook = new BookModel(title, author, year, blurb, image, false);

            // Add to DataDummy
            DataDummy.getBooks().add(0, newBook);

            // Clear Form
            etTitle.setText("");
            etAuthor.setText("");
            etYear.setText("");
            etBlurb.setText("");
            selectedImageUri = null;
            imgPreview.setImageResource(android.R.drawable.ic_menu_camera);

            Toast.makeText(getContext(), "Book added!", Toast.LENGTH_SHORT).show();

            // Auto switch to Home
            if (getActivity() != null) {
                BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation);
                if (bottomNav != null) {
                    bottomNav.setSelectedItemId(R.id.nav_home);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                selectedImageUri = imageUri;
                imgPreview.setImageURI(imageUri);
            }
        }
    }
}
