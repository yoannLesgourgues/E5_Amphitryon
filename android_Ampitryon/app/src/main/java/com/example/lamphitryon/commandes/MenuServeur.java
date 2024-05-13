package com.example.lamphitryon.commandes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lamphitryon.MainActivity;
import com.example.lamphitryon.R;
import com.example.lamphitryon.commandePlats.CommandePlatsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MenuServeur extends AppCompatActivity {

    String utilisateurId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serveur);
        try {
            final JSONObject unUtilisateur = new JSONObject(getIntent().getStringExtra("UTILISATEUR"));
            int idUtilisateur = unUtilisateur.getInt("IDUTILISATEUR");


            listeCommande(String.valueOf(idUtilisateur));

            utilisateurId = String.valueOf(idUtilisateur);
            final Button buttonCreerCommande = findViewById(R.id.buttonCreerCommande);
            buttonCreerCommande.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuServeur.this, CreateCommandeActivity.class);
                    intent.putExtra("idUtilisateur", String.valueOf(idUtilisateur));
                    startActivity(intent);

                }
            });

            final Button buttonQuitterMenuServeur = (Button) findViewById(R.id.buttonQuitterServeur);
            buttonQuitterMenuServeur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MenuServeur.this.finish();
                }
            });

        } catch (JSONException | IOException e) {
            e.printStackTrace();
            Log.d("test",e.getMessage());
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        try {
            listeCommande(utilisateurId);
        } catch (IOException e) {
            Log.d("test",e.getMessage());
            e.printStackTrace();
        }

    }

    public void listeCommande(String idUtilisateur) throws IOException {

        OkHttpClient client = new OkHttpClient();
        ArrayList arrayListCommande = new ArrayList<String>();
        Commandes lesCommandes = new Commandes();

        RequestBody formBody = new FormBody.Builder()
                .add("idUtilisateur", idUtilisateur)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.72/php_Ampitryon/controleurs/afficherCommandes.php")
                .post(formBody)
                .build();


        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.d("test",responseStr);
                JSONArray jsonArrayCommande = null;
                try {
                    jsonArrayCommande = new JSONArray(responseStr);
                    for (int i = 0; i < jsonArrayCommande.length(); i++) {
                        JSONObject jsonCommande = jsonArrayCommande.getJSONObject(i);
                        Commande uneCommande = new Commande(jsonCommande.getInt("IDCOMMANDE"),
                                jsonCommande.getInt("NUMTABLE"),
                                jsonCommande.getInt("IDSERVICE"),
                                jsonCommande.getString("DATE_SERVICE"),
                                jsonCommande.getString("HEURECOMMANDE"),
                                jsonCommande.getString("ETATCOMMANDE"));
                        lesCommandes.ajouterCommande(uneCommande);
                    }
                    ListView listViewCommandes = findViewById(R.id.listViewCommandes);
                    listViewCommandes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

                    runOnUiThread(()->{
                        listViewCommandes.setAdapter(new CommandesListAdapter(getApplicationContext(),
                                lesCommandes.getCommandes()));
                    });

                    listViewCommandes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // Récupérer l'ID de la commande à partir de la vue sélectionnée
                            Commande selectedCommande = (Commande) view.getTag();
                            Intent intent = new Intent(MenuServeur.this, CommandePlatsActivity.class);
                            intent.putExtra("commande", selectedCommande);
                            intent.putExtra("idUtilisateur", idUtilisateur);
                            startActivity(intent);
                        }
                    });

                } catch (JSONException e) {
                    Log.d("Test1", e.getMessage());
                }
            }
            public void onFailure(Call call, IOException e){
                Log.d("Test","erreur!!! connexion impossible");
            }
        });
    }
}
