package com.github.sgt_kittyKat.blog.database.models;

import java.util.Date;

public class Notification {
    private int id;
    private String text;
    private User user;
    public Notification(String text, User user) {
        this.id = 0;
        this.text = text;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
