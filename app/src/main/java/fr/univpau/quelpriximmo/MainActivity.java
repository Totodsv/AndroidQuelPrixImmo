package fr.univpau.quelpriximmo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;

import java.util.List;

import fr.univpau.quelpriximmo.GPS.GpsTracker;

public class MainActivity extends AppCompatActivity {

    private GpsTracker gpsTracker;
    private TextView tvLatitude,tvLongitude;
    private  static EditText tvRayon;
    private static  String rayonValue, LatitudeValue, longitudeValue ;
    private double tLatitudeValue, tlongitudeValue;
    private static  Float pieceMinimum, pieceMaximum;
    EditText textMinP, textMaxP;
    private static Boolean maisonTag=false, appartementTag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RangeSlider slider = findViewById(R.id.rangeSlider2);
        slider.addOnSliderTouchListener(touchListener);
        textMinP = (EditText) findViewById(R.id.minPieces);
        textMaxP = (EditText) findViewById(R.id.maxPieces);
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private final RangeSlider.OnSliderTouchListener touchListener =
            new RangeSlider.OnSliderTouchListener() {
                @Override
                public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                    RangeSlider valueTracker = findViewById(R.id.rangeSlider2);
                    float minP= valueTracker.getValues().get(0);
                    float maxP=valueTracker.getValues().get(1);
                    textMinP.setText(String.valueOf(minP));
                    textMaxP.setText(String.valueOf(maxP));
                }

                @Override
                public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                    RangeSlider valueTracker2 = findViewById(R.id.rangeSlider2);
                    float minP= valueTracker2.getValues().get(0);
                    float maxP=valueTracker2.getValues().get(1);
                    textMinP.setText(String.valueOf(minP));
                    textMaxP.setText(String.valueOf(maxP));
                }


            };



    public void recherche(View view){

        // getLocation
        gpsTracker = new GpsTracker(MainActivity.this);
        if(gpsTracker.canGetLocation()){
            tLatitudeValue = gpsTracker.getLatitude();
            tlongitudeValue = gpsTracker.getLongitude();
        }else{
            gpsTracker.showSettingsAlert();
        }

        // envoi des valeurs
        tvRayon = (EditText) findViewById(R.id.Rayon);

        rayonValue = tvRayon.getText().toString();
        LatitudeValue = String.valueOf(tLatitudeValue);
        longitudeValue = String.valueOf(tlongitudeValue);

        RangeSlider rangeSlider = findViewById(R.id.rangeSlider2);
        pieceMinimum= rangeSlider.getValues().get(0);
        pieceMaximum= rangeSlider.getValues().get(1);

        new GetImmo(this).execute();
    }

    public void maisonTag(View view) {

        if (maisonTag == true)
            maisonTag = false;
        else
            maisonTag = true;
    }

    public void appartementTag(View view) {
        if (appartementTag == true)
            appartementTag = false;
        else
            appartementTag = true;
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
        return Math.round(pieceMinimum);
    }
    public static boolean getMaisonTag() {
        return maisonTag;
    }
    public static boolean getAppartementTag() {
        return appartementTag;
    }

}



