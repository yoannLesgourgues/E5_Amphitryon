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

public class CreatePlatActivity extends AppCompatActivity {
    private Spinner spinnerPlat;
    private EditText editTextInfosComp;
    private EditText editTextQuantite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plat);

        try {
            Commande commande = (Commande) getIntent().getSerializableExtra("commande");

            spinnerPlat = findViewById(R.id.spinnerPlatCreate);
            editTextInfosComp = findViewById(R.id.editTextInfosCompCreate);
            editTextQuantite = findViewById(R.id.editTextQuantiteCreate);

            remplirSpinnerPlats(String.valueOf(commande.getIdService()), commande.getDate_Service());


        final Button buttonValiderCreatePlat = findViewById(R.id.buttonValiderCreatePlat);
        buttonValiderCreatePlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plat selectedPlat = (Plat) spinnerPlat.getSelectedItem();
                String idPlat = selectedPlat.getId();
                String textInfosComp = editTextInfosComp.getText().toString();
                String textQuantite = editTextQuantite.getText().toString();

                try {
                    ajouterPlat(String.valueOf(commande.getIdCommande()), idPlat, textInfosComp, textQuantite);
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

            final Button buttonQuitterCreatePlat = (Button) findViewById(R.id.buttonQuitterCreatePlat);
            buttonQuitterCreatePlat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CreatePlatActivity.this.finish();
                }
            });

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void remplirSpinnerPlats(String idService, String dateService) throws IOException {
        //REMPLIR LE SPINNER (LISTE DEROULANTE) DES SERVICES
        spinnerPlat = findViewById(R.id.spinnerPlatCreate);

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

                    for (int i = 0; i < jsonArrayPlats.length(); i++) {
                        JSONObject jsonPlat = jsonArrayPlats.getJSONObject(i);
                        String idPlat = jsonPlat.getString("IDPLAT");
                        String nomPlat = jsonPlat.getString("NOMPLAT");
                        arrayPlat.add(new Plat(idPlat, nomPlat));
                    }
                    ArrayAdapter<Plat> adapter = new ArrayAdapter<>(CreatePlatActivity.this, android.R.layout.simple_spinner_item, arrayPlat);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    //Permet d'empêcher une erreur : CalledFromWrongThreadException
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            spinnerPlat.setAdapter(adapter);
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

    public void ajouterPlat(String idCommande, String selectedPlat, String textInfosComp, String textQuantite) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("idPlat", selectedPlat)
                .add("idCommande", idCommande)
                .add("infosComp", textInfosComp)
                .add("quantite", textQuantite)
                .build();


        Request request = new Request.Builder()
                .url("http://192.168.1.72/php_Ampitryon/controleurs/ajouterCommander.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.d("Test","Reponse : " +responseStr);
                if (responseStr.compareTo("false") != 0) {
                    Log.d("test", "Plat ajouté");
                } else {
                    Log.d("test", "erreur dans l'ajout du plat");
                }
            }

            public void onFailure(Call call, IOException e) {
                Log.d("Test", "erreur!!! connexion impossible");
            }

        });
    }
}
