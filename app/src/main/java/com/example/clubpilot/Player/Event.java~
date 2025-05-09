package com.example.clubpilot.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
    private String date;
    private String description;
    private String nom;
    private final String formatData = "dd/MM/yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(formatData);

    public Event(String description,String data,String nom){
        this.description = description;
        this.date = data;
        this.nom = nom;
    }

    public Event( String description) {
        this.date = sdf.format(new Date());
        this.description = description;
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
    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}
}