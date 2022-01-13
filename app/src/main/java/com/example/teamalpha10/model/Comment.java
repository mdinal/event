package com.example.teamalpha10.model;

public class Comment {
    private String comment;
    private String publusher;
    private String commentid;


    public Comment() {
    }

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public Comment(String comment, String publusher, String commentid) {
        this.comment = comment;
        this.publusher = publusher;
        this.commentid=commentid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPublusher() {
        return publusher;
    }

    public void setPublusher(String publusher) {
        this.publusher = publusher;
    }
}
