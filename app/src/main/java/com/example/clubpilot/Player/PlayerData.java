package com.example.clubpilot.Player;

public class PlayerData {
    private String idUsuari;
    private String disponibilitat;
    private String dorsal;
    private String posicio;

    // Constructor del la informacio del jugador
    public PlayerData(String idUsuari, String disponibilitat, String dorsal, String posicio) {
        this.idUsuari = idUsuari;
        this.disponibilitat = disponibilitat;
        this.dorsal = dorsal;
        this.posicio = posicio;
    }

    public String getIdUsuari() {
        return idUsuari;
    }

    public String getDisponibilitat() {
        return disponibilitat;
    }

    public String getDorsal() {
        return dorsal;
    }

    public String getPosicio() {
        return posicio;
    }
}
