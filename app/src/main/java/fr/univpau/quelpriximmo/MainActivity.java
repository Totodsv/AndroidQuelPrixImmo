package fr.univpau.quelpriximmo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import fr.univpau.quelpriximmo.listItem.ListItem;
import fr.univpau.quelpriximmo.listItem.ListItemAdapter;

public class MainActivity extends AppCompatActivity {

    private GpsTracker gpsTracker;
    private TextView tvLatitude,tvLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLatitude = findViewById(R.id.latitude);
        tvLongitude = findViewById(R.id.longitude);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void getLocation(View view){
        gpsTracker = new GpsTracker(MainActivity.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            tvLatitude.setText(String.valueOf(latitude));
            tvLongitude.setText(String.valueOf(longitude));
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    public void recherche(View view){
        new GetImmo(this).execute();
    }
}