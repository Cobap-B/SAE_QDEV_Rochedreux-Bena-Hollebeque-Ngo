package MVC;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;

public class ControleurOption implements EventHandler<ActionEvent> {
    private Model model;

    /**
     *
     * @param m
     */
    public ControleurOption(Model m){
        this.model = m;
    }

    /**
     * Gere differentes options du menu du haut
     * @param actionEvent
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        if(actionEvent.getSource().getClass().getSimpleName().equals("ColorPicker")){
            ColorPicker colorPicker = (ColorPicker) actionEvent.getSource();
            model.changerColor(colorPicker.getValue().getRed(), colorPicker.getValue().getGreen(), colorPicker.getValue().getBlue());
        }
        else {
            Button b = (Button) actionEvent.getSource();
            switch (b.getText()){
                case "Effacer Diagramme":
                    model.effacer_D();
                    break;
                case "Retour arrière (Ctrl+Z)":
                    model.retour_arriere();
                    break;
            }
        }



    }
}
