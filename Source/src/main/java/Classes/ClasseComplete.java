package Classes;

import java.util.ArrayList;

public class ClasseComplete {
    private String nom;
    private String type;

    private ArrayList<Attribut> attributs;
    private ArrayList<Methode> methodes;
    private ArrayList<Dependance> dependaces;


    public ClasseComplete(String nom, String type, ArrayList<Attribut> attributs, ArrayList<Methode> methodes, ArrayList<Dependance> dependaces){
        this.nom = nom;
        this.type = type;
        this.attributs = attributs;
        this.methodes = methodes;
        this.dependaces = dependaces;
    }


    public String getUml(){
        StringBuilder resultat = new StringBuilder();
        if (!this.type.equals("class")){
            //Ajoute le abstract ou le interface
            resultat.append(this.type+" ");
        }
        resultat.append("class "+nom+"{\n");

        attributs.forEach(attribut -> {
            resultat.append(attribut.getAcces() +" " + attribut.getType() + " "+ attribut.getNom()+"\n");
        });

        methodes.forEach(methode -> {
            resultat.append(methode.getAcces() +" " + methode.getType_retour() + " "+ methode.getNom());
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
            resultat.append(")"+"\n");
        });


        dependaces.forEach(dependance -> {
            resultat.append(this.nom+" ");
            if(dependance.getType().equals("Extend")){
                resultat.append("--|> ");
            } else if (dependance.getType().equals("Interface")) {
                resultat.append("..|> ");
            } else {
                throw new RuntimeException("Dependance pas bon ?");
            }
            resultat.append(dependance.getDepend()+"\n");
        });

        resultat.append("}");

        return "";
    }
}
