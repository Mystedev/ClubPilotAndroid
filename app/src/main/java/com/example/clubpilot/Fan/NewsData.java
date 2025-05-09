package com.example.clubpilot.Fan;

import java.io.Serializable;

public class NewsData implements Serializable {
    private String id;
    private String autor;
    private String data;
    private String descripcio;
    private String titol;
    public NewsData(String id) {
        this.id = id;
    }

    public NewsData(String autor, String data, String descripcio, String titol) {
        this.autor = autor;
        this.data = data;
        this.descripcio = descripcio;
        this.titol = titol;
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
}
