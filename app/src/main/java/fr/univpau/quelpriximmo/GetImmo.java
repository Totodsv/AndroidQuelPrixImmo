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
import fr.univpau.quelpriximmo.Orientation.OrientationUtils;

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

        Log.i(TAG, "BBBBBBBBBBBBBBBBBBBBBB: " + rayon);
        Log.i(TAG, "BBBBBBBBBBBBBBBBBBBBBB: " + latitude);
        Log.i(TAG, "BBBBBBBBBBBBBBBBBBBBBB: " + longitute);

        String url = "https://api.cquest.org/dvf?lat="+latitude+"&lon="+longitute+"&dist="+rayon;

        Log.i(TAG, "BBBBBBBBBBBBBBBBBBBBBB: " + url);
        String jsonStr = sh.makeServiceCall(url);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray features = jsonObj.getJSONArray("features");
                Log.i(TAG, "BBBBBBBBBBBBBBBBBBBBBB: " + features.length());
                // looping through All features
                int j = 0;
                if (features.length()>2500){
                    j=2500;
                }else{
                    j = features.length();
                }
                for (int i = 0; i < j; i++) {
                    JSONObject c = features.getJSONObject(i);

                    // properties node is JSON Object

                    JSONObject properties = c.getJSONObject("properties");
                    //Log.e(TAG, "BBBBBBBBBBBBBBBBBBBBBB: " + i);
                    //Log.e(TAG, "BBBBBBBBBBBBBBBBBBBBBB: " + properties.getString("type_local").isEmpty());

                    String type_local;
                    String nombre_pieces_principales;
                    String valeur_fonciere = properties.getString("valeur_fonciere");
                    String nature_mutation;
                    String verifMaison = "rien";
                    String verifAppartement = "rien";
                    String numero_voie = "rien";
                    String type_voie= "rien";
                    String voie= "rien";
                    String adresse = "rien";
                    String date_mutation_US = properties.getString("date_mutation");
                    int pieceMini = MainActivity.getpieceMinimum();
                    int pieceMaxi = MainActivity.getpieceMaximum();

                    String[] arr = date_mutation_US.split("-");
                    String date_mutation = arr[2] + "/" + arr[1] + "/" + arr[0];

                    if (!properties.isNull("type_local")) {

                        type_local = properties.getString("type_local");
                    } else {
                        type_local = "inconnu";

                    }
                    if (!properties.isNull("nombre_pieces_principales")) {
                        if (pieceMini <= Integer.parseInt(properties.getString("nombre_pieces_principales")) && pieceMaxi >= Integer.parseInt(properties.getString("nombre_pieces_principales"))) {
                            nombre_pieces_principales = properties.getString("nombre_pieces_principales");
                        } else {
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
                    if (!properties.isNull("numero_voie")) {

                        numero_voie = properties.getString("numero_voie");
                    } else {
                        adresse = "inconnu";
                    }
                    if (!properties.isNull("type_voie")) {

                        type_voie = properties.getString("type_voie");
                    } else {
                        adresse = "inconnu";
                    }
                    if (!properties.isNull("voie")) {

                        voie = properties.getString("voie");
                    } else {
                        adresse = "inconnu";
                    }
                    // tmp hash map for single contact
                    HashMap<String, String> contact = new HashMap<>();

                    // adding each child node to HashMap key => value
                    contact.put("valeur_fonciere", valeur_fonciere);
                    contact.put("type_local", type_local);
                    contact.put("nombre_pieces_principales", nombre_pieces_principales);
                    contact.put("date_mutation", date_mutation);

                    if (MainActivity.getMaisonTag() == true) {
                        verifMaison = "Maison";
                    }
                    if (MainActivity.getAppartementTag() == true) {
                        verifAppartement = "Appartement";
                    }

                    // adding contact to contact list
                    if (!adresse.equals("inconnu")) {
                        adresse = numero_voie+ " " + type_voie+ " " + voie;
                        contact.put("adresse", adresse);
                        if (!nombre_pieces_principales.equals("inconnu")) {
                            if (verifMaison.equals(type_local) || verifAppartement.equals(type_local)) {
                                if (nature_mutation.equals("Vente")) {
                                    contactList.add(contact);
                                }
                            }
                        }
                    }
                }
            } catch (final JSONException e) {
                Log.i(TAG, "Json parsing error: " + e.getMessage());
            }

        } else {
            Log.i(TAG, "Couldn't get json from server.");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        OrientationUtils.unlockOrientation(this.screen);
        if(progress.isShowing()) progress.dismiss();
        Intent i = new Intent(); /* Intent de type direct */
        i.setClass(screen, Resultat.class);
        Bundle testSend = new Bundle();
        testSend.putSerializable("Valeur", contactList);
        i.putExtras(testSend);
        screen.startActivity(i); /* Poussé sur le bus */
   }
}
