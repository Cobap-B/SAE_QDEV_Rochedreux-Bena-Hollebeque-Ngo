package Classes;

public class Attribut {

    private String nom;
    private String type;
    private boolean visibilite;

    public Attribut(String nom, String type) {
        this.nom = nom;
        this.type = type;
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
}
