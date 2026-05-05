package com.example.praktikum_3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class FavoritesFragment extends Fragment implements BookAdapter.OnBookClickListener {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> favoriteBooks;
    private ProgressBar progressBar;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewFavorites);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        updateList();

        return view;
    }

    private void updateList() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        if (recyclerView != null) recyclerView.setVisibility(View.GONE);

        executor.execute(() -> {
            // Simulasi proses pemuatan data di background thread
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            favoriteBooks = DataSource.getBooks().stream()
                    .filter(Book::isLiked)
                    .collect(Collectors.toList());

            handler.post(() -> {
                if (adapter == null) {
                    adapter = new BookAdapter(favoriteBooks, this);
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.updateData(favoriteBooks);
                }
                
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                if (recyclerView != null) recyclerView.setVisibility(View.VISIBLE);
            });
        });
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
        DataSource.updateBookLikeStatus(book.getId(), book.isLiked());
        updateList();
    }
}
