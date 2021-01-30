package fr.univpau.quelpriximmo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Statistiques extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistiques);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() { // Simule un back depuis la flèche de navigation
        super.onBackPressed();
        return true;
    }
}
