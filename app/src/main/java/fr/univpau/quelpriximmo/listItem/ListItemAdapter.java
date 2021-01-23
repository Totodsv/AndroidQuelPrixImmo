package fr.univpau.quelpriximmo.listItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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
        TextView addEmail = (TextView) convertView.findViewById(R.id.email);
        TextView numMobile = (TextView) convertView.findViewById(R.id.mobile);


        // Populate the data into the template view using the data object
        addEmail.setText(listItem.email);
        numMobile.setText(listItem.mobile);
        //tacheBox.isChecked();

        // Return the completed view to render on screen
        return convertView;
    }

}
