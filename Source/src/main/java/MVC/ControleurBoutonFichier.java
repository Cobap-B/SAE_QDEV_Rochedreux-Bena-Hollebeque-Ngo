package MVC;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public class ControleurBoutonFichier implements EventHandler<ActionEvent> {
    private Window w;
    private Model m;
    private VueDiagramme v;

    public ControleurBoutonFichier(Window w, Model model, VueDiagramme v) {
        this.w = w;
        this.m = model;
        this.v = v;
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
            case "Exporter PNG plantUML":
                try {
                    m.saveUML();
                    m.savePNG();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "Exporter Diagramme":
                try {
                    m.saveDiagramme(v);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "Save":
                try {
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("pipotam", "*.pipotam");
                    fileChooser.getExtensionFilters().add(filter);
                    fileChooser.setSelectedExtensionFilter(filter);
                    File saveFile = fileChooser.showSaveDialog(w);
                    if(saveFile != null) {
                        System.out.println(saveFile.getAbsolutePath() + " " + saveFile.getName());
                        m.save(saveFile.getAbsolutePath());
                    }
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case "Load":
                try {
                    FileChooser fileChooser = new FileChooser();
                    File f = fileChooser.showOpenDialog(w);
                    if(f != null) {
                        // on regarde l'extension
                        String[] s = f.getName().split("\\.");
                        if(s[1].equals("pipotam")){
                            m.load(f.getAbsolutePath());
                        }
                    }
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }
}
