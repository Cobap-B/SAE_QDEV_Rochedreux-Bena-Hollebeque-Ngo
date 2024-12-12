package MVC;

import Classes.*;
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

        Dossier d = new Dossier("src/main/java/Classes");
        Classe c = (Classe) d.files.getFirst();
        ClasseComplete c1 = c.getClasseComplete();
        System.out.println(c1.getUml());


        //---------TOP----------
        MenuBar menu = new MenuBar();
        ToolBar toolbar = new ToolBar();

        Menu fichier = new Menu("Fichier");


        Button effacer = new Button("Effacer Diagramme");
        Button retour = new Button("Retour arrière (Ctrl+Z)");
        Button creer = new Button("Créer nouvelle classe");

        menu.getMenus().addAll(fichier);
        toolbar.getItems().addAll(effacer,retour,creer);
        bp.setTop(menu);
        bp.setTop(toolbar);


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