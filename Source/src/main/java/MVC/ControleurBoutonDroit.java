package MVC;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ControleurBoutonDroit implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent mouseEvent) {
        if(mouseEvent.isSecondaryButtonDown()){
            System.out.println("clic droit détecté");
        }
    }
}
