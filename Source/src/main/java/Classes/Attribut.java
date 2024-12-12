package Classes;

public class Attribut {

    private String nom;
    private String type;
    private String acces;
    private boolean visibilite;

    public Attribut(String nom, String acces, String type) {
        this.nom = nom;
        this.type = type;
        this.access = acces;
        this.visibilite = true;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public boolean isVisibilite() {
        return visibilite;
    }

    public String getAcces() {
        return acces;
    }
}
