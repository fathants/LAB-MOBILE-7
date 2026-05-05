package com.example.praktikum_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FavoritesFragment extends Fragment implements BookAdapter.OnBookClickListener {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> favoriteBooks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        updateList();

        return view;
    }

    private void updateList() {
        favoriteBooks = DataSource.getBooks().stream()
                .filter(Book::isLiked)
                .collect(Collectors.toList());

        if (adapter == null) {
            adapter = new BookAdapter(favoriteBooks, this);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updateData(favoriteBooks);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
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
        updateList();
    }
}
