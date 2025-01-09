package MVC;

import Classes.ClasseComplete;
import Classes.DependanceFleche;
import Classes.Fleche;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class VueDiagramme extends Pane implements Observateur{

    Canvas canvas;
    Model model;
    public VueDiagramme(Model m){
        canvas = new Canvas();
        model = m;

        model.enregistrerObservateur(this);


        TwoTimeChangeListener<Bounds> oneTimeListener = new TwoTimeChangeListener<>((observable, oldBounds, newBounds) -> {
            this.setWidth(newBounds.getWidth());
            this.setHeight(newBounds.getHeight());
            canvas.setHeight(getHeight());
            canvas.setWidth(getWidth());
        },()->{
            //Rien ici
        });

        this.layoutBoundsProperty().addListener(oneTimeListener);

        // Ajouter le contrôleur pour gérer le clic droit
        Controleur_Classe_Boutton controleurCanvas = new Controleur_Classe_Boutton(model);
        canvas.setOnMousePressed(controleurCanvas);



    }
    @Override
    public void actualiser(Sujet s) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        this.getChildren().clear();
        model = (Model) s;

        this.getChildren().add(canvas);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        ArrayList<Fleche> fleches = new ArrayList<>();
        for (ClasseComplete classeComplete : model.getDiagramme()) {
            VueClasse vue = new VueClasse(classeComplete);
            vue.relocate(classeComplete.getX(), classeComplete.getY());
            this.getChildren().add(vue);

            Runnable r = () -> {};

            //Si jamais c'est la dernière classe et bien c'est elle qui lorsque j'aurais ca taille vas dessiner les flèches... c'est un peu compliqué
            if (model.getDiagramme().indexOf(classeComplete) == model.getDiagramme().size()-1){
                r = () -> {
                    //Le permet de calculer si les flèches ce supperpose ou non
                    calculerFleche(fleches, gc);
                };
            }

            //Creer un listenenr pour obtenir les tailles des vbox car ont les get seulement après les avoirs afficher
            TwoTimeChangeListener<Bounds> oneTimeListener = new TwoTimeChangeListener<>((observable, oldBounds, newBounds) -> {
                // Mettre à jour les tailles
                classeComplete.setTailleX(newBounds.getWidth());
                classeComplete.setTailleY(newBounds.getHeight());
            }, r);

            vue.layoutBoundsProperty().addListener(oneTimeListener);


            ControleurClasseDrag controleurClasseDrag = new ControleurClasseDrag(model, classeComplete, this);

            vue.setOnMousePressed(controleurClasseDrag);
            vue.setOnMouseReleased(controleurClasseDrag);
            vue.setOnMouseDragged(controleurClasseDrag);

            vue.setOnMouseClicked(new ControleurBoutonDroit(model));

        }
    }


    public void calculerFleche(ArrayList<Fleche> fleches, GraphicsContext gc){
        //La fonction est trigger lorsqu'on possède enfin les tailles de toutes les classes donc on calcule les flèches
        for (ClasseComplete classeComplete : model.getDiagramme()) {
            ArrayList<DependanceFleche> dependances = model.getDependances(classeComplete);
            fleches.addAll(createFleches(classeComplete,dependances, gc));
        }

        //Ici c'est pour calculer lorsque les flcèhes se supperpose ou non
        //J'ai pas le courage d'expliquer tous les calculs....... my bad
        boolean pas_fin = true;
        while (pas_fin) {       //Tant que deux fleches ne sont pas bonnes on rebouge
            pas_fin = false;
            for (Fleche flech1 : fleches) {
                for (Fleche flech2 : fleches) {
                    if (((flech1.getDepart() == flech2.getDepart() && flech1.getDestination() == flech2.getDestination()) ||
                            (flech1.getDestination() == flech2.getDepart() && flech1.getDepart() == flech2.getDestination())) &&
                            ! flech1.equals(flech2)){

                        if (flech1.getDepartX() >= flech2.getDepartX()-4 && flech1.getDepartX() <= flech2.getDepartX()+4 &&
                                flech1.getDepartY() >= flech2.getDepartY()-4 && flech1.getDepartY() <= flech2.getDepartY()+4){
                            flech1.setDepartX(10);
                            flech1.setDepartY(10);
                            flech2.setDepartX(-10);
                            flech2.setDepartY(-10);
                            pas_fin = true;
                        }
                        if (flech1.getFinX() >= flech2.getFinX()-4 && flech1.getFinX() <= flech2.getFinX()+4 &&
                                flech1.getFinY() >= flech2.getFinY()-4 && flech1.getFinY() <= flech2.getFinY()+4){
                            flech1.setFinX(10);
                            flech1.setFinY(10);
                            flech2.setFinX(-10);
                            flech2.setFinY(-10);
                            pas_fin = true;
                        }
                        if (flech1.getFinX() >= flech2.getDepartX()-4 && flech1.getFinX() <= flech2.getDepartX()+4 &&
                                flech1.getFinY() >= flech2.getDepartY()-4 && flech1.getFinY() <= flech2.getDepartY()+4){
                            flech1.setFinX(10);
                            flech1.setFinY(10);
                            flech2.setDepartX(-10);
                            flech2.setDepartY(-10);
                            pas_fin = true;
                        }
                        if (flech1.getDepartX() >= flech2.getFinX()-4 && flech1.getDepartX() <= flech2.getFinX()+4 &&
                                flech1.getDepartY() >= flech2.getFinY()-4 && flech1.getDepartY() <= flech2.getFinY()+4){
                            flech1.setDepartX(-10);
                            flech1.setDepartY(-10);
                            flech2.setFinX(10);
                            flech2.setFinY(10);
                            pas_fin = true;
                        }

                    }
                }

            }
            for (Fleche flech : fleches) {
                flech.draw(gc);
            }
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

            if (dependanceFleche.getNom() != null){
                fleches.add(new Fleche(dependanceFleche.getString(), c1x, c1y, c2x, c2y,distance, angle, classeComplete, c, dependanceFleche.getCardinalite1(), dependanceFleche.getCardinalite2(), dependanceFleche.getNom()));
            }else{
                fleches.add(new Fleche(dependanceFleche.getString(), c1x, c1y, c2x, c2y,distance, angle, classeComplete, c));
            }


        }

        return fleches;

    }



}
