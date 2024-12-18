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
        m.enregistrerObservateur(vue);

        //Dossier d = new Dossier("src/main/java/Classes");
        Dossier d = new Dossier("Source/src/main/java/Classes");
        Classe c = (Classe) d.files.getFirst();
        ClasseComplete c1 = c.getClasseComplete();
        m.ajouter_Classe_D(c1);

        m.saveUML();
        //m.ouvrirDossier("src/main/java/Classes");
        m.ouvrirDossier("Source/src/main/java/Classes");




        //---------TOP----------
        MenuBar menu = new MenuBar();
        ToolBar toolbar = new ToolBar();

        Menu fichier = new Menu("Fichier");
        MenuItem ouvrir = new MenuItem("Ouvrir");
        ouvrir.setOnAction(new ControleurBoutonFichier(scene.getWindow()));
        fichier.getItems().add(ouvrir);


//        Button effacer = new Button("Effacer Diagramme");
//        Button retour = new Button("Retour arrière (Ctrl+Z)");
//        Button creer = new Button("Créer nouvelle classe");

        menu.getMenus().addAll(fichier);
//        toolbar.getItems().addAll(effacer,retour,creer);
        bp.setTop(menu);
//        bp.setTop(toolbar);


        //--------BOTTOM---------
        TextArea log = new TextArea("Log");
        log.setPrefHeight(100);
        bp.setBottom(log);
        //


        //---------LEFT----------
        VBox conteneur_arbre = new VBox();
        conteneur_arbre.setPrefWidth(200);


        // Création de l'arbre
        TreeItem<String> item = new TreeItem<>("arbre de dossier");

        item.getChildren().addAll(
                new TreeItem<>("dossier 1"),
                new TreeItem<>("dossier 2"),
                new TreeItem<>("classe 1")

        );

        // Ajout de l'arbre
        TreeView<String> arbre = new TreeView<>(item);
        VBox.setVgrow(arbre, Priority.ALWAYS);

        conteneur_arbre.getChildren().add(arbre);
        bp.setLeft(conteneur_arbre);



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