package com.example.clubpilot.Fan;

import java.util.Date;

public class CardNew {
    private String title;
    private String description;
    private Date date;
    private int image;

    public CardNew(String title, String description, int image) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.date = new Date();
    }
    // Getters i setters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setImage(int image) {
        this.image = image;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public Date getDate() {
        return date;
    }
}
