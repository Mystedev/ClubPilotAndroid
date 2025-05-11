package com.example.clubpilot.Fan;

import java.io.Serializable;

public class NewsData implements Serializable {
    private String id;
    private String autor;
    private String data;
    private String descripcio;
    private String titol;
    private String Imatge;
    private int clubId;          // 🔥 NUEVO
    private String clubName;     // 🔥 NUEVO

    public NewsData(String id) {
        this.id = id;
    }

    public NewsData(String autor, String data, String descripcio, String titol, int clubId, String clubName) {
        this.autor = autor;
        this.data = data;
        this.descripcio = descripcio;
        this.titol = titol;
        this.clubId = clubId;
        this.clubName = clubName;
    }

    public NewsData(String data, String autor, String titol, String descripcio) {
        this.data = data;
        this.autor = autor;
        this.titol = titol;
        this.descripcio = descripcio;
    }

    public NewsData(String id, String autor, String data, String descripcio, String titol, int clubId, String clubName,String imatge) {
        this.id = id;
        this.autor = autor;
        this.data = data;
        this.descripcio = descripcio;
        this.titol = titol;
        this.Imatge = imatge;
        this.clubId = clubId;
        this.clubName = clubName;
    }

    public String getId() {
        return id;
    }

    public String getAutor() {
        return autor;
    }

    public String getData() {
        return data;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public String getTitol() {
        return titol;
    }

    public int getClubId() {
        return clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public String getImatge() {
        return Imatge;
    }

    public void setImatge(String imatge) {
        Imatge = imatge;
    }
}
