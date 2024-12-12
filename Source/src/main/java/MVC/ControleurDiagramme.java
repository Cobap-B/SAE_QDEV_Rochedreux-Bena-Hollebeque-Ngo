package MVC;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurDiagramme implements EventHandler<ActionEvent> {
    private Model model;

    public ControleurDiagramme(Model m){
        this.model = m;
    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }
}
