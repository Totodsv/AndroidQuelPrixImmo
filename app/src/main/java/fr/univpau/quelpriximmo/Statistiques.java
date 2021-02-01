package fr.univpau.quelpriximmo;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Statistiques extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistiques);


        Bundle receiveEtendue = getIntent().getExtras();
        ArrayList<String> stringEtendue = (ArrayList<String>)receiveEtendue.getSerializable("Etendue");
        Log.i("BBBBBBBB", String.valueOf(stringEtendue));

        Bundle receiveNbValeur = getIntent().getExtras();
        ArrayList<Integer> nbValeur = (ArrayList<Integer>)receiveEtendue.getSerializable("nbValeur");
        Log.i("BBBBBBBB", String.valueOf(nbValeur));

        BarChart barChart = findViewById(R.id.fragment_verticalbarchart_chart);
        ArrayList<BarEntry>barEntries = new ArrayList<BarEntry>();

        barEntries.add(new BarEntry(0, nbValeur.get(0)));
        barEntries.add(new BarEntry(1, nbValeur.get(1)));
        barEntries.add(new BarEntry(2, nbValeur.get(2)));
        barEntries.add(new BarEntry(3, nbValeur.get(3)));
        barEntries.add(new BarEntry(4, nbValeur.get(4)));
        barEntries.add(new BarEntry(5, nbValeur.get(5)));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Nombre de bien vendus dans la tranche de prix");
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        //        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setHighlightEnabled(true);
        barDataSet.setHighLightColor(Color.RED);
        barDataSet.setColors(new int[]{Color.parseColor("#FF7F50")});
        barDataSet.setDrawValues(false);

        BarData barData = new BarData(barDataSet);

        barChart.getDescription().setTextSize(12);
        barChart.getDescription().setText("Nb de ventes par tranche");
        barChart.setDrawMarkers(true);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.TOP);

        ArrayList<String> labels = new ArrayList<String> ();

        labels.add( stringEtendue.get(0));
        labels.add( stringEtendue.get(1));
        labels.add( stringEtendue.get(2));
        labels.add( stringEtendue.get(3));
        labels.add( stringEtendue.get(4));
        labels.add( stringEtendue.get(5));


        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.animateY(1000);

        barChart.setData(barData);
    }

    @Override
    public boolean onSupportNavigateUp() { // Simule un back depuis la flèche de navigation
        super.onBackPressed();
        return true;
    }

}
