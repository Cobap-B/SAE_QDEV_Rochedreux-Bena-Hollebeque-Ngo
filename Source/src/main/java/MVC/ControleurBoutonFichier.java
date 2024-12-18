package MVC;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;

public class ControleurBoutonFichier implements EventHandler<ActionEvent> {
    Window w;

    public ControleurBoutonFichier(Window w) {
        this.w = w;
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        MenuItem b = (MenuItem) actionEvent.getSource();
        switch (b.getText()) {
            case "Ouvrir":
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = directoryChooser.showDialog(w);
                if(selectedDirectory != null) {System.out.println(selectedDirectory.getAbsolutePath());}
                break;
        }
    }
}
