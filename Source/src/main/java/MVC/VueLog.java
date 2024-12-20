package MVC;

import javafx.scene.control.TextArea;

public class VueLog extends TextArea implements Observateur{

    Model m;

    public VueLog(Model modele){
        this.m = modele;
    }

    @Override
    public void actualiser(Sujet s) {
        this.setText(this.getText() + m.getLogs().getLast() + "\n");
    }
}
