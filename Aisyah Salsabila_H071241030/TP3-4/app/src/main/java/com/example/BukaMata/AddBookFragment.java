package com.example.BukaMata;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddBookFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etTitle, etAuthor, etYear, etBlurb;
    private Spinner spinnerGenre;
    private ImageView ivPreview;
    private Button btnPickImage, btnSave;
    private Uri selectedImageUri = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        etTitle = view.findViewById(R.id.etTitle);
        etAuthor = view.findViewById(R.id.etAuthor);
        etYear = view.findViewById(R.id.etYear);
        etBlurb = view.findViewById(R.id.etBlurb);
        spinnerGenre = view.findViewById(R.id.spinnerGenreAdd);
        ivPreview = view.findViewById(R.id.ivPreview);
        btnPickImage = view.findViewById(R.id.btnPickImage);
        btnSave = view.findViewById(R.id.btnSave);

        String[] genres = {"Fantasy", "Classic", "Dystopia", "Romance", "Thriller",
                "Sci-Fi", "Non-Fiction", "Self-Help", "Fiction", "Philosophy",
                "Coming of Age", "Mystery", "Horror", "Biography"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, genres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(adapter);

        btnPickImage.setOnClickListener(v -> openGallery());
        btnSave.setOnClickListener(v -> saveBook());

        return view;
    }

    private void openGallery() {
        if (getActivity() == null) return;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            ivPreview.setImageURI(selectedImageUri);
        }
    }

    private void saveBook() {
        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();
        String yearStr = etYear.getText().toString().trim();
        String blurb = etBlurb.getText().toString().trim();
        String genre = spinnerGenre.getSelectedItem().toString();

        if (title.isEmpty() || author.isEmpty() || yearStr.isEmpty() || blurb.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all required fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid year format!", Toast.LENGTH_SHORT).show();
            return;
        }

        Book newBook;
        if (selectedImageUri != null) {
            newBook = new Book(title, author, year, blurb, genre, 0f, "", selectedImageUri);
        } else {
            newBook = new Book(title, author, year, blurb, genre, 0f, "", R.drawable.cover_placeholder);
        }

        BookRepository.getInstance().addBook(newBook);
        Toast.makeText(getContext(), "Book added successfully!", Toast.LENGTH_SHORT).show();
        clearForm();
    }

    private void clearForm() {
        etTitle.setText("");
        etAuthor.setText("");
        etYear.setText("");
        etBlurb.setText("");
        selectedImageUri = null;
        ivPreview.setImageResource(R.drawable.cover_placeholder);
    }
}
