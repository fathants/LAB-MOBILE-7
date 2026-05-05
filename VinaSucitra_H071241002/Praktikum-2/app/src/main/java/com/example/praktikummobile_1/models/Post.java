package com.example.praktikummobile_1.models;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private final int profileImage;
    private final String username;
    private final int postImage;
    private final String postImageUri;
    private final String caption;

    public static List<Post> profilePosts = new ArrayList<>();

    public Post(int profileImage, String username, int postImage, String caption) {
        this.profileImage = profileImage;
        this.username = username;
        this.postImage = postImage;
        this.postImageUri = null;
        this.caption = caption;
    }

    public Post(int profileImage, String username, String postImageUri, String caption) {
        this.profileImage = profileImage;
        this.username = username;
        this.postImage = 0;
        this.postImageUri = postImageUri;
        this.caption = caption;
    }

    public int getProfileImage() { return profileImage; }
    public String getUsername() { return username; }
    public int getPostImage() { return postImage; }
    public String getPostImageUri() { return postImageUri; }
    public String getCaption() { return caption; }
}
