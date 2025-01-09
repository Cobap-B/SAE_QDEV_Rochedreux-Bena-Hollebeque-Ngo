package Classes;

import java.io.Serializable;

public class Parametre implements Serializable {
    private String nom;
    private String type;


    public Parametre(String nom, String type) {
        this.nom = nom;
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }
}

