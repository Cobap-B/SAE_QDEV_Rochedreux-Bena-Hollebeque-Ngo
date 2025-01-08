package MVC;

import Classes.Dossier;
import Classes.FichierComposite;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

public class VueArbre extends TreeView<String> implements Observateur {
    Model m;
    String nomArbre;

    public VueArbre(Model model) {
        this.m = model;
        this.setRoot(new TreeItem<>("Aucun dossier chargé"));
        this.nomArbre = "";
    }

    @Override
    public void actualiser(Sujet s) {
        if(m.getArbre() != null){
            if(!this.nomArbre.equals(((Model)s).getArbre().getName())){
                this.getRoot().getChildren().clear();
            }
        }
        if (s instanceof Model && this.getRoot().getChildren().isEmpty()) {
            Dossier racine = ((Model)s).getArbre();
            //si le dossier n'est pas vide
            if (racine != null) {
                // Mettre à jour l'arborescence
                TreeItem<String> racineItem = new TreeItem<>(racine.getName());
                remplirArborescence(racine, racineItem);
                this.setRoot(racineItem);
                this.nomArbre = racine.getName();
            } else {
                //aucun fichier
                this.setRoot(new TreeItem<>("Aucun dossier chargé"));
            }
        }
    }

    private void remplirArborescence(Dossier dossier, TreeItem<String> parent) {
        for (FichierComposite fichier : dossier.files) {
            // je créé un item de l'arbre pour chaque dossier/fichier
            TreeItemFile tif = new TreeItemFile(fichier);
            parent.getChildren().add(tif);

            if (fichier instanceof Dossier) {
                // Appel récursif pour les sous-dossiers, le sous dossier devient le parent etc...
                remplirArborescence((Dossier) fichier, tif );
            }
        }
    }
}
