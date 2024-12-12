package Classes;

public class Dependance {

    private String depend;
    private String type;
    private boolean visibilite;

    public Dependance(String depend, String type) {
        this.depend = depend;
        this.type = type;
        this.visibilite = true;
    }

    public String getDepend() {
        return depend;
    }

    public String getType() {
        return type;
    }

    public boolean isVisibilite() {
        return visibilite;
    }
}
