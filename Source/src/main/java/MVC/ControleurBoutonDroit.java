package MVC;

import Classes.*;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

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

                model.retour_save();
                classeComplete.setVisible_Attributs(afficherAttributs.isSelected());
                model.notifierObservateurs();
            }
        });

        //ajouter un attribut
        MenuItem ajouterAttribut = new MenuItem("Ajouter un Attribut");

        ajouterAttribut.setOnAction(e -> {
            if (contextMenu.getOwnerNode() instanceof VueClasse) {
                VueClasse vueClasse = (VueClasse) contextMenu.getOwnerNode();
                ClasseComplete classeComplete = vueClasse.getClasseComplete();

                // Créer la boîte de dialogue pour ajouter un attribut
                Dialog<Attribut> dialog = new Dialog<>();
                dialog.setTitle("Ajouter un Attribut");
                dialog.setHeaderText("Entrez les détails de l'attribut");
                TextField nomAttribut = new TextField();
                nomAttribut.setPromptText("Nom de l'attribut");
                TextField nomType = new TextField();
                nomType.setPromptText("Type de l'attribut");
                CheckBox accessible = new CheckBox("Accessible");
                VBox vbox = new VBox(nomAttribut, nomType, accessible);
                dialog.getDialogPane().setContent(vbox);
                ButtonType ajouterButtonType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(ajouterButtonType, ButtonType.CANCEL);

                // Gestion de l'évenement pour le bouton Ajouter
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == ajouterButtonType) {
                        String nom = nomAttribut.getText();
                        String type = nomType.getText();
                        String acces = "";
                        if(accessible.isSelected())
                            acces = "+";
                        else
                            acces = "-";
                        return new Attribut(nom, acces, type);
                    }
                    return null;
                });

                // Afficher la boîte de dialogue et récupérer le résultat
                dialog.showAndWait().ifPresent(nouvelAttribut -> {
                    model.retour_save();
                    classeComplete.ajoutAttribut(nouvelAttribut);
                    try{
                        //ajouter dans le .java
                        GenerateurFichierClasse.ajouterAttribut(classeComplete.getNom(), "Source/classGenerer", GenerateurFichierClasse.convertirAccessibilite(nouvelAttribut.getAcces()), nouvelAttribut.getType(), nouvelAttribut.getNom());
                    }catch (IOException e2){
                        e2.printStackTrace();
                    }
                    model.notifierObservateurs();
                });
            }
        });

        attributs.getItems().addAll(afficherAttributs, ajouterAttribut);



        // --------menu pour les méthodes---------

        //afficher les méthodes
        Menu methodes = new Menu("Méthodes");
        CheckMenuItem afficherMethodes = new CheckMenuItem("Afficher les Méthodes");

        afficherMethodes.setOnAction(e -> {
            if (contextMenu.getOwnerNode() instanceof VueClasse) {
                VueClasse vueClasse = (VueClasse) contextMenu.getOwnerNode();
                ClasseComplete classeComplete = vueClasse.getClasseComplete();

                model.retour_save();
                classeComplete.setVisible_Methodes(afficherMethodes.isSelected());
                model.notifierObservateurs();
            }
        });

        //Ajouter une méthode
        MenuItem ajouterMethode = new MenuItem("Ajouter une Méthode");

        ajouterMethode.setOnAction(e -> {
            if (contextMenu.getOwnerNode() instanceof VueClasse) {
                VueClasse vueClasse = (VueClasse) contextMenu.getOwnerNode();
                ClasseComplete classeComplete = vueClasse.getClasseComplete();

                // Créer la boîte de dialogue pour ajouter une méthode
                Dialog<Methode> dialog = new Dialog<>();
                dialog.setTitle("Ajouter une Méthode");
                dialog.setHeaderText("Entrez les détails de la méthode");
                TextField nomMethode = new TextField();
                nomMethode.setPromptText("Nom de la méthode");
                TextField nomTypeRetour = new TextField();
                nomTypeRetour.setPromptText("Type de retour");
                TextField parametres = new TextField();
                parametres.setPromptText("Paramètres, ex: int a, String b");
                CheckBox accessible = new CheckBox("Accessible");
                VBox vbox = new VBox(nomMethode, nomTypeRetour, parametres, accessible);
                dialog.getDialogPane().setContent(vbox);
                ButtonType ajouterButtonType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(ajouterButtonType, ButtonType.CANCEL);

                // Gestion de l'événement pour le bouton "Ajouter"
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == ajouterButtonType) {
                        String nom = nomMethode.getText();
                        String typeRetour = nomTypeRetour.getText();
                        String param = parametres.getText();
                        String acces = "";
                        if(accessible.isSelected())
                            acces = "+";
                        else
                            acces = "-";
                        ArrayList<Parametre> listeParametres = new ArrayList<>();


                        if (!param.trim().isEmpty()) {
                            String[] parametresArray = param.split(",");
                            for (String p : parametresArray) {
                                String[] parts = p.trim().split(" ");
                                if (parts.length == 2) {
                                    String typeParam = parts[0];
                                    String nomParam = parts[1];
                                    listeParametres.add(new Parametre(nomParam, typeParam));
                                } else {
                                    System.out.println("Format de paramètre incorrect : " + param);
                                }
                            }
                        }

                        return new Methode(nom, acces, typeRetour, listeParametres);
                    }
                    return null;
                });

                // Afficher la boîte de dialogue et récupérer le résultat
                dialog.showAndWait().ifPresent(nouvelleMethode -> {
                    model.retour_save();
                    classeComplete.ajoutMethode(nouvelleMethode);

                    try {
                        GenerateurFichierClasse.ajouterMethode(classeComplete.getNom(), "Source/classGenerer", GenerateurFichierClasse.convertirAccessibilite(nouvelleMethode.getAcces()), nouvelleMethode.getType_retour(), nouvelleMethode.getNom(), nouvelleMethode.getParametres());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    model.notifierObservateurs();
                });
            }
        });

        methodes.getItems().addAll(afficherMethodes, ajouterMethode);



        // --------menu pour les dépendances---------
        Menu dependances = new Menu("Dépendances");
        CheckMenuItem afficherDependances = new CheckMenuItem("Afficher les Dépendances");

        afficherDependances.setOnAction(e -> {
            if (contextMenu.getOwnerNode() instanceof VueClasse) {
                VueClasse vueClasse = (VueClasse) contextMenu.getOwnerNode();
                ClasseComplete classeComplete = vueClasse.getClasseComplete();

                model.retour_save();
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

                model.retour_save();
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
                ClasseComplete classeComplete = vueClasse.getClasseComplete();

                // Mise à jour de l'état des options du menu contextuel en fonction des attributs de la classe
                CheckMenuItem afficherAttributs = (CheckMenuItem) ((Menu) contextMenu.getItems().get(0)).getItems().get(0);
                afficherAttributs.setSelected(classeComplete.isVisible_Attributs());

                CheckMenuItem afficherMethodes = (CheckMenuItem) ((Menu) contextMenu.getItems().get(1)).getItems().get(0);
                afficherMethodes.setSelected(classeComplete.isVisible_Methodes());

                CheckMenuItem afficherDependances = (CheckMenuItem) ((Menu) contextMenu.getItems().get(2)).getItems().get(0);
                afficherDependances.setSelected(classeComplete.isVisible_Dependances());

                // Afficher le menu contextuel
                contextMenu.show((Node) mouseEvent.getSource(), mouseEvent.getScreenX(), mouseEvent.getScreenY());
            }
        }

        if (mouseEvent.getButton().name().equals("PRIMARY")) {
            contextMenu.hide();
        }
    }

}
