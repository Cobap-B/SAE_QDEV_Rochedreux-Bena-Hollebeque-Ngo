package MVC;

import Classes.ClasseComplete;
import Classes.Dependance;
import Classes.DependanceFleche;
import Classes.Fleche;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.awt.geom.Line2D;
import java.util.ArrayList;

public class VueDiagramme extends Pane implements Observateur{

    Canvas c;
    Model model;
    public VueDiagramme(Model m){
        c = new Canvas();
        model = m;
        this.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) -> {
            this.setWidth(newBounds.getWidth());
            this.setHeight(newBounds.getHeight());
            c.setHeight(getHeight());
            c.setWidth(getWidth());


        });



    };
    @Override
    public void actualiser(Sujet s) {
        GraphicsContext gc = c.getGraphicsContext2D();

        this.getChildren().clear();
        model = (Model) s;

        this.getChildren().add(c);
        gc.clearRect(0, 0, c.getWidth(), c.getHeight());

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);


        ArrayList<Fleche> fleches = new ArrayList<>();
        for (ClasseComplete classeComplete : model.getDiagramme()) {

            ArrayList<DependanceFleche> dependances = model.getDependances(classeComplete);
            fleches.addAll(createFleches(classeComplete,dependances, gc));

            VueClasse vue = new VueClasse(classeComplete);
            vue.relocate(classeComplete.getX(), classeComplete.getY());
            this.getChildren().add(vue);

            vue.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) -> {
                //OBLIGATOIRE pour obtenir la taille
                classeComplete.setTailleX(newBounds.getWidth());
                classeComplete.setTailleY(newBounds.getHeight());
            });

            ControleurClasseDrag controleurClasseDrag = new ControleurClasseDrag(model, classeComplete, this);

            vue.setOnMousePressed(controleurClasseDrag);
            vue.setOnMouseReleased(controleurClasseDrag);
            vue.setOnMouseDragged(controleurClasseDrag);

            vue.setOnMouseClicked(new ControleurBoutonDroit(model));
        }
        for (Fleche flech1 : fleches) {
            for (Fleche flech2 : fleches) {
                if (flech1.getDepartX() >= flech2.getDepartX()-2 && flech1.getDepartX() <= flech2.getDepartX()+2 &&
                        flech1.getDepartY() >= flech2.getDepartY()-2 && flech1.getDepartY() <= flech2.getDepartY()+2){
                    flech1.setDepartX(10);
                    flech1.setDepartY(10);
                    flech2.setDepartX(-10);
                    flech2.setDepartY(-10);
                }
                if (flech1.getFinX() >= flech2.getFinX()-2 && flech1.getFinX() <= flech2.getFinX()+2 &&
                        flech1.getFinY() >= flech2.getFinY()-2 && flech1.getFinY() <= flech2.getFinY()+2){
                    flech1.setFinX(10);
                    flech1.setFinY(10);
                    flech2.setFinX(-10);
                    flech2.setFinY(-10);
                }
                if (flech1.getFinX() >= flech2.getDepartX()-2 && flech1.getFinX() <= flech2.getDepartX()+2 &&
                        flech1.getFinY() >= flech2.getDepartY()-2 && flech1.getFinY() <= flech2.getDepartY()+2){
                    flech1.setFinX(15);
                    flech1.setFinY(15);
                    flech2.setDepartX(-15);
                    flech2.setDepartY(-15);
                }
            }
            flech1.draw(gc);
        }
    }

    public ArrayList<Fleche> createFleches(ClasseComplete c, ArrayList<DependanceFleche> dep, GraphicsContext gc){
        ArrayList<Fleche> fleches = new ArrayList<>();

        for (DependanceFleche dependanceFleche : dep) {
            ClasseComplete classeComplete = dependanceFleche.getClasseComplete();
            double c1x = c.getX()+(c.getTailleX()/2);
            double c1y = c.getY()+(c.getTailleY()/2);

            double c2x = classeComplete.getX()+(classeComplete.getTailleX()/2);
            double c2y = classeComplete.getY()+(classeComplete.getTailleY()/2);

            //Distance entre les deux centres x et y comme un triangle
            double difx = c2x-c1x;
            double dify = c2y-c1y;

            //Ici on calcule donc la distance entre les deux centres
            double distance = Math.sqrt((difx*difx)+(dify*dify));

            //Angle de c à classComplete au niveau de 0
            double angle = Math.atan2(dify,difx);


            fleches.add(new Fleche(dependanceFleche.getString(), c1x, c1y, c2x, c2y,distance, angle, classeComplete));


        }

        return fleches;

    }



}
