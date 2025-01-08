package MVC;

import Classes.ClasseComplete;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

public class ControleurClasseDrag implements EventHandler<MouseEvent> {
    private Model model;
    private ClasseComplete classeComplete;
    //private VueClasse vueClasse;
    private VueDiagramme vd;

    //double startx, starty;
    double difx, dify;
    public ControleurClasseDrag(Model m, ClasseComplete c, VueDiagramme v){
        this.model = m;
        this.classeComplete = c;
        //this.vueClasse = v;
        this.vd = v;
    }



    @Override
    public void handle(MouseEvent mouseEvent) {
        double MX = vd.getWidth();
        double MY = vd.getHeight();
        //Verification si clic gauche pour drag
        if (mouseEvent.getButton().name().equals("PRIMARY")){
            if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_RELEASED)){
                //Deplacement de la classe
                classeComplete.setCo(mouseEvent.getSceneX()-250-difx , mouseEvent.getSceneY()-40-dify, MX , MY);
            }else if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_DRAGGED)){
                //Lorsque drag
                classeComplete.setCo(mouseEvent.getSceneX()- 250 -difx, mouseEvent.getSceneY()- 40 -dify, MX, MY);
            }else if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_PRESSED)){
                //startx = mouseEvent.getSceneX() - vueClasse.getTranslateX();
                //starty = mouseEvent.getSceneY() - vueClasse.getTranslateY();
                difx = mouseEvent.getSceneX() - classeComplete.getX() - 250;
                dify = mouseEvent.getSceneY() - classeComplete.getY() - 40;
            }

            model.notifierObservateurs();
        }

    }
}