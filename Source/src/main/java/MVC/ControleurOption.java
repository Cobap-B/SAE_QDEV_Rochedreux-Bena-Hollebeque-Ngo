package MVC;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ControleurOption implements EventHandler<ActionEvent> {
    private Model model;

    public ControleurOption(Model m){
        this.model = m;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        Button b = (Button) actionEvent.getSource();

        switch (b.getText()){
            case "Effacer Diagramme":
                model.effacer_D();
                break;
        }
    }
}
