package com.example.clubpilot.Player;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event implements Serializable {

        private int id;
        private String description;
        private String date;
        private String nom;
        private String titol;
        private String imatge;
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

    public String getFormatData() {
        return formatData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getImatge() {
        return imatge;
    }

    public void setImatge(String imatge) {
        this.imatge = imatge;
    }

    public Event(int id, String description, String date, String nom, String titol, String imatge) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.nom = nom;
        this.titol = titol;
        this.imatge = imatge;
    }
}