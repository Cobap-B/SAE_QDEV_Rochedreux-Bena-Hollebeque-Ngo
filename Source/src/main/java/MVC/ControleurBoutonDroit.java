package MVC;

import Classes.ClasseComplete;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

public class ControleurBoutonDroit implements EventHandler<MouseEvent> {
    private ContextMenu contextMenu;

    public ControleurBoutonDroit(Model model) {
        this.contextMenu = new ContextMenu();

        // --------menu pour les attributs---------

        //afficher les attributs
        Menu attributs = new Menu("Attributs");
        CheckMenuItem afficherAttributs = new CheckMenuItem("Afficher les Attributs");

        afficherAttributs.setOnAction(e -> {
            if (contextMenu.getOwnerNode() instanceof VueClasse) {
                VueClasse vueClasse = (VueClasse) contextMenu.getOwnerNode();
                ClasseComplete classeComplete = vueClasse.getClasseComplete();

                classeComplete.setVisible_Attributs(afficherAttributs.isSelected());
                model.notifierObservateurs();
            }
        });

        //ajouter un attribut
        MenuItem ajouterAttribut = new MenuItem("Ajouter un Attribut");

        //ACTION a implémenter
        //
        ajouterAttribut.setOnAction(e -> System.out.println("Ajouter un Attribut"));
        //
        //

        attributs.getItems().addAll(afficherAttributs, ajouterAttribut);



        // --------menu pour les méthodes---------

        //afficher les méthodes
        Menu methodes = new Menu("Méthodes");
        CheckMenuItem afficherMethodes = new CheckMenuItem("Afficher les Méthodes");

        afficherMethodes.setOnAction(e -> {
            if (contextMenu.getOwnerNode() instanceof VueClasse) {
                VueClasse vueClasse = (VueClasse) contextMenu.getOwnerNode();
                ClasseComplete classeComplete = vueClasse.getClasseComplete();
                classeComplete.setVisible_Methodes(afficherMethodes.isSelected());
                model.notifierObservateurs();
            }
        });

        //Ajouter une méthode
        MenuItem ajouterMethodes = new MenuItem("Ajouter une Méthode");

        //ACTION a implémenter
        //
        ajouterAttribut.setOnAction(e -> System.out.println("Ajouter une méthode"));
        //
        //

        methodes.getItems().addAll(afficherMethodes, ajouterMethodes);



        // --------menu pour les dépendances---------
        Menu dependances = new Menu("Dépendances");
        CheckMenuItem afficherDependances = new CheckMenuItem("Afficher les Dépendances");

        afficherDependances.setOnAction(e -> {
            if (contextMenu.getOwnerNode() instanceof VueClasse) {
                VueClasse vueClasse = (VueClasse) contextMenu.getOwnerNode();
                ClasseComplete classeComplete = vueClasse.getClasseComplete();
                classeComplete.setVisible_Dependances(afficherDependances.isSelected());
                model.notifierObservateurs();
            }
        });


        dependances.getItems().add(afficherDependances);

        // Ajout des menus au contextMenu
        contextMenu.getItems().addAll(attributs, methodes, dependances);


        //Supprimer la classe courante du diagramme

        MenuItem supprimer_classe = new MenuItem("Supprimer la classe");

        supprimer_classe.setOnAction(e -> {
            if (contextMenu.getOwnerNode() instanceof VueClasse) {
                VueClasse vueClasse = (VueClasse) contextMenu.getOwnerNode();
                ClasseComplete classeComplete = vueClasse.getClasseComplete();
                if(model.getDiagramme().contains(classeComplete)){
                    model.getDiagramme().remove(classeComplete);
                }
                model.notifierObservateurs();
            }
        });



        contextMenu.getItems().add(supprimer_classe);


    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().name().equals("SECONDARY")) {
            if (mouseEvent.getSource() instanceof VueClasse) {
                VueClasse vueClasse = (VueClasse) mouseEvent.getSource();

                // Mise à jour de l'état de "Afficher les Attributs"
                ClasseComplete classeComplete = vueClasse.getClasseComplete();
                CheckMenuItem afficherAttributs = (CheckMenuItem) ((Menu) contextMenu.getItems().get(0)).getItems().get(0);
                afficherAttributs.setSelected(classeComplete.isVisible_Attributs());

                // Mise à jour de l'état de "Afficher les Méthodes"
                CheckMenuItem afficherMethodes = (CheckMenuItem) ((Menu) contextMenu.getItems().get(1)).getItems().get(0);
                afficherMethodes.setSelected(classeComplete.isVisible_Methodes());

                // Mise à jour de l'état de "Afficher les Méthodes"
                CheckMenuItem afficherDependances = (CheckMenuItem) ((Menu) contextMenu.getItems().get(1)).getItems().get(0);
                afficherDependances.setSelected(classeComplete.isVisible_Dependances());

                contextMenu.show((Node) mouseEvent.getSource(), mouseEvent.getScreenX(), mouseEvent.getScreenY());
            }
        }

        if (mouseEvent.getButton().name().equals("PRIMARY")) {
            contextMenu.hide();
        }
    }
}
