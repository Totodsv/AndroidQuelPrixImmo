package fr.univpau.quelpriximmo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import fr.univpau.quelpriximmo.listItem.ListItem;
import fr.univpau.quelpriximmo.listItem.ListItemAdapter;

import static android.content.ContentValues.TAG;

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
    int image;
    ArrayList<Float>valeur = new ArrayList<Float>();
    ArrayList<String> barEtendue = new ArrayList<>();
    ArrayList<Integer> barNbValeur = new ArrayList<>();
    ArrayList<HashMap> arlText = new ArrayList<>();
    int barre1, barre2, barre3, barre4, barre5, barre6, barre7;
    int nbBarre1=0, nbBarre2=0, nbBarre3=0, nbBarre4=0, nbBarre5=0, nbBarre6=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nombreResultats=findViewById(R.id.titreRes);
        //image=findViewById(R.id.imgMaisonOuAppart);
        // Create the adapter to convert the array to views
        adapter = new ListItemAdapter(this, arrayOfItems);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        arlText = (ArrayList<HashMap>)bundle.getSerializable("Valeur");
        // Add resultats
        if(arlText.size()>1){
            nombreResultats.setText(String.valueOf(arlText.size())+" biens ont été trouvés");
            nombreResultats.setGravity(Gravity.CENTER_VERTICAL);
        }
        else{
            nombreResultats.setText(String.valueOf(arlText.size())+" bien a été trouvé");
            nombreResultats.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        NumberFormat nf = NumberFormat.getInstance(); //Pour espacer les nombres tout les 3 chiffres

        // Add item to adapter
        //Log.i("AAAAAAAAAAAAAA ", String.valueOf(arlText.size()));
        int[] images={
                R.drawable.house,
                R.drawable.building
        };
        for(int i=0; i<arlText.size();i++){
            //Image Maison ou Appartement
            Log.i("AAAAAAAAAAAAAA ", String.valueOf(arlText.get(i).get("type_local")));
            if(arlText.get(i).get("type_local").equals("Maison")){
                image=images[0];
            }
            else{
                image=images[1];
            }
            //Cas des prix avec centimes
            Double virgule=Double.parseDouble((String) arlText.get(i).get("valeur_fonciere"));
            int sansVirgule = (int) Math.round(virgule);
            //Log.i("Prix", String.valueOf(sansVirgule));
            String s = nf.format(Integer.parseInt((String.valueOf(sansVirgule))));
            valeur_fonciere=s+" €"; //Ajout du symbole € après le prix
            valeur.add(Float.parseFloat((String) arlText.get(i).get("valeur_fonciere")));

            //valeur_fonciere=String.valueOf(arlText.get(i).get("valeur_fonciere"))+" €";
            type_local=String.valueOf(arlText.get(i).get("type_local"))+" / ";
            nombre_pieces_principales=String.valueOf(arlText.get(i).get("nombre_pieces_principales"))+"p";
            adresse=String.valueOf(arlText.get(i).get("adresse"));
            date_mutation=String.valueOf(arlText.get(i).get("date_mutation"));
            ListItem nouvelleMaison = new ListItem(image, valeur_fonciere,type_local,nombre_pieces_principales,adresse,date_mutation);
            this.adapter.add(nouvelleMaison);
        }

        if(arlText.size()>0){
            Collections.sort(valeur);
            Log.i("CCCCCCCCCCC", String.valueOf(valeur));
            int etendue = transfoZeroEtendue((float) ((transfoZeroPremier(valeur.get(0))+ transfoZerodernier(valeur.get(valeur.size()-1)))/6));
            int barre=transfoZeroPremier(valeur.get(0)) ;;
            barEtendue.add(String.valueOf(barre)+"€-"+String.valueOf(barre+etendue)+"€");
            barre1 = transfoZeroPremier(valeur.get(0));
            barre2 = transfoZeroPremier(valeur.get(0))+etendue;
            barre3 = transfoZeroPremier(valeur.get(0))+etendue*2;
            barre4 = transfoZeroPremier(valeur.get(0))+etendue*3;
            barre5 = transfoZeroPremier(valeur.get(0))+etendue*4;
            barre6 = transfoZeroPremier(valeur.get(0))+etendue*5;
            for(int j=1; j<5;j++){
                barre= barre + etendue;
                Log.i("CCCCCCCCCCC", String.valueOf(barre));
                barEtendue.add(String.valueOf(String.valueOf(barre)+"€-"+String.valueOf(barre+etendue)+"€"));
            }
            barEtendue.add(String.valueOf(String.valueOf(barre)+"€ et +"));
            for (int j = 0; j < valeur.size(); j++) {
                if(valeur.get(j)<barre2){
                    nbBarre1=nbBarre1 +1;
                }else if(valeur.get(j)<barre3){
                    nbBarre2=nbBarre2 +1;
                }else if(valeur.get(j)<barre4){
                    nbBarre3=nbBarre3 +1;
                }else if(valeur.get(j)<barre5){
                    nbBarre4=nbBarre4 +1;
                }else if(valeur.get(j)<barre6){
                    nbBarre5=nbBarre5 +1;
                }else{
                    nbBarre6=nbBarre6 +1;
                }

            }



            barNbValeur.add(nbBarre1);
            barNbValeur.add(nbBarre2);
            barNbValeur.add(nbBarre3);
            barNbValeur.add(nbBarre4);
            barNbValeur.add(nbBarre5);
            barNbValeur.add(nbBarre6);
            Log.i("CCCCCCCCCCC", String.valueOf(barEtendue));
            Log.i("CCCCCCCCCCC", String.valueOf(barNbValeur));
            Log.i("CCCCCCCCCCC", String.valueOf(etendue));
        }
    }

    public void goStatistiques(View view) { //Changement d'état d'un bouton sélectionné/déselectionnée
        if(arlText.size()>0) {
            Intent i = new Intent(); /* Intent de type direct */
            i.setClass(this, Statistiques.class);
            Bundle sendEtendue = new Bundle();
            sendEtendue.putSerializable("Etendue", barEtendue);
            i.putExtras(sendEtendue);
            Bundle sendNbValeur = new Bundle();
            sendNbValeur.putSerializable("nbValeur", barNbValeur);
            i.putExtras(sendNbValeur);
            //PUT LES VALEURS NECESSAIRES POUR FAIRE LES STATS
            this.startActivity(i); /* Poussé sur le bus */
        }
        else{
            Toast.makeText(this,"Aucun bien trouvé, Statistiques indisponible",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onSupportNavigateUp() { // Simule un back depuis la flèche de navigation
        super.onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public int transfoZeroPremier(Float i){
        String nbrChar = String.valueOf(i);
        String  c1 = null;
        if(i<10000){
            c1="0";
        }
        else if(i<100000) {

            c1=nbrChar.substring(0,1);
            c1=c1+"0000";

        }else if(i<1000000){
            c1=nbrChar.substring(0,1);
            c1=c1+"00000";
        }else{
            c1=nbrChar.substring(0,2);
            c1=c1+"00000";
        }
        return  Integer.parseInt(c1);

    }

    public int transfoZerodernier(Float i){
        String nbrChar = String.valueOf(i);
        String  c1 = null;
        int intermediaire= 0;

        if(i<10000){
            intermediaire=0;
        }
        else if(i<100000) {
            c1=nbrChar.substring(0,1);
            c1=c1+"0";
            intermediaire = Integer.parseInt(c1)+10000;

        }else if(i<1000000){
            c1=nbrChar.substring(0,1);
            c1=c1+"00000";
            intermediaire = Integer.parseInt(c1)+100000;
        }else{
            c1=nbrChar.substring(0,2);
            c1=c1+"00000";
            intermediaire = Integer.parseInt(c1)+100000;
        }
        return  intermediaire;

    }

    public int transfoZeroEtendue(Float i){
        String nbrChar = String.valueOf(i);
        String  c1 = null;
        if(i<10000){
            c1="1000";
        }
        else if(i<100000) {
            c1="10000";

        }else if(i<1000000){
            c1=nbrChar.substring(0,1);
            c1=c1+"00000";
        }else{
            c1=nbrChar.substring(0,2);
            c1=c1+"00000";
        }
        return  Integer.parseInt(c1);


    }

}



