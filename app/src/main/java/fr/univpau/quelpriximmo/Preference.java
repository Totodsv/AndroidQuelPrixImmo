package fr.univpau.quelpriximmo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import fr.univpau.quelpriximmo.listItem.ListItem;
import fr.univpau.quelpriximmo.listItem.ListItemAdapter;

import static android.content.ContentValues.TAG;

public class Preference extends AppCompatActivity {

    private static EditText tvRayon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvRayon=findViewById(R.id.rayon);
        tvRayon.setText(MainActivity.getRayon());
    }

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
}
