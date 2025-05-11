package com.example.clubpilot.Player;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

// Classe que representa un esdeveniment
public class Event implements Serializable {

    private int id;                  // Identificador de l'esdeveniment
    private String description;     // Descripció de l'esdeveniment
    private String date;            // Data de l'esdeveniment
    private String nom;             // Nom del creador o relacionat
    private String titol;           // Títol de l'esdeveniment
    private String imatge;          // Ruta o URL de la imatge

    // Format de la data per defecte
    private final String formatData = "dd/MM/yyyy";

    // Formatador de dates
    private SimpleDateFormat sdf = new SimpleDateFormat(formatData);

    // Constructor amb descripció, data i nom
    public Event(String description, String data, String nom){
        this.description = description;
        this.date = data;
        this.nom = nom;
    }

    // Constructor amb només descripció. Assigna la data actual automàticament
    public Event(String description) {
        this.date = sdf.format(new Date());
        this.description = description;
    }

    // Getters i setters
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

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

    // Constructor amb tots els atributs
    public Event(int id, String description, String date, String nom, String titol, String imatge) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.nom = nom;
        this.titol = titol;
        this.imatge = imatge;
    }
}
