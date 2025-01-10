package MVC;

import Classes.Dossier;
import Classes.FichierComposite;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Objects;

public class VueArbre extends TreeView<String> implements Observateur{
    Model m;
    String nomArbre;
    private final Image image_dossier = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/dossier.png")));
    private final Image image_classe = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/classe.png")));

    /**
     *
     * @param model
     */
    public VueArbre(Model model) {
        this.m = model;
        this.setRoot(new TreeItem<>("Aucun dossier chargé"));
        this.nomArbre = "";
    }

    /**
     * Change la taille de l'image
     * @param image
     * @return
     */
    private ImageView Image_resize(Image image) {
        // on adapte l'image pour qu'elle soit pas trop grande
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(16);
        imageView.setFitHeight(16);
        return imageView;
    }

    /**
     * Afficher l'arbre du dossier
     * @param s
     */
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
                // Mise a jour de l'arborescence
                TreeItem<String> racineItem = new TreeItem<>(racine.getName());
                racineItem.setGraphic(Image_resize(image_dossier));
                remplirArborescence(racine, racineItem);
                this.setRoot(racineItem);
                this.nomArbre = racine.getName();
            } else {
                //aucun fichier
                this.setRoot(new TreeItem<>("Aucun dossier chargé"));
            }
        }
    }

    /**
     * Remplit l'arboressence de fichiers
     * @param dossier
     * @param parent
     */
    private void remplirArborescence(Dossier dossier, TreeItem<String> parent) {
        boolean contient_fichier_class = false;

        for (FichierComposite fichier : dossier.files) {
            // on ne prend que les .class ou les dossiers sans .idea
            if ((fichier instanceof Dossier && !fichier.getName().equals(".idea")) || fichier.getName().endsWith(".class")) {

                // TreeItem pour chaque fichier ou dossier
                TreeItemFile item = new TreeItemFile(fichier);

                // Si c'est un fichier .class, on n'affiche pas l'extension .class
                String nom_classe = fichier.getName();
                if (fichier.getName().endsWith(".class")) {
                    nom_classe = nom_classe.substring(0, nom_classe.length() - 6);
                }
                item.setValue(nom_classe);

                // image pour dossier ou fichier
                if (fichier instanceof Dossier) {
                    item.setGraphic(Image_resize(image_dossier));
                } else if (fichier.getName().endsWith(".class")) {
                    item.setGraphic(Image_resize(image_classe));
                }

                parent.getChildren().add(item);

                //si on a un dossier
                if (fichier instanceof Dossier) {
                    // on vérifie qu'il n'est pas vide et s'il contient des .class
                    contient_fichier_class = contient_fichier_class || !((Dossier) fichier).files.isEmpty();
                    // récursif pour les sous-dossiers
                    remplirArborescence((Dossier) fichier, item);
                } else if (fichier.getName().endsWith(".class")) {
                    contient_fichier_class = true;
                }
            }
        }


        //CA MARCHE PAAAAAAS

        // Si le dossier n'a pas de fichier .class ni de dossier valides, on ne l'ajoute pas
        if (dossier instanceof Dossier && !contient_fichier_class) {
            parent.getChildren().removeIf(item -> item.getValue().equals(dossier.getName()));
        }
    }
}
