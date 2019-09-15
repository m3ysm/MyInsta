package com.meysam.myinsta.Models;

public class postItem {

    private String id;
    private String user_id;
    private String des;
    private String image;
    private String date;
    private String comment;
    private String commentCount;


    public String getCommentCount() {
        return commentCount;
    }

    public String getComment() {
        return comment;
    }

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getDes() {
        return des;
    }

    public String getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }
}
