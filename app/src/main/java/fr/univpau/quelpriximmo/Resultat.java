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

import java.util.ArrayList;
import java.util.Date;

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

        //Bundle bundle = getIntent().getExtras();
        //Log.i("TAG", bundle.getString("Valeur"));
        // Add item to adapter
        //email=bundle.getString("Valeur");


        Bundle bundle = getIntent().getExtras();
        ArrayList arlText = bundle.getStringArrayList("Valeur");
        // Add item to adapter
        Log.i("AAAAAAAAAAAAAA ", String.valueOf(arlText));
        email="test2";
        mobile="test";
        ListItem nouvelleMaison = new ListItem(email, mobile);
        this.adapter.add(nouvelleMaison);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}
