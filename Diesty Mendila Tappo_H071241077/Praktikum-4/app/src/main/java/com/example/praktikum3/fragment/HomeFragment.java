package com.example.praktikum3.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.praktikum3.DetailActivity;
import com.example.praktikum3.R;
import com.example.praktikum3.adapter.BookAdapter;
import com.example.praktikum3.model.Book;
import com.example.praktikum3.model.BookRepository;
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

        adapter = new BookAdapter(getContext(), repo.getAllBooks(), book -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("book_id", book.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        setupSearch();
        setupGenreFilter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        filterBooks();
    }

    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentQuery = query;
                filterBooks();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentQuery = newText;
                filterBooks();
                return true;
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
                filterBooks();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
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