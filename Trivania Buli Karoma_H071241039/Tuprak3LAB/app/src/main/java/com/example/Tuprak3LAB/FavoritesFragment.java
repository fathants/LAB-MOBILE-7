package com.example.Tuprak3LAB;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class  FavoritesFragment extends Fragment {

    private RecyclerView rvFavorites;
    private BookAdapter bookAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFavorites = view.findViewById(R.id.rv_favorites);
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        bookAdapter = new BookAdapter(new ArrayList<>());
        rvFavorites.setAdapter(bookAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavoriteBooks();
    }

    private void loadFavoriteBooks() {
        ArrayList<Book> favoriteList = new ArrayList<>();

        for (Book book : DataSource.books) {
            if (book.isLiked()) {
                favoriteList.add(book);
            }
        }

        bookAdapter.setFilteredList(favoriteList);
    }
}