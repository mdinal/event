package com.example.teamalpha10.model;

public class Post {
    private String date;
    private String description;
    private String location;
    private String postid;
    private String postimage;
    private String publisher;
    private String tile;
    private String time;

    public Post() {
    }

    public Post(String date, String description, String location, String postid, String postimage, String publisher, String tile, String time) {
        this.date = date;
        this.description = description;
        this.location = location;
        this.postid = postid;
        this.postimage = postimage;
        this.publisher = publisher;
        this.tile = tile;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
