package com.example.lamphitryon.commandePlats;

public class Plat {
    private String id;
    private String nom;

    public Plat(String id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return nom;
    }
}
