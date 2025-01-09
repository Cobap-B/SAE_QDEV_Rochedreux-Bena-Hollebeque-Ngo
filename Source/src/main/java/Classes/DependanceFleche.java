package Classes;

public class DependanceFleche {
    private ClasseComplete classeComplete;
    private String string;

    public DependanceFleche(ClasseComplete classeComplete, String string) {
        this.classeComplete = classeComplete;
        this.string = string;
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
