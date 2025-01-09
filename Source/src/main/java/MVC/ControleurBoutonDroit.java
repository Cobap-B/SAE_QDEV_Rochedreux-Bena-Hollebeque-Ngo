package MVC;

import Classes.Attribut;
import Classes.ClasseComplete;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

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
        ajouterAttribut.setOnAction(e -> {
            if (contextMenu.getOwnerNode() instanceof VueClasse) {
                VueClasse vueClasse = (VueClasse) contextMenu.getOwnerNode();
                ClasseComplete classeComplete = vueClasse.getClasseComplete();

                // Créer la boîte de dialogue pour ajouter un attribut
                Dialog<Attribut> dialog = new Dialog<>();
                dialog.setTitle("Ajouter un Attribut");
                dialog.setHeaderText("Entrez les détails de l'attribut");

                // Créer les champs de saisie
                TextField nomField = new TextField();
                nomField.setPromptText("Nom de l'attribut");

                TextField typeField = new TextField();
                typeField.setPromptText("Type de l'attribut");

                CheckBox accessibleCheckBox = new CheckBox("Accessible");

                // Créer un layout
                VBox vbox = new VBox(nomField, typeField, accessibleCheckBox);
                dialog.getDialogPane().setContent(vbox);

                // Ajouter les boutons
                ButtonType ajouterButtonType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(ajouterButtonType, ButtonType.CANCEL);

                // Gestion de l'événement pour le bouton "Ajouter"
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == ajouterButtonType) {
                        String nom = nomField.getText();
                        String type = typeField.getText();
                        String acces = accessibleCheckBox.isSelected() ? "+" : "-";
                        return new Attribut(nom, acces, type);
                    }
                    return null;
                });

                // Afficher la boîte de dialogue et récupérer le résultat
                dialog.showAndWait().ifPresent(nouvelAttribut -> {
                    classeComplete.ajoutAttribut(nouvelAttribut);
                    model.notifierObservateurs(); // Notifier les observateurs après ajout
                });
            }
        });
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
        ajouterMethodes.setOnAction(e -> System.out.println("Ajouter une méthode"));
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
                System.out.println("Affichage des dependances : " + classeComplete.isVisible_Dependances());
                System.out.println(afficherDependances.isSelected());

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

                // Mise à jour "Afficher les Attributs"
                ClasseComplete classeComplete = vueClasse.getClasseComplete();
                CheckMenuItem afficherAttributs = (CheckMenuItem) ((Menu) contextMenu.getItems().get(0)).getItems().get(0);
                afficherAttributs.setSelected(classeComplete.isVisible_Attributs());

                // Mise à jour "Afficher les Méthodes"
                CheckMenuItem afficherMethodes = (CheckMenuItem) ((Menu) contextMenu.getItems().get(1)).getItems().get(0);
                afficherMethodes.setSelected(classeComplete.isVisible_Methodes());

                // Mise à jour de "Afficher les "
                CheckMenuItem afficherDependances = (CheckMenuItem) ((Menu) contextMenu.getItems().get(2)).getItems().get(0);
                afficherDependances.setSelected(classeComplete.isVisible_Dependances());

                contextMenu.show((Node) mouseEvent.getSource(), mouseEvent.getScreenX(), mouseEvent.getScreenY());
            }
        }

        if (mouseEvent.getButton().name().equals("PRIMARY")) {
            contextMenu.hide();
        }
    }
}
