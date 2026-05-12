package com.example.praktikum_3;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String id;
    private String title;
    private String author;
    private int year;
    private String blurb;
    private String genre;
    private float rating;
    private String imageUri; // Can be a resource name or a file URI
    private boolean isLiked;

    public Book(String id, String title, String author, int year, String blurb, String genre, float rating, String imageUri, boolean isLiked) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.genre = genre;
        this.rating = rating;
        this.imageUri = imageUri;
        this.isLiked = isLiked;
    }

    protected Book(Parcel in) {
        id = in.readString();
        title = in.readString();
        author = in.readString();
        year = in.readInt();
        blurb = in.readString();
        genre = in.readString();
        rating = in.readFloat();
        imageUri = in.readString();
        isLiked = in.readByte() != 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    // Getters and Setters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getBlurb() { return blurb; }
    public String getGenre() { return genre; }
    public float getRating() { return rating; }
    public String getImageUri() { return imageUri; }
    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeInt(year);
        dest.writeString(blurb);
        dest.writeString(genre);
        dest.writeFloat(rating);
        dest.writeString(imageUri);
        dest.writeByte((byte) (isLiked ? 1 : 0));
    }
}
