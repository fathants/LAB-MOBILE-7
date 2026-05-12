package com.example.praktikum_2;

public class Post {
    private String username;
    private int profileImage;
    private int postImage;
    private String caption;
    private String imageUri;

    public Post(String username, int profileImage, int postImage, String caption) {
        this.username = username;
        this.profileImage = profileImage;
        this.postImage = postImage;
        this.caption = caption;
    }

    public String getUsername() { return username; }
    public int getProfileImage() { return profileImage; }
    public int getPostImage() { return postImage; }
    public String getCaption() { return caption; }
    public String getImageUri() { return imageUri; }
    public void setImageUri(String imageUri) { this.imageUri = imageUri; }
}
