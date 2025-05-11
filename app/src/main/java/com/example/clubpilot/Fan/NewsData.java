package com.example.clubpilot.Fan;

import java.io.Serializable;

// Classe que representa una notícia
public class NewsData implements Serializable {
    private String id;             // Identificador de la noticia
    private String autor;          // Autor de la notícia
    private String data;           // Data de publicacio
    private String descripcio;     // Descripcio del contingut
    private String titol;          // Títol de la notícia
    private String Imatge;         // Ruta o URL de la imatge
    private int clubId;            // Identificador del club
    private String clubName;       // Nom del club

    // Constructor amb només l'id
    public NewsData(String id) {
        this.id = id;
    }

    // Constructor amb data, autor, títol i descripcio
    public NewsData(String data, String autor, String titol, String descripcio) {
        this.data = data;
        this.autor = autor;
        this.titol = titol;
        this.descripcio = descripcio;
    }

    // Constructor complet amb tots els atributs
    public NewsData(String id, String autor, String data, String descripcio, String titol, int clubId, String clubName, String imatge) {
        this.id = id;
        this.autor = autor;
        this.data = data;
        this.descripcio = descripcio;
        this.titol = titol;
        this.Imatge = imatge;
        this.clubId = clubId;
        this.clubName = clubName;
    }

    // Constructor alternatiu amb id com a enter
    public NewsData(String autor, String data, String descripcio, String titol, int id, String clubname) {
        this.autor = autor;
        this.data = data;
        this.descripcio = descripcio;
        this.titol = titol;
        this.id = String.valueOf(id);
        this.clubName = clubname;
    }

    // Getters
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

    // Setter per a la imatge
    public void setImatge(String imatge) {
        Imatge = imatge;
    }
}
