package MVC;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.w3c.dom.Text;

public class Principale extends Application {
    @Override
    public void start(Stage stage){
        BorderPane bp= new BorderPane();
        Scene scene = new Scene(bp,935,670);


        //---------TOP----------
        MenuBar menu = new MenuBar();
        Menu fichier = new Menu("Fichier");

//
//        Button effacer = new Button("Effacer Diagramme");
//        Button retour = new Button("Retour arrière (Ctrl+Z)");
//        Button creer = new Button("Créer nouvelle classe");

        menu.getMenus().addAll(fichier);
        bp.setTop(menu);


        //--------BOTTOM---------
        TextArea log = new TextArea("Log");
        log.setPrefHeight(100);
        bp.setBottom(log);
        //


        //---------LEFT----------
        VBox conteneur_arbre = new VBox();

        // Création de l'arbre
        TreeItem<String> item = new TreeItem<>("arbre de dossier");

        item.getChildren().addAll(
                new TreeItem<>("dossier de merde 1"),
                new TreeItem<>("dossier de merde 2"),
                new TreeItem<>("classe de merde 1")

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