package com.example.praktikum_2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rvHome = view.findViewById(R.id.rvHome);
        TextView tvEmptyMessage = view.findViewById(R.id.tvEmptyMessage);
        rvHome.setLayoutManager(new LinearLayoutManager(getContext()));

        SharedPreferences userPrefs = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String currentUser = userPrefs.getString("currentUser", "ira.fitri4343");

        List<Post> postList = new ArrayList<>();
        
        // Simulasikan: Hanya akun 'ira' yang sudah memiliki "mengikuti" atau postingan di home.
        // Untuk user baru, feed akan kosong sampai ada fitur follow.
        if (currentUser.equals("ira.fitri4343")) {
            postList.add(new Post("ira.fitri4343", R.drawable.profile_pic, R.drawable.post1, "Lagi lihat buku di gramed"));
            postList.add(new Post("user_unhas_1", R.drawable.user1_pic, R.drawable.user1_post, "Suasana kantin hari ini."));
            postList.add(new Post("user_unhas_2", R.drawable.user2_pic, R.drawable.user2_post, "Tugas Android menumpuk."));
            postList.add(new Post("user_unhas_3", R.drawable.user3_pic, R.drawable.user3_post, "Semangat mahasiswa SI!"));
            postList.add(new Post("user_unhas_4", R.drawable.user4_pic, R.drawable.user4_post, "Coding is fun."));
        }

        if (postList.isEmpty()) {
            rvHome.setVisibility(View.GONE);
            tvEmptyMessage.setVisibility(View.VISIBLE);
        } else {
            rvHome.setVisibility(View.VISIBLE);
            tvEmptyMessage.setVisibility(View.GONE);
            HomeAdapter adapter = new HomeAdapter(postList, post -> {
                ((MainActivity)getActivity()).navigateToProfile(post);
            });
            rvHome.setAdapter(adapter);
        }

        return view;
    }
}