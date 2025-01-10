package Classes;

import java.io.Serializable;

public class Dependance implements Serializable {

    private String depend;
    private String type;
    private boolean visibilite;

    /**
     *
     * @param depend
     * @param type
     */
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

    public void setVisibilite(boolean visibilite) {
        this.visibilite = visibilite;
    }
}
