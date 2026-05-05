package com.example.praktikummobile_1.models;

public class Story {
    private int storyImage;
    private String title;

    public Story(int storyImage, String title) {
        this.storyImage = storyImage;
        this.title = title;
    }

    public int getStoryImage() { return storyImage; }
    public String getTitle() { return title; }
}
