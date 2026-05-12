package com.example.tuprak5;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsManager {
    private static final String PREF_NAME = "ThreadsClonePrefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_POSTS_DATA = "posts_data";
    private static final String KEY_DARK_MODE = "dark_mode";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SharedPrefsManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setUsername(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME, "");
    }

    public void setPostsData(String postsJson) {
        editor.putString(KEY_POSTS_DATA, postsJson);
        editor.apply();
    }

    public String getPostsData() {
        return pref.getString(KEY_POSTS_DATA, "[]");
    }

    public void setDarkMode(boolean isDarkMode) {
        editor.putBoolean(KEY_DARK_MODE, isDarkMode);
        editor.apply();
    }

    public boolean isDarkMode() {
        return pref.getBoolean(KEY_DARK_MODE, false);
    }

    public void saveUser(String username, String password) {
        editor.putString("user_" + username, password);
        editor.apply();
    }

    public boolean checkUser(String username, String password) {
        String savedPassword = pref.getString("user_" + username, null);
        return savedPassword != null && savedPassword.equals(password);
    }

    public boolean isUserExists(String username) {
        return pref.contains("user_" + username);
    }

    public void initDummyData() {
        if (getPostsData().equals("[]")) {
            String dummy = "[" +
                "{\"username\":\"Zeleo\",\"content\":\"Selamat datang di Threads Clone! Ini adalah aplikasi modern dengan SharedPreferences.\",\"time\":\"10:30\"}," +
                "{\"username\":\"DeepSeek\",\"content\":\"Desainnya sangat minimalis dan bersih, suka banget!\",\"time\":\"11:45\"}," +
                "{\"username\":\"AndroidDev\",\"content\":\"Jangan lupa cek mode gelap di pengaturan ya!\",\"time\":\"13:20\"}" +
                "]";
            setPostsData(dummy);
        }
    }

    public void clearSession() {
        editor.remove(KEY_IS_LOGGED_IN);
        editor.remove(KEY_USERNAME);
        editor.apply();
    }

    public void clearAll() {
        editor.clear();
        editor.apply();
    }
}
