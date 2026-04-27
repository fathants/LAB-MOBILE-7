package com.example.praktikum3.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.praktikum3.R;
import com.example.praktikum3.model.Book;
import com.example.praktikum3.model.BookRepository;
import com.google.android.material.textfield.TextInputEditText;

public class AddBookFragment extends Fragment {

    private TextInputEditText etTitle, etAuthor, etYear, etBlurb;
    private Spinner spinnerGenre;
    private ImageView imgPreview;
    private Uri selectedImageUri;
    private Button btnPickImage, btnSave;

    private final String[] genres = {
        "Classic Fiction", "Dystopian", "Fantasy", "Science Fiction",
        "Adventure", "Non-Fiction", "Self-Help", "Coming of Age",
        "Thriller", "Classic Romance", "Psychology", "Memoir", "Horror", "Romance"
    };

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
        registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                selectedImageUri = result.getData().getData();
                if (selectedImageUri != null) {
                    try {
                        requireContext().getContentResolver().takePersistableUriPermission(
                            selectedImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    imgPreview.setImageURI(selectedImageUri);
                    imgPreview.setVisibility(View.VISIBLE);
                }
            }
        });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        etTitle      = view.findViewById(R.id.etTitle);
        etAuthor     = view.findViewById(R.id.etAuthor);
        etYear       = view.findViewById(R.id.etYear);
        etBlurb      = view.findViewById(R.id.etBlurb);
        spinnerGenre = view.findViewById(R.id.spinnerGenre);
        imgPreview   = view.findViewById(R.id.imgPreview);
        btnPickImage = view.findViewById(R.id.btnPickImage);
        btnSave      = view.findViewById(R.id.btnSave);

        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(
            requireContext(), R.layout.spinner_item, genres);
        genreAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerGenre.setAdapter(genreAdapter);

        btnPickImage.setOnClickListener(v -> openGallery());
        btnSave.setOnClickListener(v -> saveBook());

        return view;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
            | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        imagePickerLauncher.launch(intent);
    }

    private void saveBook() {
        String title  = etTitle.getText()  != null ? etTitle.getText().toString().trim()  : "";
        String author = etAuthor.getText() != null ? etAuthor.getText().toString().trim() : "";
        String yearStr = etYear.getText()  != null ? etYear.getText().toString().trim()   : "";
        String blurb  = etBlurb.getText()  != null ? etBlurb.getText().toString().trim()  : "";
        String genre  = spinnerGenre.getSelectedItem().toString();

        if (title.isEmpty())  { etTitle.setError("Judul wajib diisi");   return; }
        if (author.isEmpty()) { etAuthor.setError("Penulis wajib diisi"); return; }
        if (yearStr.isEmpty()){ etYear.setError("Tahun wajib diisi");    return; }
        if (blurb.isEmpty())  { etBlurb.setError("Blurb wajib diisi");   return; }

        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            etYear.setError("Tahun tidak valid");
            return;
        }

        Book book = new Book(title, author, year, blurb, genre, selectedImageUri);
        BookRepository.getInstance().addBook(book);

        Toast.makeText(getContext(), "Buku berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
        clearForm();
    }

    private void clearForm() {
        etTitle.setText("");
        etAuthor.setText("");
        etYear.setText("");
        etBlurb.setText("");
        selectedImageUri = null;
        imgPreview.setVisibility(View.GONE);
        spinnerGenre.setSelection(0);
    }
}
