package Classes;

import javafx.scene.canvas.GraphicsContext;

public class Fleche {
    private String type;
    private double departX, departY, finX, finY;

    private double distance;
    private  double angle;

    private ClasseComplete destination;

    public Fleche(String type, double departX, double departY, double finX, double finY, double distance, double angle, ClasseComplete c) {
        this.type = type;
        this.departX = departX;
        this.departY = departY;
        this.finX = finX;
        this.finY = finY;
        this.distance = distance;
        this.angle = angle;
        this.destination = c;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDepartX() {
        return departX;
    }

    public void setDepartX(double dif) {
        this.departX = departX + (dif) * Math.cos(angle+1.571);
    }

    public double getDepartY() {
        return departY;
    }

    public void setDepartY(double dif) {
        this.departY = departY + (dif) * Math.sin(angle+1.571);
    }

    public double getFinX() {
        return finX;
    }

    public void setFinX(double dif) {
        this.finX = finX + (dif) * Math.cos(angle+1.571);
    }

    public double getFinY() {
        return finY;
    }

    public void setFinY(double dif) {
        this.finY = finY + (dif) * Math.sin(angle+1.571);
    }



    public void draw(GraphicsContext gc){

        finX = departX + distance * Math.cos(angle);
        finY = departY + distance * Math.sin(angle);

        while (finX>destination.getX() && finX<destination.getX()+destination.getTailleX() &&
                finY>destination.getY() && finY<destination.getY()+destination.getTailleY()){
            distance-=1;
            finX = departX + distance * Math.cos(angle);
            finY = departY + distance * Math.sin(angle);
        }

        switch (type) {
            case "Extend":
                drawHeritage(gc,10);
                break;
            case "Implement":
                drawInterface(gc, 10);
                break;
            case "Base":
                drawFleche(gc,10);
                break;
        }
    }
    private void drawHeritage(GraphicsContext gc, double size) {

        gc.strokeLine(departX, departY, departX + (distance-10) * Math.cos(angle),departY + (distance-10) * Math.sin(angle));


        // Position de la pointe (sommet avant)
        double tipX = finX;
        double tipY = finY;

        // Positions des sommets arrière (base du triangle)
        double baseLeftX = finX - size * Math.cos(angle) - (size / 2) * Math.sin(angle);
        double baseLeftY = finY - size * Math.sin(angle) + (size / 2) * Math.cos(angle);

        double baseRightX = finX - size * Math.cos(angle) + (size / 2) * Math.sin(angle);
        double baseRightY = finY - size * Math.sin(angle) - (size / 2) * Math.cos(angle);

        gc.strokePolygon(
                new double[]{tipX, baseLeftX, baseRightX},
                new double[]{tipY, baseLeftY, baseRightY},
                3
        );
    }

    private void drawInterface(GraphicsContext gc, double size) {

        gc.setLineDashes(10, 5);
        gc.strokeLine(departX, departY, departX + (distance-10) * Math.cos(angle),departY + (distance-10) * Math.sin(angle));
        gc.setLineDashes(null);
        // Position de la pointe (sommet avant)
        double tipX = finX;
        double tipY = finY;

        // Positions des sommets arrière (base du triangle)
        double baseLeftX = finX - size * Math.cos(angle) - (size / 2) * Math.sin(angle);
        double baseLeftY = finY - size * Math.sin(angle) + (size / 2) * Math.cos(angle);

        double baseRightX = finX - size * Math.cos(angle) + (size / 2) * Math.sin(angle);
        double baseRightY = finY - size * Math.sin(angle) - (size / 2) * Math.cos(angle);

        gc.strokePolygon(
                new double[]{tipX, baseLeftX, baseRightX},
                new double[]{tipY, baseLeftY, baseRightY},
                3
        );
    }

    private void drawFleche(GraphicsContext gc, double size) {

        gc.strokeLine(departX, departY, finX, finY);

        // Position de la pointe (sommet avant)
        double tipX = finX;
        double tipY = finY;

        double leftX = finX - size * Math.cos(angle - Math.PI / 6); // Angle ajusté pour la "branche" gauche
        double leftY = finY - size * Math.sin(angle - Math.PI / 6);

        double rightX = finX - size * Math.cos(angle + Math.PI / 6); // Angle ajusté pour la "branche" droite
        double rightY = finY - size * Math.sin(angle + Math.PI / 6);


        gc.strokeLine(departX, departY, finX, finY);

        // Dessiner les deux côtés de la flèche
        gc.strokeLine(tipX, tipY, leftX, leftY); // Branche gauche
        gc.strokeLine(tipX, tipY, rightX, rightY); // Branche droite
    }
}
