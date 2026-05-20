package com.example.BukaMata;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> allBooks;
    private List<Book> filteredBooks;
    private SearchView searchView;
    private ProgressBar progressBar;  // TAMBAHAN TP4
    private String currentQuery = "";

    // TAMBAHAN TP4
    private ExecutorService executor = Executors.newSingleThreadExecutor(); // Membuat ExecutorService satu thread background yang siap nerima tugas dan jalanin satu-satu
    private Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.searchView);
        progressBar = view.findViewById(R.id.progressBar);  // TAMBAHAN TP4

        allBooks = BookRepository.getInstance().getAllBooks();
        filteredBooks = new ArrayList<>(allBooks);

        adapter = new BookAdapter(getContext(), filteredBooks, book -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("book_id", book.getId());
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        setupSearch();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        allBooks = BookRepository.getInstance().getAllBooks();
        applyFilter(currentQuery);
    }

    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentQuery = query;
                applyFilter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentQuery = newText;
                applyFilter(newText);
                return true;
            }
        });
    }

    // TAMBAHAN TP4 - applyFilter sekarang pakai background thread
    private void applyFilter(String query) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        executor.execute(() -> {
            // proses filter di background thread
            List<Book> result = new ArrayList<>();
            for (Book book : allBooks) {
                if (query.isEmpty() || book.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    result.add(book);
                }
                try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
            }

            handler.post(() -> {
                filteredBooks.clear();
                filteredBooks.addAll(result);
                adapter.updateList(filteredBooks);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            });
        });
    }
}