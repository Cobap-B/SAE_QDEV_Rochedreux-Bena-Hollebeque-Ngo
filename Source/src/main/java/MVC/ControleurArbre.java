package MVC;

import Classes.Classe;
import Classes.ClasseComplete;
import Classes.FichierComposite;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.*;
import javafx.event.EventHandler;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;

public class ControleurArbre implements EventHandler<MouseEvent> {
    private Model model;

    public ControleurArbre(Model m){
        this.model = m;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        //clic sur un fichier classe
        //tant qu'il est drag il peut déplacé le fichier où il veut
        //si le fichier est drop sur l'interface principale
        //affiche la classe
        TreeView tv = (TreeView) mouseEvent.getSource();

        Node node = mouseEvent.getPickResult().getIntersectedNode();
        // Accept clicks only on node cells, and not on empty spaces of the TreeView
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
            TreeItemFile item = (TreeItemFile) tv.getSelectionModel().getSelectedItem();
            String name = (String) ((TreeItem)tv.getSelectionModel().getSelectedItem()).getValue();

            if(item.isLeaf() && mouseEvent.getClickCount() == 2){
//                System.out.println("Node click: " + name);
//                System.out.println(item.getF().getAbsolutePath());
                Classe c = new Classe(item.getF().getAbsolutePath());
                model.ajouter_Classe_D(c.getClasseComplete());
            }
        }

    }
}
