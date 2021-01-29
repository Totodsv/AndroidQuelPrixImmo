package fr.univpau.quelpriximmo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;

import java.util.List;

import fr.univpau.quelpriximmo.GPS.GpsTracker;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private GpsTracker gpsTracker;
    private Button boutonMaison, boutonAppartement;
    private static  String rayonValue, LatitudeValue, longitudeValue, min, max ;
    private double tLatitudeValue, tlongitudeValue;
    private static  Float pieceMinimum, pieceMaximum;
    TextView textMinP, textMaxP;
    private static Boolean maisonTag=false, appartementTag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boutonMaison = findViewById(R.id.btnMaison);
        boutonAppartement = findViewById(R.id.btnAppartement);
        RangeSlider slider = findViewById(R.id.rangeSlider2);
        slider.addOnSliderTouchListener(touchListener);
        textMinP = findViewById(R.id.minPieces);
        textMaxP = findViewById(R.id.maxPieces);
        rayonValue="500";//Valeur par défaut
        min="1";
        max="8";
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //Création du menu "..." pour l'activité Preference
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //Sélection dans le sous-menu "..."
        if(item.getItemId()==R.id.pref){
            Intent i = new Intent(); /* Intent de type direct */
            i.setClass(this, Preference.class);
            /* Poussé sur le bus */
            ((Activity) this).startActivityForResult(i,0);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { // Récupération du rayon choisi depuis Preference
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            String value = (String) data.getExtras().getString("Result");
            rayonValue=value;
            Toast.makeText(this,"Rayon de "+value+"m ajouté au filtre de recherche",Toast.LENGTH_SHORT).show();
        }
    }

    public void recherche(View view){ //Lance la recherche selon les filtres choisis
        // getLocation
        gpsTracker = new GpsTracker(MainActivity.this);
        if(gpsTracker.canGetLocation()){
            tLatitudeValue = gpsTracker.getLatitude();
            tlongitudeValue = gpsTracker.getLongitude();
        }else{
            gpsTracker.showSettingsAlert();
        }
        //rayonValue = tvRayon.getText().toString();
        LatitudeValue = String.valueOf(tLatitudeValue);
        longitudeValue = String.valueOf(tlongitudeValue);

        RangeSlider rangeSlider = findViewById(R.id.rangeSlider2);
        pieceMinimum= rangeSlider.getValues().get(0);
        pieceMaximum= rangeSlider.getValues().get(1);
        Log.i(TAG, "RAYON "+rayonValue);
        new GetImmo(this).execute();
    }

    public void maisonTag(View view) { //Changement d'état d'un bouton sélectionné/déselectionnée
        if (maisonTag == true) {
            //On reset le bouton
            boutonMaison.setBackgroundColor(getResources().getColor(R.color.themeOrange));
            boutonMaison.setTextColor(getResources().getColor(R.color.white));
            maisonTag = false;
            Toast.makeText(this,"Maison retirée du filtre de recherche",Toast.LENGTH_SHORT).show();
        }
        else {
            //On enfonce le bouton
            boutonMaison.setBackgroundColor(getResources().getColor(R.color.white));
            boutonMaison.setTextColor(getResources().getColor(R.color.themeOrange));
            maisonTag = true;
            Toast.makeText(this,"Maison ajoutée au filtre de recherche",Toast.LENGTH_SHORT).show();
        }
    }

    public void appartementTag(View view) { //Changement d'état d'un bouton sélectionné/déselectionnée
        if (appartementTag == true) {
            //On reset le bouton
            boutonAppartement.setBackgroundColor(getResources().getColor(R.color.themeOrange));
            boutonAppartement.setTextColor(getResources().getColor(R.color.white));
            appartementTag = false;
            Toast.makeText(this,"Appartement retiré du filtre de recherche",Toast.LENGTH_SHORT).show();
        }
        else {
            //On enfonce le bouton
            boutonAppartement.setBackgroundColor(getResources().getColor(R.color.white));
            boutonAppartement.setTextColor(getResources().getColor(R.color.themeOrange));
            appartementTag = true;
            Toast.makeText(this,"Appartement ajouté au filtre de recherche",Toast.LENGTH_SHORT).show();
        }
    }

    private final RangeSlider.OnSliderTouchListener touchListener =
            new RangeSlider.OnSliderTouchListener() { //Tracking de la touchbar pour afficher les pièces à l'utilisateur
                @Override
                public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                    RangeSlider valueTracker = findViewById(R.id.rangeSlider2);
                    float minP= valueTracker.getValues().get(0);
                    float maxP=valueTracker.getValues().get(1);
                    min=String.valueOf(minP);
                    max=String.valueOf(maxP);
                    textMinP.setText(min.substring(0,1));
                    textMaxP.setText(max.substring(0,1));
                    //textMinP.setText(String.valueOf(minP));
                    //textMaxP.setText(String.valueOf(maxP));
                }

                @Override
                public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                    RangeSlider valueTracker2 = findViewById(R.id.rangeSlider2);
                    float minP= valueTracker2.getValues().get(0);
                    float maxP=valueTracker2.getValues().get(1);
                    min=String.valueOf(minP);
                    max=String.valueOf(maxP);
                    textMinP.setText(min.substring(0,1));
                    textMaxP.setText(max.substring(0,1));
                    //textMinP.setText(String.valueOf(minP));
                    //textMaxP.setText(String.valueOf(maxP));
                }
            };

    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        super.onRestoreInstanceState (savedInstanceState);
        // Restore our variable by key
        //count = savedInstanceState.getInt (“Count”);
        // We can also set the default value after the key, separated by commas
        rayonValue=savedInstanceState.getString("Rayon");
        min=savedInstanceState.getString("Minimum");
        max=savedInstanceState.getString("Maximum");
        textMinP.setText(min.substring(0,1));
        textMaxP.setText(max.substring(0,1));
        maisonTag=savedInstanceState.getBoolean("boutonMaison");
        appartementTag=savedInstanceState.getBoolean("boutonAppart");

        if (maisonTag == true) {
            //On restaure le bouton enfoncé
            boutonMaison.setBackgroundColor(getResources().getColor(R.color.white));
            boutonMaison.setTextColor(getResources().getColor(R.color.themeOrange));
        }
        else {
            //On restaure le bouton initial
            boutonMaison.setBackgroundColor(getResources().getColor(R.color.themeOrange));
            boutonMaison.setTextColor(getResources().getColor(R.color.white));
        }

        if (appartementTag == true) {
            //On restaure le bouton enfoncé
            boutonAppartement.setBackgroundColor(getResources().getColor(R.color.white));
            boutonAppartement.setTextColor(getResources().getColor(R.color.themeOrange));
        }
        else {
            //On restaure le bouton initial
            boutonAppartement.setBackgroundColor(getResources().getColor(R.color.themeOrange));
            boutonAppartement.setTextColor(getResources().getColor(R.color.white));
        }
    }
    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState (outState);
        // Write the variable with the key in the Bundle
        //outState.putInt (“Count”, count);
        //Pour save nos données en landscape
        //Les boutons enfoncés ou non
        outState.putBoolean("boutonMaison",maisonTag);
        outState.putBoolean("boutonAppart",appartementTag);
        //Le nombre de pièces
        outState.putString("Minimum",min);
        outState.putString("Maximum",max);
        outState.putString("Rayon",rayonValue);
    }

    public static String getRayon() {
        return rayonValue;
    }
    public static String getLatitude() {
        return LatitudeValue;
    }
    public static String getLongitude() {
        return longitudeValue;
    }
    public static int getpieceMinimum() {
        return Math.round(pieceMinimum);
    }
    public static int getpieceMaximum() {
        return Math.round(pieceMaximum);
    }
    public static boolean getMaisonTag() {
        return maisonTag;
    }
    public static boolean getAppartementTag() {
        return appartementTag;
    }

}



