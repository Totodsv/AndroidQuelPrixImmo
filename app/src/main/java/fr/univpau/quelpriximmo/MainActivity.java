package fr.univpau.quelpriximmo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import fr.univpau.quelpriximmo.GPS.GpsTracker;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private GpsTracker gpsTracker;
    private TextView tvLatitude,tvLongitude;
    private  static EditText tvRayon;
    private static  String rayonValue, LatitudeValue, longitudeValue ;
    private double tLatitudeValue, tlongitudeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void getLocation(View view){
    }


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

        new GetImmo(this).execute();
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


}



