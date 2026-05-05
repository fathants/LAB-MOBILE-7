package com.example.praktikum_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment implements BookAdapter.OnBookClickListener {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> allBooks;
    private String currentQuery = "";
    private String selectedGenre = "All";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewBooks);
        SearchView searchView = view.findViewById(R.id.searchView);
        ChipGroup chipGroupGenre = view.findViewById(R.id.chipGroupGenre);

        allBooks = DataSource.getBooks();
        adapter = new BookAdapter(new ArrayList<>(allBooks), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        setupSearchView(searchView);
        setupGenreFilter(chipGroupGenre);

        return view;
    }

    private void setupSearchView(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentQuery = newText;
                filterBooks();
                return true;
            }
        });
    }

    private void setupGenreFilter(ChipGroup chipGroupGenre) {
        Set<String> genres = new HashSet<>();
        genres.add("All");
        for (Book book : allBooks) {
            genres.add(book.getGenre());
        }

        chipGroupGenre.removeAllViews();
        for (String genre : genres) {
            Chip chip = new Chip(getContext());
            chip.setText(genre);
            chip.setCheckable(true);
            if (genre.equals("All")) chip.setChecked(true);
            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedGenre = genre;
                    filterBooks();
                }
            });
            chipGroupGenre.addView(chip);
        }
    }

    private void filterBooks() {
        List<Book> filteredList = allBooks.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(currentQuery.toLowerCase()))
                .filter(book -> selectedGenre.equals("All") || book.getGenre().equalsIgnoreCase(selectedGenre))
                .collect(Collectors.toList());
        adapter.updateData(filteredList);
    }

    @Override
    public void onResume() {
        super.onResume();
        allBooks = DataSource.getBooks();
        filterBooks();
    }

    @Override
    public void onBookClick(Book book) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("BOOK_EXTRA", book);
        startActivity(intent);
    }

    @Override
    public void onLikeClick(Book book, int position) {
        book.setLiked(!book.isLiked());
        // Menggunakan update data item spesifik agar lebih efisien
        adapter.notifyItemChanged(position);
    }
}
