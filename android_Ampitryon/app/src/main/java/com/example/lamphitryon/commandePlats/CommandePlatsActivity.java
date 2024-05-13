package com.example.lamphitryon.commandePlats;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lamphitryon.R;
import com.example.lamphitryon.commandes.Commande;
import com.example.lamphitryon.commandes.CreateCommandeActivity;
import com.example.lamphitryon.commandes.EditCommandeActivity;
import com.example.lamphitryon.commandes.MenuServeur;

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

public class CommandePlatsActivity extends AppCompatActivity {
    Commande commandeRefreshList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande_plats);
        try {
            Commande commande = (Commande) getIntent().getSerializableExtra("commande");
            String idUtilisateur = getIntent().getStringExtra("idUtilisateur");

            commandeRefreshList = commande;

            listeCommandePlats(commande);

            final Button buttonSupprimerCommande = findViewById(R.id.buttonSupprimerCommande);
            buttonSupprimerCommande.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        supprimerCommande(String.valueOf(commande.getIdCommande()));
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            final Button buttonReglerCommande = findViewById(R.id.buttonReglerCommande);
            buttonReglerCommande.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        reglerCommande(String.valueOf(commande.getIdCommande()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            final Button buttonModifierCommande = findViewById(R.id.buttonModifierCommande);
            buttonModifierCommande.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CommandePlatsActivity.this, EditCommandeActivity.class);
                    intent.putExtra("commande", commande);
                    intent.putExtra("idUtilisateur", idUtilisateur);
                    startActivity(intent);

                }
            });

            final Button buttonAjouterPlat = findViewById(R.id.buttonAjouterPlat);
            buttonAjouterPlat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CommandePlatsActivity.this, CreatePlatActivity.class);
                    intent.putExtra("commande", commande);
                    startActivity(intent);

                }
            });

            final Button buttonQuitterCommandePlats = (Button) findViewById(R.id.buttonQuitterCommandePlats);
            buttonQuitterCommandePlats.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommandePlatsActivity.this.finish();
                }
            });

        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume(){
        runOnUiThread(()->{
            super.onResume();
            try {
                listeCommandePlats(commandeRefreshList);
            } catch (IOException e) {
                Log.d("test",e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public void listeCommandePlats(Commande commande) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ArrayList arrayListCommandePlat = new ArrayList<String>();
        CommandePlats lesCommandePlats = new CommandePlats();

        RequestBody formBody = new FormBody.Builder()
                .add("idCommande", String.valueOf(commande.getIdCommande()))
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.1.72/php_Ampitryon/controleurs/afficherCommander.php")
                .post(formBody)
                .build();


        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public  void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();

                JSONArray jsonArrayCommandePlats = null;
                try {
                    jsonArrayCommandePlats = new JSONArray(responseStr);
                    for (int i = 0; i < jsonArrayCommandePlats.length(); i++) {
                        JSONObject jsonCommandeUnPlat = jsonArrayCommandePlats.getJSONObject(i);
                        CommandeUnPlat uneCommandePlat = new CommandeUnPlat(jsonCommandeUnPlat.getInt("IDPLAT"),
                                jsonCommandeUnPlat.getString("NOMPLAT"),
                                jsonCommandeUnPlat.getInt("QUANTITE"),
                                jsonCommandeUnPlat.getString("ETATPLAT"),
                                jsonCommandeUnPlat.getString("INFOSCOMPLEMENTAIRES"));
                        lesCommandePlats.ajouterCommandePlat(uneCommandePlat);
                    }
                    Log.d("test", lesCommandePlats.getCommandePlats().toString());
                    ListView listViewCommandes = findViewById(R.id.listViewCommandePlats);
                    listViewCommandes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

                    runOnUiThread(()->{
                        listViewCommandes.setAdapter(new CommandePlatsListAdapter(getApplicationContext(),
                                lesCommandePlats.getCommandePlats()));
                    });

                    listViewCommandes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // Récupérer l'ID de la commande à partir de la vue sélectionnée
                            CommandeUnPlat selectedPlat = (CommandeUnPlat) view.getTag();
                            Intent intent = new Intent(CommandePlatsActivity.this, EditPlatActivity.class);
                            intent.putExtra("plat", selectedPlat);
                            intent.putExtra("commande", commande);
                            startActivity(intent);
                        }
                    });

                } catch (JSONException e) {
                    Log.d("Test2", e.getMessage());
                }
            }
            public void onFailure(Call call, IOException e){
                Log.d("Test","erreur!!! connexion impossible");
            }
        });
    }

    public void supprimerCommande(String idCommande) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("idCommande", idCommande)
                .build();


        Request request = new Request.Builder()
                .url("http://192.168.1.72/php_Ampitryon/controleurs/supprimerCommande.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.d("Test","Reponse : " +responseStr);
                if (responseStr.compareTo("false") != 0) {
                    Log.d("test", "Commande supprimée");
                } else {
                    Log.d("test", "erreur dans la suppression de la commande");
                }
            }

            public void onFailure(Call call, IOException e) {
                Log.d("Test", "erreur!!! connexion impossible");
            }
        });
    }

    public void reglerCommande(String idCommande) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("idCommande", idCommande)
                .build();


        Request request = new Request.Builder()
                .url("http://192.168.1.72/php_Ampitryon/controleurs/reglerCommande.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.d("Test","Reponse : " +responseStr);
                if (responseStr.compareTo("false") != 0) {
                    Log.d("test", "Commande réglée");
                } else {
                    Log.d("test", "erreur dans le réglement de la commande");
                }
            }

            public void onFailure(Call call, IOException e) {
                Log.d("Test", "erreur!!! connexion impossible");
            }

        });
    }

}
