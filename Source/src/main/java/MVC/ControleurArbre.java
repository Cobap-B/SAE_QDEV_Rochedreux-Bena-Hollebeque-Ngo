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

    /**
     *
     * @param m
     */
    public ControleurArbre(Model m){
        this.model = m;
    }

    /**
     * Permet de prendre une classe de l'arbre en la glissant
     * @param mouseEvent
     */
    @Override
    public void handle(MouseEvent mouseEvent) {
        try {
            Node node = mouseEvent.getPickResult().getIntersectedNode();
            TreeView tv = (TreeView) mouseEvent.getSource();

            Dragboard db = node.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
                TreeItemFile item = (TreeItemFile) tv.getSelectionModel().getSelectedItem();
                content.putString(item.getF().getAbsolutePath());
                db.setContent(content);
                mouseEvent.consume();
            }
        }
        catch (ClassCastException e){

        }
    }
}
