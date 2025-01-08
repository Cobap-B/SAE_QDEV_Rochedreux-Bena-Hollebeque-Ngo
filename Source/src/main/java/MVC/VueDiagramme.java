package MVC;

import Classes.ClasseComplete;
import Classes.Dependance;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class VueDiagramme extends Pane implements Observateur{

    Canvas c;
    Model model;
    public VueDiagramme(Model m){
        c = new Canvas(1000, 1000);
        model = m;

    };
    @Override
    public void actualiser(Sujet s) {
        GraphicsContext gc = c.getGraphicsContext2D();

        this.getChildren().clear();
        Model m = (Model) s;

        this.getChildren().add(c);
        gc.clearRect(0, 0, c.getWidth(), c.getHeight());

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);





        for (ClasseComplete classeComplete : m.getDiagramme()) {

            ArrayList<ClasseComplete> dependances = model.getDependances(classeComplete);
            createFleches(classeComplete,dependances, gc);

            VueClasse vue = new VueClasse(classeComplete);
            vue.relocate(classeComplete.getX(), classeComplete.getY());
            this.getChildren().add(vue);

            vue.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) -> {
                //OBLIGATOIRE pour obtenir la taille
                classeComplete.setTailleX(newBounds.getWidth());
                classeComplete.setTailleY(newBounds.getHeight());
            });


            ControleurClasseDrag controleurClasseDrag = new ControleurClasseDrag(m, classeComplete);

            vue.setOnMousePressed(controleurClasseDrag);
            vue.setOnMouseReleased(controleurClasseDrag);
            vue.setOnMouseDragged(controleurClasseDrag);

            vue.setOnMouseClicked(new ControleurBoutonDroit(m));
        }
    }

    public void createFleches(ClasseComplete c, ArrayList<ClasseComplete> dep, GraphicsContext gc){

        for (ClasseComplete classeComplete : dep) {
            gc.strokeLine(c.getX()+(c.getTailleX()/2), c.getY()+(c.getTailleY()/2),
                    classeComplete.getX()+(classeComplete.getTailleX()/2), classeComplete.getY()+(classeComplete.getTailleY()/2));

            double difx = c.getX()+(c.getTailleX()/2)-classeComplete.getX()+(classeComplete.getTailleX()/2);
            double dify = c.getY()+(c.getTailleY()/2)-classeComplete.getY()+(classeComplete.getTailleY()/2);
            double angle = Math.toDegrees(Math.atan(difx/dify));
            System.out.println(angle);
            angle = Math.toDegrees(Math.atan(difx/dify));
            System.out.println(angle);
        }





    }

}
