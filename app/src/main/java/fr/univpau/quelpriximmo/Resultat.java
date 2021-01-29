package fr.univpau.quelpriximmo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import fr.univpau.quelpriximmo.listItem.ListItem;
import fr.univpau.quelpriximmo.listItem.ListItemAdapter;

public class Resultat extends AppCompatActivity {

    // Construct the data source
    ArrayList<ListItem> arrayOfItems = new ArrayList<ListItem>();
    ListItemAdapter adapter;
    TextView nombreResultats;
    String valeur_fonciere;
    String type_local;
    String nombre_pieces_principales;
    String adresse;
    String date_mutation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultat);
        nombreResultats=findViewById(R.id.titreRes);
        // Create the adapter to convert the array to views
        adapter = new ListItemAdapter(this, arrayOfItems);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        ArrayList<HashMap> arlText = (ArrayList<HashMap>)bundle.getSerializable("Valeur");
        // Add resultats
        if(arlText.size()>1){
            nombreResultats.setText(String.valueOf(arlText.size())+" biens ont été trouvés");
        }
        else{
            nombreResultats.setText(String.valueOf(arlText.size())+" bien a été trouvé");
        }
        NumberFormat nf = NumberFormat.getInstance(); //Pour espacer les nombres tout les 3 chiffres

        // Add item to adapter
        Log.i("AAAAAAAAAAAAAA ", String.valueOf(arlText.size()));

        for(int i=0; i<arlText.size();i++){
            //Cas des prix avec centimes
            Double virgule=Double.parseDouble((String) arlText.get(i).get("valeur_fonciere"));
            int sansVirgule = (int) Math.round(virgule);
            Log.i("Prix", String.valueOf(sansVirgule));
            String s = nf.format(Integer.parseInt((String.valueOf(sansVirgule))));
            valeur_fonciere=s+" €"; //Ajout du symbole € après le prix

            //valeur_fonciere=String.valueOf(arlText.get(i).get("valeur_fonciere"))+" €";
            type_local=String.valueOf(arlText.get(i).get("type_local"))+" / ";
            nombre_pieces_principales=String.valueOf(arlText.get(i).get("nombre_pieces_principales"))+"p";
            adresse=String.valueOf(arlText.get(i).get("adresse"));
            date_mutation=String.valueOf(arlText.get(i).get("date_mutation"));
            ListItem nouvelleMaison = new ListItem(valeur_fonciere,type_local,nombre_pieces_principales,adresse,date_mutation);
            this.adapter.add(nouvelleMaison);
        }
    }

    public void goStatistiques(View view) { //Changement d'état d'un bouton sélectionné/déselectionnée
        Intent i = new Intent(); /* Intent de type direct */
        i.setClass(this, Statistiques.class);
        //PUT LES VALEURS NECESSAIRES POUR FAIRE LES STATS
        this.startActivity(i); /* Poussé sur le bus */
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
