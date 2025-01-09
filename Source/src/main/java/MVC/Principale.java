package MVC;

import Classes.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Principale extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        BorderPane bp= new BorderPane();
        Scene scene = new Scene(bp,935,670);

        //TEST
        Model m = new Model();
        VueDiagramme diagramme_interface  = new VueDiagramme(m);

        VueConsole vue = new VueConsole();
        VueArbre vueArbre = new VueArbre(m);
        m.enregistrerObservateur(vue);
        m.enregistrerObservateur(vueArbre);

        VueLog log = new VueLog(m);
        m.enregistrerObservateur(log);



        //---------TOP----------
        MenuBar menu = new MenuBar();
        ToolBar toolbar = new ToolBar();

        MenuItem exporterUML = new MenuItem("Exporter plantUML");
        MenuItem exporterPNG = new MenuItem("Exporter PNG plantUML");
        MenuItem exporterDiagramme = new MenuItem("Exporter Diagramme");
        MenuItem save = new MenuItem("Save");
        MenuItem load = new MenuItem("Load");

        Menu fichier = new Menu("Fichier");

        MenuItem ouvrir = new MenuItem("Ouvrir");
        ouvrir.setOnAction(new ControleurBoutonFichier(scene.getWindow(), m, diagramme_interface));
        exporterUML.setOnAction(new ControleurBoutonFichier(scene.getWindow(), m, diagramme_interface));
        exporterPNG.setOnAction(new ControleurBoutonFichier(scene.getWindow(), m, diagramme_interface));
        exporterDiagramme.setOnAction(new ControleurBoutonFichier(scene.getWindow(), m, diagramme_interface));
        save.setOnAction(new ControleurBoutonFichier(scene.getWindow(), m, diagramme_interface));
        load.setOnAction(new ControleurBoutonFichier(scene.getWindow(), m, diagramme_interface));
        fichier.getItems().addAll(ouvrir, exporterUML, exporterPNG, exporterDiagramme, save, load);

        ControleurOption option = new ControleurOption(m);
        Button effacer = new Button("Effacer Diagramme");
        effacer.setOnAction(option);
        Button retour = new Button("Retour arrière (Ctrl+Z)");
        Button creer = new Button("Créer nouvelle classe");

        menu.getMenus().addAll(fichier);
        toolbar.getItems().addAll(menu, effacer,retour,creer);
//        bp.setTop(menu);
        bp.setTop(toolbar);


        //--------BOTTOM---------
        log.setPrefHeight(100);
        log.setEditable(false);
        bp.setBottom(log);
        //


//        //---------LEFT----------
        bp.setLeft(vueArbre);
        vueArbre.setOnDragDetected(new ControleurArbre(m));



        //----------CENTER----------
        ControleurDiagrammeDrag cdd = new ControleurDiagrammeDrag(m);

        ControleurBoutonDroit cbd = new ControleurBoutonDroit(m);

        diagramme_interface.setOnDragOver(cdd);
        diagramme_interface.setOnDragDropped(cdd);
    //    diagramme_interface.setBorder();
        m.enregistrerObservateur(diagramme_interface);

        bp.setCenter(diagramme_interface);

        ControleurBoutonDroit boutondroit = new ControleurBoutonDroit(m);
        diagramme_interface.setOnMouseClicked(boutondroit);

        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<ClasseComplete> classes = m.getDiagramme();
            for (ClasseComplete c : classes) {
                c.setCo(c.getX(), c.getY(), diagramme_interface.getWidth() - c.getTailleX(), diagramme_interface.getHeight() - c.getTailleY());
            }
            m.notifierObservateurs();
        });

        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<ClasseComplete> classes = m.getDiagramme();
            for (ClasseComplete c : classes) {
                c.setCo(c.getX(), c.getY(), diagramme_interface.getWidth() - c.getTailleX(), diagramme_interface.getHeight() - c.getTailleY());
            }
            m.notifierObservateurs();
        });



        stage.setTitle("MVC");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}