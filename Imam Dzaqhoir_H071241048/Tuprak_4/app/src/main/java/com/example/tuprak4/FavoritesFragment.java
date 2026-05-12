package com.example.tuprak4;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private ArrayList<BookModel> favoriteBooks;
    private ProgressBar progressBarFav;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = view.findViewById(R.id.rv_books_favorites);
        progressBarFav = view.findViewById(R.id.progressBarFav);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        favoriteBooks = new ArrayList<>();
        adapter = new BookAdapter(getContext(), favoriteBooks);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }

    private void loadFavorites() {
        if (favoriteBooks != null && adapter != null) {
            progressBarFav.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

            executorService.execute(() -> {
                try {
                    // Simulate delay to show loading animation
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ArrayList<BookModel> tempFavoriteBooks = new ArrayList<>();
                ArrayList<BookModel> allBooks = DataDummy.getBooks();
                for (BookModel book : allBooks) {
                    if (book.isFavorite()) {
                        tempFavoriteBooks.add(book);
                    }
                }

                handler.post(() -> {
                    favoriteBooks.clear();
                    favoriteBooks.addAll(tempFavoriteBooks);
                    adapter.notifyDataSetChanged();
                    
                    progressBarFav.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                });
            });
        }
    }
}
