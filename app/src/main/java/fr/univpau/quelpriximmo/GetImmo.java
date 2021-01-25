package fr.univpau.quelpriximmo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import fr.univpau.quelpriximmo.Http.HttpHandler;

import static android.content.ContentValues.TAG;

public class GetImmo extends AsyncTask<Void, Void, Void> {

    private ProgressDialog progress;
    private MainActivity screen;
    ArrayList<Object> contactList = new ArrayList<>();

    public GetImmo(MainActivity s) {
        this.screen = s;
        this.progress = new ProgressDialog(this.screen);
    }

    //private static final String BASE_URL = "http://api.cquest.org/dvf?lat=43.30&lon=-0.36";
    @Override
    protected void onPreExecute() {
        progress.setTitle("Veuillez patienter");
        progress.setMessage("Récupération des données en cours...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        Log.e(TAG, "BBBBBBBBBBBBBBBBBBBBBB: " + MainActivity.getpieceMaximum());
        HttpHandler sh = new HttpHandler();

        //changement url
        String rayon = MainActivity.getRayon();
        String latitude = MainActivity.getLatitude();
        String longitute = MainActivity.getLongitude();

        Log.e(TAG, "BBBBBBBBBBBBBBBBBBBBBB: " + rayon);
        Log.e(TAG, "BBBBBBBBBBBBBBBBBBBBBB: " + latitude);
        Log.e(TAG, "BBBBBBBBBBBBBBBBBBBBBB: " + longitute);



        String url = "https://api.cquest.org/dvf?lat="+latitude+"&lon="+longitute+"&dist="+rayon;

        Log.e(TAG, "BBBBBBBBBBBBBBBBBBBBBB: " + url);
        String jsonStr = sh.makeServiceCall(url);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray features = jsonObj.getJSONArray("features");
                //Log.e(TAG, "BBBBBBBBBBBBBBBBBBBBBB: " + features.length());
                // looping through All Contacts
                for (int i = 0; i < features.length(); i++) {
                    JSONObject c = features.getJSONObject(i);

                    // Phone node is JSON Object

                    JSONObject properties = c.getJSONObject("properties");
                    //Log.e(TAG, "BBBBBBBBBBBBBBBBBBBBBB: " + i);
                    //Log.e(TAG, "BBBBBBBBBBBBBBBBBBBBBB: " + properties.getString("type_local").isEmpty());

                    String type_local;
                    String nombre_pieces_principales;
                    String valeur_fonciere = properties.getString("valeur_fonciere");
                    String nature_mutation;
                    String verifMaison = "rien";
                    String verifAppartement = "rien";
                    int pieceMini = MainActivity.getpieceMinimum();
                    int pieceMaxi = MainActivity.getpieceMaximum();

                    if (!properties.isNull("type_local")) {

                        type_local = properties.getString("type_local");
                    } else {
                        type_local = "inconnu";

                    }
                    if (!properties.isNull("nombre_pieces_principales")) {
                        if(pieceMini <= Integer.parseInt(properties.getString("nombre_pieces_principales")) &&  pieceMaxi >= Integer.parseInt(properties.getString("nombre_pieces_principales"))) {
                            nombre_pieces_principales = properties.getString("nombre_pieces_principales");
                        }
                        else {
                            nombre_pieces_principales = "inconnu";
                        }
                    } else {
                        nombre_pieces_principales = "inconnu";
                    }
                    if (!properties.isNull("nature_mutation")) {

                        nature_mutation = properties.getString("nature_mutation");
                    } else {
                        nature_mutation = "inconnu";
                    }
                    // tmp hash map for single contact
                    HashMap<String, String> contact = new HashMap<>();

                    // adding each child node to HashMap key => value
                    contact.put("valeur_fonciere", valeur_fonciere);
                    contact.put("type_local", type_local);
                    contact.put("nombre_pieces_principales", nombre_pieces_principales);

                    if(MainActivity.getMaisonTag()== true){
                        verifMaison="Maison";
                    }
                    if(MainActivity.getAppartementTag()== true){
                        verifAppartement="Appartement";
                    }

                    // adding contact to contact list
                    if(!nombre_pieces_principales.equals("inconnu")) {
                        if (verifMaison.equals(type_local) || verifAppartement.equals(type_local)) {
                            if (nature_mutation.equals("Vente")) {
                                contactList.add(contact);
                            }
                        }
                    }
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                /*
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                })*/;

            }

        } else {
            Log.e(TAG, "Couldn't get json from server.");
            /*runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG).show();
                }
            });*/
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if(progress.isShowing()) progress.dismiss();
        /*Intent i = new Intent();
        i.putExtra("Valeur", contactList);
        i.setClass(screen, Resultat.class);
        screen.startActivity(i); */
        Intent i = new Intent(); /* Intent de type direct */
        i.setClass(screen, Resultat.class);
        Bundle testSend = new Bundle();
        testSend.putSerializable("Valeur", contactList);
        i.putExtras(testSend);
        screen.startActivity(i); /* Poussé sur le bus */
   }
}
