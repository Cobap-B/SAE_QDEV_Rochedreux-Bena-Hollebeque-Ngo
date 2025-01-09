package MVC;

import Classes.Attribut;
import Classes.ClasseComplete;
import Classes.Dependance;
import Classes.Methode;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Optional;


public class Controleur_Classe_Boutton implements EventHandler<MouseEvent> {
    private final ContextMenu contextMenu;
    private final Model model;
    private double X_menu;
    private double Y_menu;


    public Controleur_Classe_Boutton(Model model) {
        this.model = model;

        // Créer le menu contextuel
        contextMenu = new ContextMenu();
        MenuItem ajouterClasse = new MenuItem("Ajouter une Classe");

        // Action pour ajouter une nouvelle classe
        ajouterClasse.setOnAction(e -> ajouterClasse());

        contextMenu.getItems().add(ajouterClasse);
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            // menu contextuel
            contextMenu.show((javafx.scene.Node) event.getSource(), event.getScreenX(), event.getScreenY());

            // Convertir les coordonnées globales en coordonnées locales
            Node source = (javafx.scene.Node) event.getSource();
            Point2D localPoint = source.sceneToLocal(event.getSceneX(), event.getSceneY());

            // Sauvegarder pour positionner la classe
            X_menu = localPoint.getX();
            Y_menu = localPoint.getY();
        } else if (event.isPrimaryButtonDown()) {
            contextMenu.hide();
        }
    }

    private void ajouterClasse() {
        //formulaire pour la saisie
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ajouter une classe");
        dialog.setHeaderText("Entrez le nom de la classe");
        dialog.setContentText("Nom de la classe:");
        dialog.setContentText("Nom de la classe:");


        // Récupérer le nom de la classe
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nom -> {

            // Créer les listes d'attributs/methodes et dependances vide pour la classe
            ArrayList<Attribut> attributs = new ArrayList<>();
            ArrayList<Methode> methodes = new ArrayList<>();
            ArrayList<Dependance> dependances = new ArrayList<>();

            // Ajouter la classe au modèle
            model.ajouter_squelette_Classe(nom, "class", attributs, methodes, dependances, X_menu , Y_menu);
        });
    }

}
