package com.example.teamalpha10.model;

public class User {
    private String id;
    private String username;
    private String email;
    private String fullname;
    private String imageURL;
    private String bio;

    public User(String id, String username, String email, String fullname, String imageURL, String bio) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.imageURL = imageURL;
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImageurl() {
        return imageURL;
    }

    public void setImageurl(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
