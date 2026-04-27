package com.example.praktikum3.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
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

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private TextView tvEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        recyclerView = view.findViewById(R.id.recyclerFavorites);
        tvEmpty      = view.findViewById(R.id.tvEmpty);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadFavorites();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }

    private void loadFavorites() {
        List<Book> favorites = BookRepository.getInstance().getFavoriteBooks();
        if (adapter == null) {
            adapter = new BookAdapter(getContext(), favorites, book -> {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("book_id", book.getId());
                startActivity(intent);
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updateList(favorites);
        }
        tvEmpty.setVisibility(favorites.isEmpty() ? View.VISIBLE : View.GONE);
    }
}
