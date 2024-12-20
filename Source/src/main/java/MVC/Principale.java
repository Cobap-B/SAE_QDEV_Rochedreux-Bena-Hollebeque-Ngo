package MVC;

import Classes.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Principale extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        BorderPane bp= new BorderPane();
        Scene scene = new Scene(bp,935,670);

        //TEST
        Model m = new Model();

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
        MenuItem exporterPNG = new MenuItem("Exporter PNG");

        Menu fichier = new Menu("Fichier");

        MenuItem ouvrir = new MenuItem("Ouvrir");
        ouvrir.setOnAction(new ControleurBoutonFichier(scene.getWindow(), m));
        exporterUML.setOnAction(new ControleurBoutonFichier(scene.getWindow(), m));
        exporterPNG.setOnAction(new ControleurBoutonFichier(scene.getWindow(), m));
        fichier.getItems().addAll(ouvrir, exporterUML, exporterPNG);


//        Button effacer = new Button("Effacer Diagramme");
//        Button retour = new Button("Retour arrière (Ctrl+Z)");
//        Button creer = new Button("Créer nouvelle classe");

        menu.getMenus().addAll(fichier);
//        toolbar.getItems().addAll(effacer,retour,creer);
        bp.setTop(menu);
//        bp.setTop(toolbar);


        //--------BOTTOM---------
        log.setPrefHeight(100);
        log.setEditable(false);
        bp.setBottom(log);
        //


//        //---------LEFT----------
//        VBox conteneur_arbre = new VBox();
//        conteneur_arbre.setPrefWidth(200);
//
//
//        // Création de l'arbre
//        TreeItem<String> item = new TreeItem<>("arbre de dossier");
//
//        item.getChildren().addAll(
//                new TreeItem<>("dossier 1"),
//                new TreeItem<>("dossier 2"),
//                new TreeItem<>("classe 1")
//
//        );
//
//        // Ajout de l'arbre
//        TreeView<String> arbre = new TreeView<>(item);
//        VBox.setVgrow(arbre, Priority.ALWAYS);
//
//        conteneur_arbre.getChildren().add(arbre);
//        bp.setLeft(conteneur_arbre);
        bp.setLeft(vueArbre);
        vueArbre.setOnMouseClicked(new ControleurArbre(m));



        //----------CENTER----------
        Pane diagramme_interface = new Pane();
    //    diagramme_interface.setBorder();
        bp.setCenter(diagramme_interface);

        stage.setTitle("MVC");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}