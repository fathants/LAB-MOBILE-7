package com.example.Tuprak3LAB;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment {

    private RecyclerView rvBooks;
    private BookAdapter bookAdapter;
    private SearchView searchView;
    private Spinner spinnerGenre;
    private Spinner spinnerRating;

    private String currentSearchText = "";
    private String currentSelectedGenre = "Semua Genre";
    private String currentSelectedRating = "Semua Rating";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvBooks = view.findViewById(R.id.rv_books);
        searchView = view.findViewById(R.id.search_view);
        spinnerGenre = view.findViewById(R.id.spinner_genre);
        spinnerRating = view.findViewById(R.id.spinner_rating);

        rvBooks.setLayoutManager(new LinearLayoutManager(getContext()));

        Collections.sort(DataSource.books, (book1, book2) -> book2.getYear().compareTo(book1.getYear()));

        bookAdapter = new BookAdapter(DataSource.books);
        rvBooks.setAdapter(bookAdapter);

        setupGenreSpinner();
        setupRatingSpinner();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentSearchText = newText;
                applyFilter();
                return true;
            }
        });
    }

    private void setupGenreSpinner() {
        ArrayList<String> genreList = new ArrayList<>();
        genreList.add("Semua Genre");

        for (Book book : DataSource.books) {
            if (!genreList.contains(book.getGenre())) {
                genreList.add(book.getGenre());
            }
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, genreList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(spinnerAdapter);

        spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentSelectedGenre = genreList.get(position);
                applyFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupRatingSpinner() {
        ArrayList<String> ratingList = new ArrayList<>();
        ratingList.add("Semua Rating");
        ratingList.add("Bintang 5");
        ratingList.add("Bintang 4");
        ratingList.add("Bintang 3");
        ratingList.add("Bintang 2");
        ratingList.add("Bintang 1");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ratingList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRating.setAdapter(spinnerAdapter);

        spinnerRating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentSelectedRating = ratingList.get(position);
                applyFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void applyFilter() {
        ArrayList<Book> filteredList = new ArrayList<>();

        for (Book book : DataSource.books) {
            boolean matchSearch = book.getTitle().toLowerCase().contains(currentSearchText.toLowerCase());
            boolean matchGenre = currentSelectedGenre.equals("Semua Genre") || book.getGenre().equals(currentSelectedGenre);
            boolean matchRating = true;

            if (!currentSelectedRating.equals("Semua Rating")) {
                int roundedRating = Math.round(book.getRating());
                String expectedRating = "Bintang " + roundedRating;
                matchRating = currentSelectedRating.equals(expectedRating);
            }

            if (matchSearch && matchGenre && matchRating) {
                filteredList.add(book);
            }
        }

        bookAdapter.setFilteredList(filteredList);
    }
}