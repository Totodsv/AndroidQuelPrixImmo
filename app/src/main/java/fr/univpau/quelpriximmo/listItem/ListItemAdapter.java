package fr.univpau.quelpriximmo.listItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.univpau.quelpriximmo.R;

public class ListItemAdapter extends ArrayAdapter<ListItem> {

    public ListItemAdapter(Context context, ArrayList<ListItem> listItems) {
        super(context, 0, listItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ListItem listItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        ImageView imageView = convertView.findViewById(R.id.imgMaisonOuAppart);
        TextView valeur_fonciere = (TextView) convertView.findViewById(R.id.prix);
        TextView type_local = (TextView) convertView.findViewById(R.id.type);
        TextView nombre_pieces_principales = (TextView) convertView.findViewById(R.id.nombre_pieces_principales);
        TextView adresse = (TextView) convertView.findViewById(R.id.adresse);
        TextView date = (TextView) convertView.findViewById(R.id.date);

        // Populate the data into the template view using the data object
        //imageView.setImageResource(R.drawable.house);
        imageView.setImageResource(listItem.image);
        valeur_fonciere.setText(listItem.valeur_fonciere);
        type_local.setText(listItem.type_local);
        nombre_pieces_principales.setText(listItem.nombre_pieces_principales);
        adresse.setText(listItem.adresse);
        date.setText(listItem.date_mutation);

        // Return the completed view to render on screen
        return convertView;
    }

}
