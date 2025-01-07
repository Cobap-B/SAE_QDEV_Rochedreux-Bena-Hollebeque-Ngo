package MVC;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

//Quand on clic sur le Pane de l'interface avec un clic droit, un menu textuel s'ffiche
public class ControleurBoutonDroit implements EventHandler<MouseEvent> {
    private ContextMenu contextMenu;


    public ControleurBoutonDroit(Model model) {
        this.contextMenu = new ContextMenu(); // Initialise ici

        //--------menu pour les attributs---------
        Menu attributs = new Menu("Attributs");
        CheckMenuItem afficherAttributs = new CheckMenuItem("Afficher les Attributs");
        afficherAttributs.setSelected(true);

        //
        // ACTION
        //
        //


        MenuItem ajouterAttribut = new MenuItem("Ajouter un Attribut");
        ajouterAttribut.setOnAction(e -> System.out.println("Ajouter un Attribut"));

        attributs.getItems().addAll(afficherAttributs, ajouterAttribut);

        //--------menu pour les méthodes---------
        Menu methodes = new Menu("Méthodes");
        CheckMenuItem afficherMethodes = new CheckMenuItem("Afficher les Méthodes");
        afficherMethodes.setSelected(true);

        //
        // ACTION
        //
        //

        MenuItem ajouterMethodes = new MenuItem("Ajouter une Méthode");
        ajouterMethodes.setOnAction(e -> System.out.println("Ajouter une Méthode"));

        methodes.getItems().addAll(afficherMethodes, ajouterMethodes);

        //--------menu pour les dépendances---------
        Menu dependances = new Menu("Dépendances");
        CheckMenuItem afficherDependances = new CheckMenuItem("Afficher les Dépendances");
        afficherDependances.setSelected(true);

        //
        // ACTION
        //
        //

        dependances.getItems().add(afficherDependances);

        // Ajout des menus au contextMenu
        contextMenu.getItems().addAll(attributs, methodes, dependances);
    }


    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().name().equals("SECONDARY")) {
            if(mouseEvent.getSource().getClass().equals(VueClasse.class))
                contextMenu.show((Node) mouseEvent.getSource(), mouseEvent.getScreenX(), mouseEvent.getScreenY());
        }

        if (mouseEvent.getButton().name().equals("PRIMARY")) {
            contextMenu.hide();
        }

    }

}
