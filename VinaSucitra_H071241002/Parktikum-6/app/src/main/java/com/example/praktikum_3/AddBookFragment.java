package com.example.praktikum_3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

public class AddBookFragment extends Fragment {

    private ImageView ivCover;
    private TextInputEditText etTitle, etAuthor, etYear, etBlurb;
    private AutoCompleteTextView etGenre;
    private RatingBar ratingBar;
    private MaterialButton btnChooseImage, btnAdd;
    private Uri selectedImageUri;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    ivCover.setImageURI(selectedImageUri);
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        ivCover = view.findViewById(R.id.ivCover);
        etTitle = view.findViewById(R.id.etTitle);
        etAuthor = view.findViewById(R.id.etAuthor);
        etYear = view.findViewById(R.id.etYear);
        etGenre = view.findViewById(R.id.etGenre);
        etBlurb = view.findViewById(R.id.etBlurb);
        ratingBar = view.findViewById(R.id.ratingBar);
        btnChooseImage = view.findViewById(R.id.btnChooseImage);
        btnAdd = view.findViewById(R.id.btnAdd);

        setupGenreDropdown();

        btnChooseImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(intent);
        });

        btnAdd.setOnClickListener(v -> saveBook());

        return view;
    }

    private void setupGenreDropdown() {
        String[] genres = {"Drama", "Historical", "Short Story", "Romance", "Inspirational", "Fantasy", "Classic", "Sci-Fi", "Religious"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, genres);
        etGenre.setAdapter(adapter);
    }

    private void saveBook() {
        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();
        String yearStr = etYear.getText().toString().trim();
        String genre = etGenre.getText().toString().trim();
        String blurb = etBlurb.getText().toString().trim();
        float rating = ratingBar.getRating();

        if (title.isEmpty() || author.isEmpty() || yearStr.isEmpty() || genre.isEmpty() || blurb.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int year = Integer.parseInt(yearStr);
        String imageUri = selectedImageUri != null ? selectedImageUri.toString() : "laskar_pelangi";

        Book newBook = new Book(UUID.randomUUID().toString(), title, author, year, blurb, genre, rating, imageUri, false);
        DataSource.addBook(newBook);

        Toast.makeText(getContext(), "Book added successfully", Toast.LENGTH_SHORT).show();
        
        // Clear form
        etTitle.setText("");
        etAuthor.setText("");
        etYear.setText("");
        etGenre.setText("");
        etBlurb.setText("");
        ratingBar.setRating(0);
        ivCover.setImageResource(android.R.drawable.ic_menu_gallery);
        selectedImageUri = null;

        // Navigate back to Home
        if (getActivity() != null) {
            ((MainActivity) getActivity()).findViewById(R.id.nav_home).performClick();
        }
    }
}
