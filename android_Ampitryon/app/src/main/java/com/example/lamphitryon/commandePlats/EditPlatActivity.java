package com.example.lamphitryon.commandePlats;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lamphitryon.R;
import com.example.lamphitryon.commandes.Commande;
import com.example.lamphitryon.commandes.CreateCommandeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditPlatActivity extends AppCompatActivity {

    private Spinner spinnerPlat;
    private EditText editTextInfosComp;
    private EditText editTextQuantite;
    private Spinner spinnerEtat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plat);

        try {
            Commande commande = (Commande) getIntent().getSerializableExtra("commande");
            CommandeUnPlat plat = (CommandeUnPlat) getIntent().getSerializableExtra("plat");

            spinnerPlat = findViewById(R.id.spinnerPlatEdit);
            spinnerEtat = findViewById(R.id.spinnerEtatEdit);
            editTextInfosComp = findViewById(R.id.editTextInfosCompEdit);
            editTextQuantite = findViewById(R.id.editTextQuantiteEdit);



            //Remplir le spinner des plats avec l'id Service et la date
            remplirElementsActivity(String.valueOf(commande.getIdService()), commande.getDate_Service(), plat);

            final Button buttonValiderEditPlat = findViewById(R.id.buttonValiderEditPlat);
            buttonValiderEditPlat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Plat selectedPlat = (Plat) spinnerPlat.getSelectedItem();
                    String selectedPlatId = selectedPlat.getId();
                    String textInfosComp = editTextInfosComp.getText().toString();
                    String textQuantite = editTextQuantite.getText().toString();
                    String selectedEtat = spinnerEtat.getSelectedItem().toString();
                    try {
                        modifierPlat(String.valueOf(plat.getIdPlat()), String.valueOf(commande.getIdCommande()), selectedPlatId, selectedEtat, textInfosComp, textQuantite);
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            final Button buttonSupprimerPlat = findViewById(R.id.buttonSupprimerPlat);
            buttonSupprimerPlat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        supprimerPlat(String.valueOf(plat.getIdPlat()), String.valueOf(commande.getIdCommande()));
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            final Button buttonQuitterEditPlat = (Button) findViewById(R.id.buttonQuitterEditPlat);
            buttonQuitterEditPlat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditPlatActivity.this.finish();
                }
            });

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void remplirElementsActivity(String idService, String dateService, CommandeUnPlat plat) throws IOException {
        //REMPLIR LE SPINNER (LISTE DEROULANTE) DES SERVICES
        spinnerPlat = findViewById(R.id.spinnerPlatEdit);
        spinnerEtat = findViewById(R.id.spinnerEtatEdit);
        editTextInfosComp = findViewById(R.id.editTextInfosCompEdit);
        editTextQuantite = findViewById(R.id.editTextQuantiteEdit);

        //Remplissage du texte des editText avec les valeurs du plats sélectionné
        editTextInfosComp.setText(plat.getInfosComplementaires());
        editTextQuantite.setText(String.valueOf(plat.getQuantite()));

        //Remplissage du texte du spinner des états + sélection avec la valeur du plat sélectionné
        String[] elements = {"commandé", "servi", "desservi"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, elements);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int selectionDefault = 0;
        for (int i = 0; i < elements.length; i++) {
            if(plat.getEtatPlat().equals(elements[i])) {
                selectionDefault = i;
            }
        }

        spinnerEtat.setAdapter(adapter);
        spinnerEtat.setSelection(selectionDefault);


        OkHttpClient client = new OkHttpClient();
        List<Plat> arrayPlat = new ArrayList<>();

        RequestBody formBody = new FormBody.Builder()
                .add("idService", idService)
                .add("date_service", dateService)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.1.72/php_Ampitryon/controleurs/afficherPlatProposerServeur.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public  void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                JSONArray jsonArrayPlats = null;
                try {
                    jsonArrayPlats = new JSONArray(responseStr);

                    int defaultSelection = 0;
                    for (int i = 0; i < jsonArrayPlats.length(); i++) {
                        JSONObject jsonPlat = jsonArrayPlats.getJSONObject(i);
                        String platId = jsonPlat.getString("IDPLAT");
                        String nomPlat = jsonPlat.getString("NOMPLAT");
                        arrayPlat.add(new Plat(platId, nomPlat));

                        if (String.valueOf(plat.getIdPlat()).equals(platId)) {
                            defaultSelection = i;
                        }
                    }
                    ArrayAdapter<Plat> adapter = new ArrayAdapter<>(EditPlatActivity.this, android.R.layout.simple_spinner_item, arrayPlat);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    int finalDefaultSelection = defaultSelection;

                    //Permet d'empêcher une erreur : CalledFromWrongThreadException
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            spinnerPlat.setAdapter(adapter);
                            spinnerPlat.setSelection(finalDefaultSelection);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            public void onFailure(Call call, IOException e)
            {
                Log.d("Test","erreur!!! connexion impossible");
            }

        });
    }

    public void modifierPlat(String idPlat, String idCommande, String selectedPlatId, String selectedEtat, String textInfosComp, String textQuantite) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("idPlat", idPlat)
                .add("idCommande", idCommande)
                .add("selectedPlatId", selectedPlatId)
                .add("etatPlat", selectedEtat)
                .add("infosComp", textInfosComp)
                .add("quantite", textQuantite)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.72/php_Ampitryon/controleurs/modifierCommander.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.d("Test","Reponse : " +responseStr);
                if (responseStr.compareTo("false") != 0) {
                    Log.d("test", "Plat modifié");
                } else {
                    Log.d("test", "erreur dans la modification du plat");
                }
            }

            public void onFailure(Call call, IOException e) {
                Log.d("Test", "erreur!!! connexion impossible");
            }

        });
    }

    public void supprimerPlat(String idPlat, String idCommande) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("idPlat", idPlat)
                .add("idCommande", idCommande)
                .build();


        Request request = new Request.Builder()
                .url("http://192.168.1.72/php_Ampitryon/controleurs/supprimerCommander.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.d("Test","Reponse : " +responseStr);
                if (responseStr.compareTo("false") != 0) {
                    Log.d("test", "Plat supprimé");
                } else {
                    Log.d("test", "erreur dans la suppression du plat");
                }
            }

            public void onFailure(Call call, IOException e) {
                Log.d("Test", "erreur!!! connexion impossible");
            }
        });
    }
}
