package Classes;

public class Attribut {

    private String nom;
    private String type;
    private String acces;
    private String nombre;
    private boolean visibilite;

    public Attribut(String nom, String acces, String type) {
        this.nom = nom;
        this.type = type;
        this.acces = acces;
        this.visibilite = true;
    }

    public Attribut(String nom, String acces, String type, String nb) {
        this.nom = nom;
        this.type = type;
        this.acces = acces;
        this.nombre = nb;
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

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return (getAcces() + " " + getType() + " " + getNom() + "\n");
    }
}
