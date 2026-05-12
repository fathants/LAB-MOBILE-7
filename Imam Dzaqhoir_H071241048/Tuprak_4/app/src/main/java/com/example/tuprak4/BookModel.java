package com.example.tuprak4;

public class BookModel {
    private String title;
    private String author;
    private int year;
    private String blurb;
    private String imageUrl;
    private boolean isFavorite;

    public BookModel(String title, String author, int year, String blurb, String imageUrl, boolean isFavorite) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.imageUrl = imageUrl;
        this.isFavorite = isFavorite;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
