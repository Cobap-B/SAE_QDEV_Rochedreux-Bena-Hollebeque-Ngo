package MVC;

import Classes.ClasseComplete;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.Serializable;

import static javafx.geometry.Pos.CENTER;

public class VueClasse extends VBox implements Observateur{


    private ClasseComplete classeComplete;

    public ClasseComplete getClasseComplete() {
        return classeComplete;
    }

    /**
     * Crée une VBox qui affiche une classe dans le diagramme
     * @param c
     */
    public VueClasse(ClasseComplete c){
        classeComplete = c;
        double r = classeComplete.getColor().getRed();
        double g = classeComplete.getColor().getGreen();
        double b = classeComplete.getColor().getBlue();
        double brightness = 0.2126*r + 0.7152*g + 0.0722 * b;
        boolean light = brightness < 50;
        String nomClasse = "<<"+ classeComplete.getType() + ">>" + '\n' + classeComplete.getNom();
        Text label = new Text(nomClasse);
        label.setTextAlignment(TextAlignment.CENTER);
        Text attribut = new Text(c.getTextAttribut());
        Text methode = new Text(c.getTextMethode());

        Pane p_label = new Pane();
        if (light) {
            p_label.setBorder(Border.stroke(Color.WHITE));
            label.setStroke(Color.WHITE);
            label.setStrokeWidth(0.8);
            attribut.setStroke(Color.WHITE);
            attribut.setStrokeWidth(0.8);
            methode.setStroke(Color.WHITE);
            methode.setStrokeWidth(0.8);
        }
        else{
            p_label.setBorder(Border.stroke(Color.BLACK));
            label.setStroke(Color.BLACK);
            label.setStrokeWidth(0.2);
            attribut.setStroke(Color.BLACK);
            attribut.setStrokeWidth(0.2);
            methode.setStroke(Color.BLACK);
            methode.setStrokeWidth(0.2);
        }
        p_label.getChildren().add(label);
        label.relocate(0,0);

        Pane p_attribut = new Pane();
        if (light) {
            p_attribut.setBorder(Border.stroke(Color.WHITE));
        }
        else{
            p_attribut.setBorder(Border.stroke(Color.BLACK));
        }
        p_attribut.getChildren().add(attribut);
        attribut.relocate(0,0);

        Pane p_methode = new Pane();
        if (light) {
            p_methode.setBorder(Border.stroke(Color.WHITE));
        }
        else{
            p_methode.setBorder(Border.stroke(Color.BLACK));
        }
        p_methode.getChildren().add(methode);
        methode.relocate(0,0);

        label.setTextAlignment(TextAlignment.CENTER);
        this.setBackground(Background.fill(Color.rgb((int)r,(int)g,(int)b)));
        if (light) {
            this.setBorder(Border.stroke(Color.WHITE));
        }
        else{
            this.setBorder(Border.stroke(Color.BLACK));
        }
        this.getChildren().addAll(p_label, p_attribut, p_methode);
    }

    @Override
    public void actualiser(Sujet s) {

    }


}
