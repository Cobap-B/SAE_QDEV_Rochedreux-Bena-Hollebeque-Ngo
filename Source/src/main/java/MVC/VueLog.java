package MVC;

import javafx.scene.control.TextArea;

import java.io.Serializable;

public class VueLog extends TextArea implements Observateur{

    Model m;

    /**
     *
     * @param modele
     */
    public VueLog(Model modele){
        this.m = modele;
    }

    /**
     * Affiche divers information en bas de l'application
     * @param s
     */
    @Override
    public void actualiser(Sujet s) {
        this.clear();
        for (String log : m.getLogs()) {
            this.setText(this.getText()+log+"\n");
        }
        this.setScrollTop(Double.MAX_VALUE);
    }
}
