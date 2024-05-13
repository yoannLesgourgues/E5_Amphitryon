package com.example.lamphitryon.commandes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lamphitryon.R;

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

public class CreateCommandeActivity extends AppCompatActivity {
    private Spinner spinnerService;
    private Spinner spinnerTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_commande);
        try {
            String idUtilisateur = getIntent().getStringExtra("idUtilisateur");

            spinnerService = findViewById(R.id.spinnerService);
            spinnerTable = findViewById(R.id.spinnerTable);

            remplirSpinnerServices();
            remplirSpinnerTables(idUtilisateur);

            final Button buttonValiderCommande = findViewById(R.id.buttonValiderEditCommande);
            buttonValiderCommande.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectedService = spinnerService.getSelectedItem().toString();
                    String selectedTable = spinnerTable.getSelectedItem().toString();

                    try {
                        creerCommande(selectedService, selectedTable);
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            final Button buttonQuitterCreateCommande = (Button) findViewById(R.id.buttonQuitterCommande);
            buttonQuitterCreateCommande.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CreateCommandeActivity.this.finish();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void remplirSpinnerServices() throws IOException {
        //REMPLIR LE SPINNER (LISTE DEROULANTE) DES SERVICES
        spinnerService = findViewById(R.id.spinnerService);

        OkHttpClient client = new OkHttpClient();
        List<String> arrayService = new ArrayList<>();

        Request request = new Request.Builder()
                .url("http://192.168.1.72/php_Ampitryon/controleurs/afficherServices.php")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public  void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                JSONArray jsonArrayServices = null;
                try {
                    jsonArrayServices = new JSONArray(responseStr);

                    for (int i = 0; i < jsonArrayServices.length(); i++) {
                        JSONObject jsonService = null;
                        jsonService = jsonArrayServices.getJSONObject(i);
                        String idService = jsonService.getString("IDSERVICE");
                        arrayService.add(idService);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateCommandeActivity.this, android.R.layout.simple_spinner_item, arrayService);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    runOnUiThread(()->{
                        spinnerService.setAdapter(adapter);
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

    public void remplirSpinnerTables(String idUtilisateur) throws IOException {
        //REMPLIR LE SPINNER (LISTE DEROULANTE) DES TABLES
        spinnerTable = findViewById(R.id.spinnerTable);

        OkHttpClient client = new OkHttpClient();
        List<String> arrayTable = new ArrayList<>();

        RequestBody formBody = new FormBody.Builder()
                .add("idUtilisateur", idUtilisateur)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.1.72/php_Ampitryon/controleurs/afficherTablesServeur.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public  void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                JSONArray jsonArrayTables = null;
                try {
                    jsonArrayTables = new JSONArray(responseStr);

                    for (int i = 0; i < jsonArrayTables.length(); i++) {
                        JSONObject jsonTable = null;
                        jsonTable = jsonArrayTables.getJSONObject(i);
                        String numTable = jsonTable.getString("NUMTABLE");
                        arrayTable.add(numTable);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateCommandeActivity.this, android.R.layout.simple_spinner_item, arrayTable);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    runOnUiThread(()->{
                        spinnerTable.setAdapter(adapter);
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

    public void creerCommande(String selectedService, String selectedTable) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("numTable", selectedTable)
                .add("idService", selectedService)
                .build();


        Request request = new Request.Builder()
                .url("http://192.168.1.72/php_Ampitryon/controleurs/creerCommande.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.d("Test","Reponse : " +responseStr);
                if (responseStr.compareTo("false") != 0) {
                    Log.d("test", "Commande créé");
                } else {
                    Log.d("test", "erreur dans la création de la commande");
                }
            }

            public void onFailure(Call call, IOException e) {
                Log.d("Test", "erreur!!! connexion impossible");
            }

        });
    }
}
