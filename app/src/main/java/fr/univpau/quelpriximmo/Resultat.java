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
    String email;
    String mobile;

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
        //HashMap<String,String> arlText = (HashMap<String, String>) bundle.getSerializable("Valeur");
        ArrayList arlText = bundle.getStringArrayList("Valeur");
        //List<HashMap<String,Object>> arlText=(ArrayList<HashMap<String,Object>>)request.getAttribute("Valeur"); //c'est comme ça que je devais récupérer la liste.


        // Add item to adapter
        Log.i("AAAAAAAAAAAAAA ", String.valueOf(arlText));
        Log.i("AAAAAAAAAAAAAA ", String.valueOf(arlText.size()));
        //Log.i("AAAAAAAAAAAAAA ", String.valueOf(arlText.get("email")));

        for(int i=0; i<arlText.size();i++){
            Log.i("AAAAAAAAAAAAAA ", String.valueOf(arlText.get(i)));
            email=String.valueOf(arlText.get(i));
            mobile=String.valueOf(i);
            ListItem nouvelleMaison = new ListItem(email, mobile);
            this.adapter.add(nouvelleMaison);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
