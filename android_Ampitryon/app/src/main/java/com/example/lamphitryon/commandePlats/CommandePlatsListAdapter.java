package com.example.lamphitryon.commandePlats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lamphitryon.R;

import java.util.List;

public class CommandePlatsListAdapter extends BaseAdapter {
    private List<CommandeUnPlat> listeCommandePlats;
    private LayoutInflater layoutInflater;
    private Context context;

    public CommandePlatsListAdapter(Context aContext, List<CommandeUnPlat> listeCommandePlats) {
        this.context = aContext;
        this.listeCommandePlats = listeCommandePlats;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listeCommandePlats.size();
    }

    @Override
    public Object getItem(int position) {
        return listeCommandePlats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_item_commande_un_plat, null);
            holder = new ViewHolder();
            holder.nomPlatView = convertView.findViewById(R.id.textView_nomPlat);
            holder.quantiteView = convertView.findViewById(R.id.textView_quantite);
            holder.etatPlatView = convertView.findViewById(R.id.textView_etatPlat);
            holder.infosCompView = convertView.findViewById(R.id.textView_infosComp);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CommandeUnPlat commandeUnPlat = this.listeCommandePlats.get(position);
        holder.nomPlatView.setText(commandeUnPlat.getNomPlat());
        holder.quantiteView.setText(String.valueOf(commandeUnPlat.getQuantite()));
        holder.etatPlatView.setText(commandeUnPlat.getEtatPlat());
        holder.infosCompView.setText(commandeUnPlat.getInfosComplementaires());

        convertView.setTag(commandeUnPlat);
        return convertView;
    }

    static class ViewHolder {
            TextView nomPlatView;
            TextView quantiteView;
            TextView etatPlatView;
            TextView infosCompView;
    }


}
