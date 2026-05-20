package com.example.praktikum5.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.*;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.praktikum5.DetailActivity;
import com.example.praktikum5.R;
import com.example.praktikum5.adapter.BookAdapter;
import com.example.praktikum5.model.Book;
import com.example.praktikum5.model.BookRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private BookRepository repo;
    private SearchView searchView;
    private Spinner spinnerGenre;
    private TextView tvEmpty;
    private ProgressBar progressBar;

    private String currentGenre = "All";
    private String currentQuery = "";

    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private Uri selectedEditImageUri = null;
    private ImageView currentEditImgPreview = null;

    private final ActivityResultLauncher<Intent> editImagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    if (uri != null) {
                        try {
                            requireContext().getContentResolver().takePersistableUriPermission(
                                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        selectedEditImageUri = uri;
                        if (currentEditImgPreview != null) {
                            currentEditImgPreview.setImageURI(uri);
                        }
                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        repo         = BookRepository.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView);
        searchView   = view.findViewById(R.id.searchView);
        spinnerGenre = view.findViewById(R.id.spinnerGenre);
        tvEmpty      = view.findViewById(R.id.tvEmpty);
        progressBar  = view.findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new BookAdapter(
                getContext(),
                repo.getAllBooks(),
                book -> {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("book_id", book.getId());
                    startActivity(intent);
                },
                book -> showBookOptionsDialog(book)
        );
        recyclerView.setAdapter(adapter);

        searchView.post(() -> applySearchViewTheme());
        setupSearch();
        setupGenreFilter();
        return view;
    }

    private void showBookOptionsDialog(Book book) {
        if (book.getCoverResId() != 0 && !book.hasCustomCover()) {
            Toast.makeText(getContext(),
                    "Buku bawaan tidak bisa diedit atau dihapus",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(requireContext())
                .setTitle(book.getTitle())
                .setItems(new String[]{"Edit Buku", "Hapus Buku"}, (dialog, which) -> {
                    if (which == 0) showEditDialog(book);
                    else            showDeleteDialog(book);
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    private void showEditDialog(Book book) {
        selectedEditImageUri = null;

        View dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_edit_book, null);

        ImageView imgPreview          = dialogView.findViewById(R.id.imgEditPreview);
        MaterialButton btnPickImage   = dialogView.findViewById(R.id.btnEditPickImage);
        TextInputEditText etTitle     = dialogView.findViewById(R.id.etEditTitle);
        TextInputEditText etAuthor    = dialogView.findViewById(R.id.etEditAuthor);
        TextInputEditText etYear      = dialogView.findViewById(R.id.etEditYear);
        TextInputEditText etBlurb     = dialogView.findViewById(R.id.etEditBlurb);
        AutoCompleteTextView spGenre  = dialogView.findViewById(R.id.spinnerEditGenre);

        currentEditImgPreview = imgPreview;

        if (book.hasCustomCover()) {
            imgPreview.setImageURI(book.getCoverUri());
        } else if (book.getCoverResId() != 0) {
            imgPreview.setImageResource(book.getCoverResId());
        } else {
            imgPreview.setImageResource(R.drawable.bg_image_placeholder);
        }

        etTitle.setText(book.getTitle());
        etAuthor.setText(book.getAuthor());
        etYear.setText(String.valueOf(book.getYear()));
        etBlurb.setText(book.getBlurb());

        String[] genres = {
                "Classic Fiction", "Dystopian", "Fantasy", "Science Fiction",
                "Adventure", "Non-Fiction", "Self-Help", "Coming of Age",
                "Thriller", "Classic Romance", "Psychology", "Memoir", "Horror", "Romance"
        };
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_dropdown_item_1line, genres);
        spGenre.setAdapter(genreAdapter);
        spGenre.setText(book.getGenre(), false);

        btnPickImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            editImagePickerLauncher.launch(intent);
        });

        new AlertDialog.Builder(requireContext())
                .setTitle("Edit Buku")
                .setView(dialogView)
                .setPositiveButton("Simpan", (dialog, which) -> {
                    String title   = etTitle.getText()  != null ? etTitle.getText().toString().trim()  : "";
                    String author  = etAuthor.getText() != null ? etAuthor.getText().toString().trim() : "";
                    String yearStr = etYear.getText()   != null ? etYear.getText().toString().trim()   : "";
                    String blurb   = etBlurb.getText()  != null ? etBlurb.getText().toString().trim()  : "";
                    String genre   = spGenre.getText().toString().trim();

                    if (title.isEmpty() || author.isEmpty() || yearStr.isEmpty() || blurb.isEmpty()) {
                        Toast.makeText(getContext(), "Semua field wajib diisi!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int year;
                    try { year = Integer.parseInt(yearStr); }
                    catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Tahun tidak valid!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    book.setTitle(title);
                    book.setAuthor(author);
                    book.setYear(year);
                    book.setBlurb(blurb);
                    book.setGenre(genre);
                    if (selectedEditImageUri != null) {
                        book.setCoverUri(selectedEditImageUri);
                    }

                    repo.updateBook(book);
                    Toast.makeText(getContext(), "Buku berhasil diperbarui!", Toast.LENGTH_SHORT).show();
                    filterBooks();
                })
                .setNegativeButton("Batal", (dialog, which) -> {
                    currentEditImgPreview = null;
                })
                .setOnDismissListener(dialog -> {
                    currentEditImgPreview = null;
                })
                .show();
    }

    private void showDeleteDialog(Book book) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Hapus Buku")
                .setMessage("Yakin ingin menghapus \"" + book.getTitle() + "\"?")
                .setPositiveButton("Hapus", (dialog, which) -> {
                    repo.deleteBook(book.getId());
                    Toast.makeText(getContext(), "Buku berhasil dihapus!", Toast.LENGTH_SHORT).show();
                    filterBooks();
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    private void applySearchViewTheme() {
        SharedPreferences prefs = requireContext().getSharedPreferences("settings_pref", 0);
        boolean isDark = prefs.getBoolean("dark_mode", false);
        int textColor = isDark ? Color.WHITE : Color.BLACK;
        int hintColor = isDark ? Color.parseColor("#888888") : Color.parseColor("#999999");
        int bgColor   = isDark ? Color.parseColor("#2C2C2E") : Color.WHITE;
        int iconColor = isDark ? Color.WHITE : Color.GRAY;

        searchView.setBackgroundColor(bgColor);
        View searchPlate = searchView.findViewById(
                getResources().getIdentifier("android:id/search_plate", null, null));
        if (searchPlate != null) searchPlate.setBackgroundColor(bgColor);
        View submitArea = searchView.findViewById(
                getResources().getIdentifier("android:id/submit_area", null, null));
        if (submitArea != null) submitArea.setBackgroundColor(bgColor);
        EditText et = searchView.findViewById(
                getResources().getIdentifier("android:id/search_src_text", null, null));
        if (et != null) {
            et.setTextColor(textColor);
            et.setHintTextColor(hintColor);
            et.setBackgroundColor(Color.TRANSPARENT);
        }
        ImageView iconSearch = searchView.findViewById(
                getResources().getIdentifier("android:id/search_mag_icon", null, null));
        if (iconSearch != null) iconSearch.setColorFilter(iconColor);
        ImageView iconClose = searchView.findViewById(
                getResources().getIdentifier("android:id/search_close_btn", null, null));
        if (iconClose != null) iconClose.setColorFilter(iconColor);
    }

    @Override
    public void onResume() {
        super.onResume();
        filterBooks();
    }

    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                currentQuery = query; filterBooks(); return true;
            }
            @Override public boolean onQueryTextChange(String newText) {
                currentQuery = newText; filterBooks(); return true;
            }
        });
    }

    private void setupGenreFilter() {
        List<String> genres = repo.getAllGenres();
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(
                requireContext(), R.layout.spinner_item, genres);
        genreAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerGenre.setAdapter(genreAdapter);
        spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
                currentGenre = genres.get(pos);
                if (v instanceof TextView) {
                    SharedPreferences p = requireContext().getSharedPreferences("settings_pref", 0);
                    boolean dark = p.getBoolean("dark_mode", false);
                    ((TextView) v).setTextColor(dark ? Color.WHITE : Color.BLACK);
                    ((TextView) v).setBackgroundColor(dark ? Color.parseColor("#2C2C2E") : Color.WHITE);
                }
                filterBooks();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void filterBooks() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.GONE);
        final String query = currentQuery;
        final String genre = currentGenre;
        new Thread(() -> {
            List<Book> filtered;
            if (query.isEmpty()) {
                filtered = repo.getBooksByGenre(genre);
            } else {
                filtered = repo.searchBooks(query);
                if (!genre.equals("All")) {
                    filtered.removeIf(b -> !b.getGenre().equals(genre));
                }
            }
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            final List<Book> result = filtered;
            mainHandler.post(() -> {
                if (getContext() == null) return;
                adapter.updateList(result);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                tvEmpty.setVisibility(result.isEmpty() ? View.VISIBLE : View.GONE);
            });
        }).start();
    }
}