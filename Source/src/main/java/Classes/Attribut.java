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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAcces(String acces) {
        this.acces = acces;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setVisibilite(boolean visibilite) {
        this.visibilite = visibilite;
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
