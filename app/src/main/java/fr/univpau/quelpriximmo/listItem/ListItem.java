package fr.univpau.quelpriximmo.listItem;

import android.widget.ImageView;

public class ListItem {
    public int image;
    public String valeur_fonciere;
    public String type_local;
    public String nombre_pieces_principales;
    public String adresse;
    public String date_mutation;
    public ListItem( int image, String valeur_fonciere, String type_local,String nombre_pieces_principales, String adresse, String date_mutation){
        this.image=image;
        this.valeur_fonciere=valeur_fonciere;
        this.type_local=type_local;
        this.nombre_pieces_principales=nombre_pieces_principales;
        this.adresse=adresse;
        this.date_mutation=date_mutation;
    }
}
