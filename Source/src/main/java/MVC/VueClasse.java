package MVC;

import Classes.ClasseComplete;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class VueClasse extends VBox implements Observateur{

    private ClasseComplete classeComplete;

    public ClasseComplete getClasseComplete() {
        return classeComplete;
    }

    public VueClasse(ClasseComplete c){
        classeComplete = c;
        Text label = new Text(classeComplete.getNom());
        Text attribut = new Text(c.getTextAttribut());
        Text methode = new Text(c.getTextMethode());

        Pane p_label = new Pane();
        p_label.setBorder(Border.stroke(Color.BLACK));
        p_label.getChildren().add(label);
        label.relocate(0,0);

        Pane p_attribut = new Pane();
        p_attribut.setBorder(Border.stroke(Color.BLACK));
        p_attribut.getChildren().add(attribut);
        attribut.relocate(0,0);

        Pane p_methode = new Pane();
        p_methode.setBorder(Border.stroke(Color.BLACK));
        p_methode.getChildren().add(methode);
        methode.relocate(0,0);

        label.setTextAlignment(TextAlignment.CENTER);
        this.setBackground(Background.fill(Color.LIGHTGREEN));
        this.setBorder(Border.stroke(Color.BLACK));
        this.getChildren().addAll(p_label, p_attribut, p_methode);
    }

    @Override
    public void actualiser(Sujet s) {

    }
}
