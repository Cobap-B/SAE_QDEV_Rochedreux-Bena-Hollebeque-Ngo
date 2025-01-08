package Classes;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Methode implements Serializable {
    private String nom;
    private String acces;
    private String type_retour;
    private boolean visibilite;
    private ArrayList<Parametre> parametres;

    public Methode(String nom, String acces, String type_retour, ArrayList<Parametre> parametres) {
        this.nom = nom;
        this.acces = acces;
        this.type_retour = type_retour;
        this.visibilite = true;
        this.parametres = parametres;
    }

    public String getNom() {
        return nom;
    }

    public String getAcces() {
        return acces;
    }

    public String getType_retour() {
        return type_retour;
    }


    public boolean isVisibilite() {
        return visibilite;
    }

    public ArrayList<Parametre> getParametres() {
        return parametres;
    }

    public void setVisibilite(boolean visibilite) {
        this.visibilite = visibilite;
    }

    @Override
    public String toString() {
        String s = (getAcces() +" " + getNom());
        s+= "(";
        if (getParametres().size()>1){
            //Boucle pour les paramètre
            for (Parametre parametre : getParametres()) {
                s+=(parametre.getType() +" "+parametre.getNom());
                s+=",";
            }
            s= s.substring(0, s.length()-1);
        }
        s+=(")"+" : "+ getType_retour()+"\n");
        return s;
    }

}
