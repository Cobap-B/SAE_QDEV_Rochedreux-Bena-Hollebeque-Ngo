package MVC;

import Classes.Dossier;
import Classes.FichierComposite;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

public class VueArbre extends TreeView<String> implements Observateur {
    //private TreeView<String> arbre;
    Model m;

    public VueArbre(Model model) {
        this.m = model;
    }

    @Override
    public void actualiser(Sujet s) {
        if (s instanceof Model) {
            Dossier racine = ((Model)s).getArbre();
            //si le dossier n'est pas vide
            if (racine != null) {
                // Mettre à jour l'arborescence
                TreeItem<String> racineItem = new TreeItem<>(racine.getName());
                remplirArborescence(racine, racineItem);
                this.setRoot(racineItem);
            } else {
                //aucun fichier
                this.setRoot(new TreeItem<>("Aucun dossier chargé"));
            }
        }
    }

    private void remplirArborescence(Dossier dossier, TreeItem<String> parent) {
        for (FichierComposite fichier : dossier.files) {
            // je créé un item de l'arbre pour chaque dossier/fichier
            TreeItem<String> item = new TreeItem<>(fichier.getName());
            parent.getChildren().add(item);

            if (fichier instanceof Dossier) {
                // Appel récursif pour les sous-dossiers, le sous dossier devient le parent etc...
                remplirArborescence((Dossier) fichier, item );
            }
        }
    }
}
