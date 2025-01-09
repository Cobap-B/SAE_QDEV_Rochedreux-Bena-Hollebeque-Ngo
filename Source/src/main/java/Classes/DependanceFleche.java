package Classes;

public class DependanceFleche {
    private ClasseComplete classeComplete;
    private String string;
    private String cardinalite1, cardinalite2, nom;


    public DependanceFleche(ClasseComplete classeComplete, String string) {
        this.classeComplete = classeComplete;
        this.string = string;
    }

    public DependanceFleche(ClasseComplete classeComplete, String string, String car1, String car2, String name) {
        this.classeComplete = classeComplete;
        this.string = string;
        this.cardinalite1 = car1;
        this.cardinalite2 = car2;
        this.nom = name;
    }

    public String getCardinalite1() {
        return cardinalite1;
    }

    public String getCardinalite2() {
        return cardinalite2;
    }

    public String getNom() {
        return nom;
    }

    public ClasseComplete getClasseComplete() {
        return classeComplete;
    }

    public void setClasseComplete(ClasseComplete classeComplete) {
        this.classeComplete = classeComplete;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
