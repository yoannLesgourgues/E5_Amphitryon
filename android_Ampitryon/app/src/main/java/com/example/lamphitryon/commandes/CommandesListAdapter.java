package com.example.lamphitryon.commandes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lamphitryon.R;
import com.example.lamphitryon.commandePlats.CommandePlatsListAdapter;
import com.example.lamphitryon.commandePlats.CommandeUnPlat;

import java.util.List;

public class CommandesListAdapter extends BaseAdapter {
    private List<Commande> listeCommandes;
    private LayoutInflater layoutInflater;
    private Context context;

    public CommandesListAdapter(Context aContext, List<Commande> listeCommandes) {
        this.context = aContext;
        this.listeCommandes = listeCommandes;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listeCommandes.size();
    }

    @Override
    public Object getItem(int position) {
        return listeCommandes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        CommandesListAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_item_commande, null);
            holder = new CommandesListAdapter.ViewHolder();
            holder.idCommande = convertView.findViewById(R.id.textView_idCommande);
            holder.numTable = convertView.findViewById(R.id.textView_numTable);
            holder.dateService = convertView.findViewById(R.id.textView_dateService);
            holder.heureCommande = convertView.findViewById(R.id.textView_heureCommande);
            holder.etatCommande = convertView.findViewById(R.id.textView_etatCommande);
            convertView.setTag(holder);
        } else {
            holder = (CommandesListAdapter.ViewHolder) convertView.getTag();
        }

        Commande commande = this.listeCommandes.get(position);
        holder.idCommande.setText(String.valueOf(commande.getIdCommande()));
        holder.numTable.setText(String.valueOf(commande.getNumTable()));
        holder.dateService.setText(commande.getDate_Service());
        holder.heureCommande.setText(commande.getHeureCommande());
        holder.etatCommande.setText(commande.getEtatCommande());

        convertView.setTag(commande);
        return convertView;
    }

    static class ViewHolder {
        TextView idCommande;
        TextView numTable;
        TextView dateService;
        TextView heureCommande;
        TextView etatCommande;
    }
}
