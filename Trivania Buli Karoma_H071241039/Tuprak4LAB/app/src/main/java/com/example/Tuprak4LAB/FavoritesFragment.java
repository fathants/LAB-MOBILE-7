package com.example.Tuprak4LAB;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {

    private RecyclerView rvFavorites;
    private BookAdapter bookAdapter;
    private ProgressBar progressBar;
    private TextView tvEmptyFavorites;

    // Executor dan Handler untuk Background Thread
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFavorites = view.findViewById(R.id.rv_favorites);
        progressBar = view.findViewById(R.id.progressBar);
        tvEmptyFavorites = view.findViewById(R.id.tv_empty_favorites);

        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inisialisasi dengan list kosong agar tidak langsung muncul data lama
        bookAdapter = new BookAdapter(new ArrayList<>());
        rvFavorites.setAdapter(bookAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Setiap kali fragment dibuka/kembali, jalankan loading
        loadFavoriteBooks();
    }

    private void loadFavoriteBooks() {
        // 1. Persiapan awal: Kosongkan list, sembunyikan pesan, tampilkan loading
        bookAdapter.setFilteredList(new ArrayList<>());
        tvEmptyFavorites.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        executor.execute(() -> {
            // --- PROSES BACKGROUND ---
            ArrayList<Book> favoriteList = new ArrayList<>();

            // Simulasi delay loading agar animasi ProgressBar terlihat (misal 1 detik)
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

            for (Book book : DataSource.books) {
                if (book.isLiked()) {
                    favoriteList.add(book);
                }
            }

            // --- KEMBALI KE UI THREAD ---
            handler.post(() -> {
                progressBar.setVisibility(View.GONE); // Sembunyikan loading

                if (favoriteList.isEmpty()) {
                    // Tampilkan pesan jika tidak ada favorit
                    tvEmptyFavorites.setVisibility(View.VISIBLE);
                } else {
                    // Tampilkan data ke RecyclerView
                    tvEmptyFavorites.setVisibility(View.GONE);
                    bookAdapter.setFilteredList(favoriteList);
                }
            });
        });
    }
}