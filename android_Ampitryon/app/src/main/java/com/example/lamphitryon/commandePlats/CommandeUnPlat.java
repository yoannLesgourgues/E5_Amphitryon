package com.example.lamphitryon.commandePlats;

import java.io.Serializable;

public class CommandeUnPlat implements Serializable {
    private int idPlat;
    private String etatPlat;
    private String infosComplementaires;
    private int Quantite;
    private String nomPlat;

    public CommandeUnPlat(int idPlat, String nomPlat, int quantite, String etatPlat, String infosComplementaires) {
        this.idPlat = idPlat;
        this.nomPlat = nomPlat;
        this.Quantite = quantite;
        this.etatPlat = etatPlat;
        this.infosComplementaires = infosComplementaires;
    }

    public int getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(int idPlat) {
        this.idPlat = idPlat;
    }

    public String getEtatPlat() {
        return etatPlat;
    }

    public void setEtatPlat(String etatPlat) {
        this.etatPlat = etatPlat;
    }

    public String getInfosComplementaires() {
        return infosComplementaires;
    }

    public void setInfosComplementaires(String infosComplementaires) {
        this.infosComplementaires = infosComplementaires;
    }

    public int getQuantite() {
        return Quantite;
    }

    public void setQuantite(int quantite) {
        Quantite = quantite;
    }

    public String getNomPlat() {
        return nomPlat;
    }

    public void setNomPlat(String nomPlat) {
        this.nomPlat = nomPlat;
    }
}

