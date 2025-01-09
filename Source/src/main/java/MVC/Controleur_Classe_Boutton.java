package MVC;

import Classes.*;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
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
        if (event.isPrimaryButtonDown()) {
            if (!(event.getSource() instanceof VueClasse)) {
                // Afficher le menu contextuel pour ajouter une classe
                contextMenu.show((javafx.scene.Node) event.getSource(), event.getScreenX(), event.getScreenY());

                // Convertir les coordonnées globales en coordonnées locales
                Node source = (javafx.scene.Node) event.getSource();
                Point2D localPoint = source.sceneToLocal(event.getSceneX(), event.getSceneY());

                // Sauvegarder les coordonnées pour positionner la nouvelle classe
                X_menu = localPoint.getX();
                Y_menu = localPoint.getY();
            }
        }

        if (event.getButton().name().equals("PRIMARY")) {
            contextMenu.hide();
        }
    }

    private void ajouterClasse() {
        // Formulaire pour la saisie du nom de la classe
        TextInputDialog dialogNom = new TextInputDialog();
        dialogNom.setTitle("Ajouter une classe");
        dialogNom.setHeaderText("Entrez le nom de la classe");
        dialogNom.setContentText("Nom de la classe:");

        Optional<String> resultNom = dialogNom.showAndWait();
        resultNom.ifPresent(nom -> {
            // Formulaire pour sélectionner le type de la classe
            TextInputDialog dialogType = new TextInputDialog();
            dialogType.setTitle("Type de la classe");
            dialogType.setHeaderText("Entrez le type de la classe");
            dialogType.setContentText("Type (class, abstract, interface):");

            Optional<String> resultType = dialogType.showAndWait();
            resultType.ifPresent(type -> {
                if (!type.equals("class") && !type.equals("abstract") && !type.equals("interface")) {
                    System.err.println("Type invalide : " + type);
                }

                // Créer les listes d'attributs/méthodes et dépendances vides pour la classe
                ArrayList<Attribut> attributs = new ArrayList<>();
                ArrayList<Methode> methodes = new ArrayList<>();
                ArrayList<Dependance> dependances = new ArrayList<>();

                // Ajouter la classe au modèle avec les coordonnées sauvegardées
                model.ajouter_squelette_Classe(nom, type, attributs, methodes, dependances, X_menu, Y_menu);

                // Générer un fichier Java pour la classe
                try {
                    GenerateurFichierClasse.genererFichierClasse(nom, type, "Source/classGenerer");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }



}
