package com.example.Tuprak2LAB;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public class ModelPost implements Parcelable {
    private final String username;
    private final String fullName;
    private final int    profileResId;
    private       int    imageResId;
    private       Uri    imageUri;
    private final String caption;
    private final String followers;
    private final String following;
    private final String followedBy;

    public ModelPost(String username, String fullName, int profileResId,
                     int imageResId, String caption,
                     String followers, String following, String followedBy) {
        this.username     = username;
        this.fullName     = fullName;
        this.profileResId = profileResId;
        this.imageResId   = imageResId;
        this.caption      = caption;
        this.followers    = followers;
        this.following    = following;
        this.followedBy   = followedBy;
    }

    public ModelPost(String username, String fullName, int profileResId,
                     int imageResId, String caption) {
        this.username     = username;
        this.fullName     = fullName;
        this.profileResId = profileResId;
        this.imageResId   = imageResId;
        this.caption      = caption;
        this.followers    = "746";
        this.following    = "186";
        this.followedBy   = "Diikuti oleh realmadrid, meidianatahir, dan 2 lainnya";
    }

    public ModelPost(String username, int profileResId, int imageResId, String caption) {
        this.username     = username;
        this.fullName     = username;
        this.profileResId = profileResId;
        this.imageResId   = imageResId;
        this.caption      = caption;
        this.followers    = "0";
        this.following    = "0";
        this.followedBy   = "";
    }

    // Constructor upload foto
    public ModelPost(String username, int profileResId, Uri imageUri, String caption) {
        this.username     = username;
        this.fullName     = username;
        this.profileResId = profileResId;
        this.imageUri     = imageUri;
        this.caption      = caption;
        this.followers    = "0";
        this.following    = "0";
        this.followedBy   = "";
    }

    protected ModelPost(Parcel in) {
        username     = in.readString();
        fullName     = in.readString();
        profileResId = in.readInt();
        imageResId   = in.readInt();
        imageUri     = in.readParcelable(Uri.class.getClassLoader());
        caption      = in.readString();
        followers    = in.readString();
        following    = in.readString();
        followedBy   = in.readString();
    }

    public static final Creator<ModelPost> CREATOR = new Creator<ModelPost>() {
        @Override public ModelPost createFromParcel(Parcel in) { return new ModelPost(in); }
        @Override public ModelPost[] newArray(int size)        { return new ModelPost[size]; }
    };

    @Override public int describeContents() { return 0; }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(fullName);
        dest.writeInt(profileResId);
        dest.writeInt(imageResId);
        dest.writeParcelable(imageUri, flags);
        dest.writeString(caption);
        dest.writeString(followers);
        dest.writeString(following);
        dest.writeString(followedBy);
    }

    public String getUsername()    { return username; }
    public String getFullName()    { return fullName; }
    public int    getProfileResId(){ return profileResId; }
    public int    getImageResId()  { return imageResId; }
    public Uri    getImageUri()    { return imageUri; }
    public String getCaption()     { return caption; }
    public String getFollowers()   { return followers; }
    public String getFollowing()   { return following; }
    public String getFollowedBy()  { return followedBy; }
}