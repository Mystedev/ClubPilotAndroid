package com.example.clubpilot.Fan;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CardNew {
    private String title;
    private String club;
    private String description;
    private String date;
    private int image;
    private final String formatData = "dd/MM/yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(formatData);

    public CardNew(String club,String title, String description, int image) {
        this.club = club;
        this.title = title;
        this.description = description;
        this.image = image;
        this.date = sdf.format(new Date());
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

    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }
    public String getClub() {return club;}
}
