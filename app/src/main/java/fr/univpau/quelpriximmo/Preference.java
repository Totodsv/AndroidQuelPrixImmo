package fr.univpau.quelpriximmo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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

//METTRE LA PREFERENCE PERSISTENTE MEME QUAND APPLI CLOSE
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        Intent resultInt = new Intent();
        resultInt.putExtra("Result", tvRayon.getText().toString());
        setResult(Activity.RESULT_OK, resultInt);
        Log.i(TAG, "RAYON "+tvRayon.getText().toString());
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() { // Simule un back depuis la flèche de navigation
        Intent resultInt = new Intent();
        resultInt.putExtra("Result", tvRayon.getText().toString());
        setResult(Activity.RESULT_OK, resultInt);
        Log.i(TAG, "RAYON "+tvRayon.getText().toString());
        super.onBackPressed();
        return true;
    }

    public void confirmer(View view){
        Intent resultInt = new Intent();
        resultInt.putExtra("Result", tvRayon.getText().toString());
        setResult(Activity.RESULT_OK, resultInt);
        Log.i(TAG, "RAYON "+tvRayon.getText().toString());
        super.onBackPressed();
    }

    private void setupSlider(){
        slider = findViewById(R.id.fluidSlider);
        int max=2000;
        int min=1;
        int total = max-min;

        slider.setStartText(String.valueOf(min));
        slider.setEndText(String.valueOf(max));
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
