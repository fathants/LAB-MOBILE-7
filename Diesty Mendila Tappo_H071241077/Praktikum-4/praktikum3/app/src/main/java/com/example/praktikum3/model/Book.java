package com.example.praktikum3.model;

import android.net.Uri;
import java.io.Serializable;

public class Book implements Serializable {
    private static int idCounter = 1;

    private int id;
    private String title;
    private String author;
    private int year;
    private String blurb;
    private String genre;
    private int coverResId;
    private Uri coverUri;
    private boolean liked;
    private long addedAt;

    private float userRating = 0f;
    private String userReview = "";
    private long likedAt = 0L;

    public Book(String title, String author, int year, String blurb,
                String genre, int coverResId) {
        this.id = idCounter++;
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.genre = genre;
        this.coverResId = coverResId;
        this.liked = false;
        this.addedAt = System.currentTimeMillis() - (idCounter * 10000L);
    }

    public Book(String title, String author, int year, String blurb,
                String genre, Uri coverUri) {
        this.id = idCounter++;
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.genre = genre;
        this.coverUri = coverUri;
        this.liked = false;
        this.addedAt = System.currentTimeMillis();
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getBlurb() { return blurb; }
    public String getGenre() { return genre; }
    public int getCoverResId() { return coverResId; }
    public Uri getCoverUri() { return coverUri; }
    public boolean isLiked() { return liked; }
    public long getAddedAt() { return addedAt; }
    public float getUserRating() { return userRating; }
    public String getUserReview() { return userReview; }
    public long getLikedAt() { return likedAt; }

    // Setters
    public void setLiked(boolean liked) {
        this.liked = liked;
        if (liked) {
            this.likedAt = System.currentTimeMillis();
        } else {
            this.likedAt = 0L;
        }
    }
    public void setUserRating(float userRating) { this.userRating = userRating; }
    public void setUserReview(String userReview) { this.userReview = userReview; }

    public boolean hasCustomCover() { return coverUri != null; }
    public boolean hasUserReview() { return userReview != null && !userReview.isEmpty(); }
    public boolean hasUserRating() { return userRating > 0f; }
}
