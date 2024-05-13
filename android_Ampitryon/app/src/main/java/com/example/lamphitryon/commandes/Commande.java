package com.example.lamphitryon.commandes;

import java.io.Serializable;

public class Commande implements Serializable {
    private int idCommande;
    private int numTable;
    private int idService;
    private String date_Service;
    private String heureCommande;
    private String etatCommande;

    public Commande(int idCommande, int numTable, int idService, String date_Service, String heureCommande, String etatCommande) {
        this.idCommande = idCommande;
        this.numTable = numTable;
        this.idService = idService;
        this.date_Service = date_Service;
        this.heureCommande = heureCommande;
        this.etatCommande = etatCommande;
    }

    public Commande() {
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getNumTable() {
        return numTable;
    }

    public void setNumTable(int numTable) {
        this.numTable = numTable;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getDate_Service() {
        return date_Service;
    }

    public void setDate_Service(String date_Service) {
        this.date_Service = date_Service;
    }

    public String getHeureCommande() {
        return heureCommande;
    }

    public void setHeureCommande(String heureCommande) {
        this.heureCommande = heureCommande;
    }

    public String getEtatCommande() {
        return etatCommande;
    }

    public void setEtatCommande(String etatCommande) {
        this.etatCommande = etatCommande;
    }
}
