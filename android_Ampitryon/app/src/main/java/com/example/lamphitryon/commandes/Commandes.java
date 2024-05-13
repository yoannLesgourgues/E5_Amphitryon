package com.example.lamphitryon.commandes;

import com.example.lamphitryon.commandePlats.CommandeUnPlat;

import java.util.ArrayList;

public class Commandes {

    private ArrayList<Commande> commandes;

    public Commandes() {
        this.commandes = new ArrayList();
    }

    public ArrayList<Commande> getCommandes() {
        return commandes;
    }

    public Integer getNbCommandes() {
        return commandes.size();
    }

    public void ajouterCommande(Commande uneCommande) {
        ArrayList<Commande> listeCommandes = new ArrayList<Commande>();
        commandes.add(uneCommande);
    }

    public Commande getCommande(Integer unIndex) {
        return commandes.get(unIndex);

    }
}
