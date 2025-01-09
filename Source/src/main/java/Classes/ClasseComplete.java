package Classes;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class ClasseComplete implements Serializable {
    private String nom;
    private String type;
    private Color color;

    private double X, Y;
    private double tailleX, tailleY;

    private ArrayList<Attribut> attributs;
    private ArrayList<Methode> methodes;
    private ArrayList<Dependance> dependances;


    public ClasseComplete(String nom, String type, ArrayList<Attribut> attributs, ArrayList<Methode> methodes, ArrayList<Dependance> dependances){
        this.nom = nom;
        this.type = type;
        this.attributs = attributs;
        this.methodes = methodes;
        this.dependances = dependances;
        this.color = Color.WHITE;
    }

    public void ajoutAttribut(Attribut a){
        attributs.add(a);
    }


    public String getUml(){
        StringBuilder resultat = new StringBuilder();
        if (!this.type.equals("class")){
            //Ajoute le abstract ou le interface
            resultat.append(this.type+" ");
        }
        resultat.append("class "+nom+"{\n");

        attributs.forEach(attribut -> {
            if (TypePrimitif(attribut.getType())) {
                resultat.append(attribut.toString());
            }
        });

        resultat.append(getTextMethode());


        resultat.append("}\n");


        attributs.forEach(attribut -> {
            //Non type primitif donc un objet
            if (!TypePrimitif(attribut.getType())) {
                resultat.append(this.nom + "\"1\" --> " +"\""+ attribut.getNombre() +"\"" + attribut.getType() +" : "+attribut.getNom() +"\n");
            }
        });


        dependances.forEach(dependance -> {
            resultat.append(this.nom+" ");
            if(dependance.getType().equals("Extend")){
                resultat.append("--|> ");
            } else if (dependance.getType().equals("Implement")) {
                resultat.append("..|> ");
            } else {
                throw new RuntimeException("Dependance pas bon ?");
            }
            resultat.append(dependance.getDepend()+"\n");
        });

        return resultat.toString();
    }

    //pour vérifier qu'un attribut est un type primitif de java, si ce n'est pas le cas alors il ya dépendance d'une autre classe
    private boolean TypePrimitif(String type) {
        String[] typesPrimitifs = {"int", "double", "float", "char", "boolean", "long", "short", "byte", "void", "String"};

        for (String primitif : typesPrimitifs) {
            if (primitif.equals(type)) {
                return true;
            }
        }
        return false;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor(){
        return this.color;
    }


    public String getTextAttribut(){
        String s = "";
        for (Attribut attribut : attributs) {
            if(attribut.isVisibilite()) {
                s += (attribut.toString());
            }
        }
        return s;
    }

    public String getTextMethode(){
        String s = "";
        for (Methode m : methodes) {
            if(m.isVisibilite()) {
                s += (m.toString());
            }
        }
        return s;
    }


    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public ArrayList<Attribut> getAttributs() {
        return attributs;
    }

    public ArrayList<Methode> getMethodes() {
        return methodes;
    }

    public ArrayList<Dependance> getDependances() {
        return dependances;
    }

    public void setVisible_Attributs(boolean b){
        for(Attribut attribut : attributs) {
            attribut.setVisibilite(b);

        }
    }

    public void setVisible_Methodes(boolean b){
        for(Methode methode : methodes) {
            methode.setVisibilite(b);

        }
    }

    public void setVisible_Dependances(boolean b) {
        for(Dependance dependance : dependances) {
            dependance.setVisibilite(b);
        }
    }

    public boolean isVisible_Attributs() {
        for (Attribut attribut : attributs) {
            if (attribut.isVisibilite()) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisible_Methodes() {
        for (Methode methode : methodes) {
            if (methode.isVisibilite()) {
                return true;
            }
        }
        return false;
    }


    public boolean isVisible_Dependances() {
        for (Dependance dependance : dependances) {
            if (dependance.isVisibilite()) {
                return true;
            }
        }
        return false;
    }



    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public void setCo(double x, double y, double mx, double my) {
        X = x;
        Y = y;
        if (X < 0) X = 0;
        if (Y < 0) Y = 0;
        if (X > mx) X = mx;
        if (Y > my) Y = my;
    }

    public double getTailleX() {
        return tailleX;
    }

    public void setTailleX(double tailleX) {
        this.tailleX = tailleX;
    }

    public double getTailleY() {
        return tailleY;
    }

    public void setTailleY(double tailleY) {
        this.tailleY = tailleY;
    }

    //Verifie si la classe est déjà dans le diagramme
    @Override
    public boolean equals(Object obj) {
        ClasseComplete c = (ClasseComplete) obj;
        return nom.equals(c.nom);
    }


}
