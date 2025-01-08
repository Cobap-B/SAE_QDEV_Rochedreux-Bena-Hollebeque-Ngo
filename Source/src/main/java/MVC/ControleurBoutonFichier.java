package MVC;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public class ControleurBoutonFichier implements EventHandler<ActionEvent> {
    private Window w;
    Model m;

    public ControleurBoutonFichier(Window w, Model model) {
        this.w = w;
        this.m = model;
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        MenuItem b = (MenuItem) actionEvent.getSource();
        switch (b.getText()) {
            case "Ouvrir":
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = directoryChooser.showDialog(w);
                if(selectedDirectory != null) {m.ouvrirDossier(selectedDirectory.getAbsolutePath());}
                break;
            case "Exporter plantUML":
                try {
                    m.saveUML();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "Exporter PNG":
                try {
                    m.saveUML();
                    m.savePNG();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }
}
