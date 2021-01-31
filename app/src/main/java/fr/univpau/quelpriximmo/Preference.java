package fr.univpau.quelpriximmo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ramotion.fluidslider.FluidSlider;

import java.util.ArrayList;
import java.util.HashMap;

import fr.univpau.quelpriximmo.RangeEditTextNumber.MinMaxFilter;
import fr.univpau.quelpriximmo.listItem.ListItem;
import fr.univpau.quelpriximmo.listItem.ListItemAdapter;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

import static android.content.ContentValues.TAG;

public class Preference extends AppCompatActivity {

    SharedPreferences pref;
    private static EditText tvRayon;
    FluidSlider slider;
    Button boutonConfirmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvRayon=findViewById(R.id.rayon);
        tvRayon.setText(MainActivity.getRayon());
        tvRayon.setFilters( new InputFilter[]{ new MinMaxFilter( "1" , "2000" )}) ;
        boutonConfirmer=findViewById(R.id.btnConfirmer);
        setupSlider();
    }


@Override
protected void onStart() {
    super.onStart();
    Log.i("TAG", "Lancement de l'activité");
    pref = getPreferences(Activity.MODE_PRIVATE);
    if (pref.contains("memoire")) {
        tvRayon.setText(pref.getString("memoire", "unknown"));
    }
}

    @Override
    public void onBackPressed() {
        sauvegarder();
    }

    @Override
    public boolean onSupportNavigateUp() { // Simule un back depuis la flèche de navigation
        sauvegarder();
        return true;
    }

    public void confirmer(View view){
        sauvegarder();
    }

    public void defaut(View view){
        tvRayon.setText("499");
        slider.setPosition((float) (((Float.parseFloat(tvRayon.getText().toString())*100)/2000)*0.01)); //Met la position en pourcentage du rayon sur 2000 (valeur max du slider)
        slider.setBubbleText(tvRayon.getText().toString());//Ecris dans la bulle de texte la valeur du rayon
        Toast.makeText(this,"Paramètres réinitialisés par défaut",Toast.LENGTH_SHORT).show();

    }

    public void sauvegarder(){
        Intent resultInt = new Intent();
        resultInt.putExtra("Result", tvRayon.getText().toString());
        setResult(Activity.RESULT_OK, resultInt);
        Log.i(TAG, "RAYON "+tvRayon.getText().toString());
        //Sauvegarder les préférences de l'utilisateur même quand l'application est détruite
        SharedPreferences.Editor ed = pref.edit();
        ed.putString("memoire", tvRayon.getText().toString());
        ed.commit();

        super.onBackPressed();
    }

    private void setupSlider(){
        slider = findViewById(R.id.fluidSlider);
        int max=2000;
        int min=1;
        int total = max-min;

        slider.setStartText(String.valueOf(min));
        slider.setEndText(String.valueOf(max));
        slider.setPosition((float) (((Float.parseFloat(tvRayon.getText().toString())*100)/max)*0.01)); //Met la position en pourcentage du rayon sur 2000 (valeur max du slider)
        slider.setBubbleText(tvRayon.getText().toString());//Ecris dans la bulle de texte la valeur du rayon
        // Or Java 8 lambda
        slider.setPositionListener(pos -> {
            //final String value = String.valueOf( min + (total  * pos) );
            int valueEntier = Math.round(min + (total  * pos));
            final String value = String.valueOf(valueEntier);
            tvRayon.setText(value);
            slider.setBubbleText(value);
            return Unit.INSTANCE;
        });
    }
}
