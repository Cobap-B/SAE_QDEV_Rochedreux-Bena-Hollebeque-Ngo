package MVC;

import Classes.ClasseComplete;
import javafx.scene.layout.Pane;

public class VueDiagramme extends Pane implements Observateur{

    public VueDiagramme(Model m){};
    @Override
    public void actualiser(Sujet s) {
        this.getChildren().clear();
        Model m = (Model) s;
        for (ClasseComplete classeComplete : m.getDiagramme()) {
            VueClasse vue = new VueClasse(classeComplete);
            vue.relocate(classeComplete.getX(), classeComplete.getY());
            this.getChildren().add(vue);

            ControleurClasseDrag controleurClasseDrag = new ControleurClasseDrag(m, classeComplete, vue);

            vue.setOnMousePressed(controleurClasseDrag);
            vue.setOnMouseReleased(controleurClasseDrag);
            vue.setOnMouseDragged(controleurClasseDrag);


            vue.setOnMouseClicked(new ControleurBoutonDroit(m));



        }


    }
}
