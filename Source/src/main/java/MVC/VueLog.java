package MVC;

import javafx.scene.control.TextArea;

public class VueLog extends TextArea implements Observateur{

    Model m;

    public VueLog(Model modele){
        this.m = modele;
    }

    @Override
    public void actualiser(Sujet s) {
        this.clear();
        for (String log : m.getLogs()) {
            this.setText(this.getText()+log+"\n");
        }
        this.setScrollTop(Double.MAX_VALUE);
    }
}
