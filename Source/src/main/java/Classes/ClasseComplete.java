package Classes;

import java.util.ArrayList;

public class ClasseComplete {
    private String nom;
    private String type;

    private ArrayList<Attribut> attributs;
    private ArrayList<Methode> methodes;
    private ArrayList<Dependance> dependances;


    public ClasseComplete(String nom, String type, ArrayList<Attribut> attributs, ArrayList<Methode> methodes, ArrayList<Dependance> dependances){
        this.nom = nom;
        this.type = type;
        this.attributs = attributs;
        this.methodes = methodes;
        this.dependances = dependances;
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
                resultat.append(attribut.getAcces() + " " + attribut.getType() + " " + attribut.getNom() + "\n");
            }
        });

        methodes.forEach(methode -> {
            resultat.append(methode.getAcces() +" " + methode.getNom());
            resultat.append("(");
            if (methode.getParametres().size()>1){
                //Boucle pour les paramètre
                methode.getParametres().forEach(parametre ->{
                    resultat.append(parametre.getType() +" "+parametre.getNom());
                    resultat.append(",");
                });
                //Retire la dernière virgule
                resultat.delete(resultat.length()-1, resultat.length());
            }
            resultat.append(")"+" : "+ methode.getType_retour()+"\n");
        });

        resultat.append("}\n");


        attributs.forEach(attribut -> {
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
}
