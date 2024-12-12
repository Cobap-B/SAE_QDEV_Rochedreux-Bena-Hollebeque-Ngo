package MVC;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ControleurBoutonDroit implements EventHandler<MouseEvent> {
    private Model model;

    public ControleurBoutonDroit(Model m){
        this.model = m;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if(mouseEvent.isSecondaryButtonDown()){
            System.out.println("clic droit détecté");
        }
    }
}
