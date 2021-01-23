package fr.univpau.quelpriximmo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univpau.quelpriximmo.listItem.ListItem;
import fr.univpau.quelpriximmo.listItem.ListItemAdapter;

import static android.content.ContentValues.TAG;

public class GetImmo extends AsyncTask<Void, Void, Void> {

    private ProgressDialog progress;
    private MainActivity screen;
    ArrayList<Object> contactList = new ArrayList<>();

    public GetImmo(MainActivity s) {
        this.screen = s;
        this.progress = new ProgressDialog(this.screen);
    }

    //private static final String BASE_URL = "http://api.cquest.org/dvf?lat=43.30&lon=-0.36";
    @Override
    protected void onPreExecute() {
        progress.setTitle("Veuillez patienter");
        progress.setMessage("Récupération des données en cours...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        HttpHandler sh = new HttpHandler();
        // Making a request to url and getting response
        String url = "http://api.androidhive.info/contacts/";
        String jsonStr = sh.makeServiceCall(url);

        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("contacts");

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    String id = c.getString("id");
                    String name = c.getString("name");
                    String email = c.getString("email");
                    String address = c.getString("address");
                    String gender = c.getString("gender");

                    // Phone node is JSON Object
                    JSONObject phone = c.getJSONObject("phone");
                    String mobile = phone.getString("mobile");
                    String home = phone.getString("home");
                    String office = phone.getString("office");

                    // tmp hash map for single contact
                    HashMap<String, String> contact = new HashMap<>();

                    // adding each child node to HashMap key => value
                    contact.put("id", id);
                    contact.put("name", name);
                    contact.put("email", email);
                    contact.put("mobile", mobile);

                    // adding contact to contact list
                    contactList.add(contact);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                /*
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                })*/;

            }

        } else {
            Log.e(TAG, "Couldn't get json from server.");
            /*runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG).show();
                }
            });*/
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if(progress.isShowing()) progress.dismiss();

        Intent i = new Intent(); /* Intent de type direct */
        i.putExtra("Valeur", contactList); /* Donnée additionnelle */
        i.setClass(screen, Resultat.class);
        screen.startActivity(i); /* Poussé sur le bus */


        //this.screen.populate(result);
        //System.out.println("ICIIIIIIIIIIIIIIIIIIIIIIIIIII"+result);
        /*ListAdapter adapter = new SimpleAdapter(
                screen, (List<? extends Map<String, ?>>) arrayOfItems,
                R.layout.list_item, new String[]{"name", "email",
                "mobile"}, new int[]{R.id.email, R.id.mobile});
        listView.setAdapter(this.adapter);*/
   }
}
