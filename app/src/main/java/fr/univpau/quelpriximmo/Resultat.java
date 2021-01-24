package fr.univpau.quelpriximmo;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    String valeur_fonciere;
    String type_local;
    String nombre_pieces_principales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultat);
        // Create the adapter to convert the array to views
        adapter = new ListItemAdapter(this, arrayOfItems);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        ArrayList<HashMap> arlText = (ArrayList<HashMap>)bundle.getSerializable("Valeur");

        // Add item to adapter
        Log.i("AAAAAAAAAAAAAA ", String.valueOf(arlText));
        Log.i("AAAAAAAAAAAAAA ", String.valueOf(arlText.size()));

        for(int i=0; i<arlText.size();i++){
            Log.i("AAAAAAAAAAAAAA ", String.valueOf(arlText.get(i)));
            valeur_fonciere=String.valueOf(arlText.get(i).get("valeur_fonciere"));
            type_local=String.valueOf(arlText.get(i).get("type_local"));
            nombre_pieces_principales=String.valueOf(arlText.get(i).get("nombre_pieces_principales"));

            ListItem nouvelleMaison = new ListItem(valeur_fonciere,type_local,nombre_pieces_principales);
            this.adapter.add(nouvelleMaison);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
