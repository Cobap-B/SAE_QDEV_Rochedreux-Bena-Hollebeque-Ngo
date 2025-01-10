package MVC;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

public class ControleurKeyEvent implements EventHandler<KeyEvent> {
    private Model model;
    private KeyCombination ctrlZ = KeyCodeCombination.keyCombination("Ctrl+Z");

    /**
     *
     * @param m
     */
    public ControleurKeyEvent(Model m){
        this.model = m;
    }

    /**
     * Gere le ctrl z
     * @param keyEvent
     */
    @Override
    public void handle(KeyEvent keyEvent) {
        if (ctrlZ.match(keyEvent)){
            model.retour_arriere();
        }

    }
}
