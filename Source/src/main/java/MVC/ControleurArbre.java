package MVC;

import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

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
    }
}
