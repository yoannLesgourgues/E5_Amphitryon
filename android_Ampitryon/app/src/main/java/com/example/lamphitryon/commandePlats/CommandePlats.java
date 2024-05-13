package com.example.lamphitryon.commandePlats;

import java.util.ArrayList;

public class CommandePlats {
    private ArrayList<CommandeUnPlat> commandePlats;

    public CommandePlats() {
        this.commandePlats = new ArrayList();
    }

    public ArrayList<CommandeUnPlat> getCommandePlats() {
        return commandePlats;
    }

    public Integer getNbCommandePlats() {
        return commandePlats.size();
    }

    public void ajouterCommandePlat(CommandeUnPlat uneCommande) {
        ArrayList<CommandeUnPlat> listeCommandePlats = new ArrayList<CommandeUnPlat>();
        commandePlats.add(uneCommande);
    }

    public CommandeUnPlat getCommandePlat(Integer unIndex) {
        return commandePlats.get(unIndex);

    }
}