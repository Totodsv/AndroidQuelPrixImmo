package fr.univpau.quelpriximmo.listItem;

public class ListItem {
    public String valeur_fonciere;
    public String type_local;
    public String nombre_pieces_principales;
    public String adresse;
    public String date_mutation;
    public ListItem( String valeur_fonciere, String type_local,String nombre_pieces_principales, String adresse, String date_mutation){
        this.valeur_fonciere=valeur_fonciere;
        this.type_local=type_local;
        this.nombre_pieces_principales=nombre_pieces_principales;
        this.adresse=adresse;
        this.date_mutation=date_mutation;
    }
}
