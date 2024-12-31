package MVC;

import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class ControleurBoutonDroit implements EventHandler<MouseEvent> {
    private Model model;
    private BorderPane pane;
    private ContextMenu contextMenu;

    public ControleurBoutonDroit(Model model, BorderPane bp) {
        this.model = model;

        this.pane = bp;

        contextMenu = new ContextMenu();
        MenuItem addClass = new MenuItem("Afficher");
        MenuItem clearDiagram = new MenuItem("Masquer");
        contextMenu.getItems().addAll(addClass, clearDiagram);

        pane.setOnMouseClicked(this);
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            contextMenu.show(pane, event.getScreenX(), event.getScreenY());
        } else if (event.getButton() == MouseButton.PRIMARY) {
            contextMenu.hide();
        }
    }
}
