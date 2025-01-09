package MVC;

import Classes.ClasseComplete;
import Classes.Dependance;
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

        model.enregistrerObservateur(this);


        this.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) -> {
            this.setWidth(newBounds.getWidth());
            this.setHeight(newBounds.getHeight());
            c.setHeight(getHeight());
            c.setWidth(getWidth());


            // Ajouter le contrôleur pour gérer le clic droit
            Controleur_Classe_Boutton controleurCanvas = new Controleur_Classe_Boutton(model);
            this.setOnMousePressed(controleurCanvas);

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





        for (ClasseComplete classeComplete : model.getDiagramme()) {

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


            ControleurClasseDrag controleurClasseDrag = new ControleurClasseDrag(model, classeComplete, this);

            vue.setOnMousePressed(controleurClasseDrag);
            vue.setOnMouseReleased(controleurClasseDrag);
            vue.setOnMouseDragged(controleurClasseDrag);

            vue.setOnMouseClicked(new ControleurBoutonDroit(model));
        }
    }

    public void createFleches(ClasseComplete c, ArrayList<ClasseComplete> dep, GraphicsContext gc){

        for (ClasseComplete classeComplete : dep) {

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

            c2x = c1x + distance * Math.cos(angle);
            c2y = c1y + distance * Math.sin(angle);

            while (c2x>classeComplete.getX() && c2x<classeComplete.getX()+classeComplete.getTailleX() &&
                    c2y>classeComplete.getY() && c2y<classeComplete.getY()+classeComplete.getTailleY()){
                distance-=1;
                c2x = c1x + distance * Math.cos(angle);
                c2y = c1y + distance * Math.sin(angle);
            }


            //Obtenir le type la dependance
            String type = "Base";
            for (Dependance dependance : c.getDependances()) {
                if (dependance.getDepend().equals(classeComplete.getNom())){
                    type = dependance.getType();
                }
            }
            switch (type) {
                case "Extend":
                    drawHeritage(gc, angle, distance, c1x, c1y, c2x, c2y, 10);
                    break;
                case "Implement":
                    drawInterface(gc, angle, distance, c1x, c1y, c2x, c2y, 10);
                    break;
                case "Base":
                    drawFleche(gc, angle, c1x, c1y, c2x, c2y, 10);
                    break;
            }


        }

    }

    private void drawHeritage(GraphicsContext gc, double angle, double distance, double startX, double startY, double endX, double endY, double size) {

        gc.strokeLine(startX, startY, startX + (distance-10) * Math.cos(angle),startY + (distance-10) * Math.sin(angle));


        // Position de la pointe (sommet avant)
        double tipX = endX;
        double tipY = endY;

        // Positions des sommets arrière (base du triangle)
        double baseLeftX = endX - size * Math.cos(angle) - (size / 2) * Math.sin(angle);
        double baseLeftY = endY - size * Math.sin(angle) + (size / 2) * Math.cos(angle);

        double baseRightX = endX - size * Math.cos(angle) + (size / 2) * Math.sin(angle);
        double baseRightY = endY - size * Math.sin(angle) - (size / 2) * Math.cos(angle);

        gc.strokePolygon(
                new double[]{tipX, baseLeftX, baseRightX},
                new double[]{tipY, baseLeftY, baseRightY},
                3
        );
    }

    private void drawInterface(GraphicsContext gc, double angle, double distance, double startX, double startY, double endX, double endY, double size) {

        gc.setLineDashes(10, 5);
        gc.strokeLine(startX, startY, startX + (distance-10) * Math.cos(angle),startY + (distance-10) * Math.sin(angle));
        gc.setLineDashes(null);
        // Position de la pointe (sommet avant)
        double tipX = endX;
        double tipY = endY;

        // Positions des sommets arrière (base du triangle)
        double baseLeftX = endX - size * Math.cos(angle) - (size / 2) * Math.sin(angle);
        double baseLeftY = endY - size * Math.sin(angle) + (size / 2) * Math.cos(angle);

        double baseRightX = endX - size * Math.cos(angle) + (size / 2) * Math.sin(angle);
        double baseRightY = endY - size * Math.sin(angle) - (size / 2) * Math.cos(angle);

        gc.strokePolygon(
                new double[]{tipX, baseLeftX, baseRightX},
                new double[]{tipY, baseLeftY, baseRightY},
                3
        );
    }

    private void drawFleche(GraphicsContext gc, double angle, double startX, double startY, double endX, double endY, double size) {

        gc.strokeLine(startX, startY, endX, endY);

        // Position de la pointe (sommet avant)
        double tipX = endX;
        double tipY = endY;

        double leftX = endX - size * Math.cos(angle - Math.PI / 6); // Angle ajusté pour la "branche" gauche
        double leftY = endY - size * Math.sin(angle - Math.PI / 6);

        double rightX = endX - size * Math.cos(angle + Math.PI / 6); // Angle ajusté pour la "branche" droite
        double rightY = endY - size * Math.sin(angle + Math.PI / 6);


        gc.strokeLine(startX, startY, endX, endY);

        // Dessiner les deux côtés de la flèche
        gc.strokeLine(tipX, tipY, leftX, leftY); // Branche gauche
        gc.strokeLine(tipX, tipY, rightX, rightY); // Branche droite
    }

}
