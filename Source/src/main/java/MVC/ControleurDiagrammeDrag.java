package MVC;

import Classes.Classe;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class ControleurDiagrammeDrag implements EventHandler<DragEvent> {
    private Model model;

    public ControleurDiagrammeDrag(Model m){
        this.model = m;
    }

    @Override
    public void handle(DragEvent event) {
        if (event.getGestureSource() != event.getTarget() &&
                event.getDragboard().hasString()) {
            /* allow for both copying and moving, whatever user chooses */
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            event.consume();
        }
        TransferMode mode = event.getTransferMode();
        if(mode.name().equals("MOVE") && event.getEventType().getName().equals("DRAG_DROPPED")){
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                Classe c = new Classe(db.getString());
                model.ajouter_Classe_D(c.getClasseComplete());
                success = true;
            }
            /* let the source know whether the string was successfully
             * transferred and used */
            event.setDropCompleted(success);

            event.consume();
        }
    }
}
