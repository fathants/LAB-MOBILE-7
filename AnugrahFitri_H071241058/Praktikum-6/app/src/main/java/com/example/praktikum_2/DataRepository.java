package com.example.praktikum_2;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    public static List<Post> userPosts = new ArrayList<>();

    static {
        userPosts.add(new Post("ira.fitri4343", R.drawable.profile_pic, R.drawable.post1, "Lagi lihat buku gramed"));
        userPosts.add(new Post("ira.fitri4343", R.drawable.profile_pic, R.drawable.post2, "Bersama adek"));
        userPosts.add(new Post("ira.fitri4343", R.drawable.profile_pic, R.drawable.post3, "Semangat 2024"));
        userPosts.add(new Post("ira.fitri4343", R.drawable.profile_pic, R.drawable.post4, "Bali"));
        userPosts.add(new Post("ira.fitri4343", R.drawable.profile_pic, R.drawable.post5, "Italia"));
    }
}
