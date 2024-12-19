package MVC;

import Classes.*;
import net.sourceforge.plantuml.core.*;
import net.sourceforge.plantuml.SourceStringReader;


import java.io.*;
import java.util.ArrayList;

public class Model implements Sujet{
    /**
     * Liste des observateurs
     */
    private ArrayList<Observateur> observateurs;
    private ArrayList<String> logs;
    private ArrayList<ClasseComplete> diagramme;
    private Dossier arbre;


    public Model(){
        logs = new ArrayList<>();
        diagramme = new ArrayList<>();
        observateurs = new ArrayList<>();
        arbre = null;
    }

    public void ouvrirDossier(String path){
        arbre = new Dossier(path);
        notifierObservateurs();
    }


    public void ajoutAttribut(ClasseComplete c,  Attribut a) {
        //Rien
    }
    public void ajoutMethode(ClasseComplete c, Methode m){
        //Rien
    }
    public void ajouter_Classe_D(ClasseComplete c){
        diagramme.add(c);
    }
    public void ajouter_Log(String s){
        logs.add(s);
    }

    public void saveUML() throws IOException{
        File dir = new File("diagramme");
        dir.mkdirs();
        BufferedWriter writer = new BufferedWriter(new FileWriter("diagramme/diagramme.txt"));
        for(ClasseComplete c : diagramme){
            writer.write(c.getUml());
        }
        writer.close();
    }

    public void savePNG() throws IOException {
        OutputStream png = new FileOutputStream("diagramme/diagramme.txt");
        String source = "@startuml\n";

        for(ClasseComplete c : diagramme){
            source += c.getUml();
        }
        source += "@enduml\n";

        SourceStringReader reader = new SourceStringReader(source);

        // Write the first image to "png"
        String desc = reader.outputImage(png).getDescription();
        // Return a null string if no generation
    }




    public Dossier getArbre(){
        return this.arbre;
    }



    /**
     * Ajoute un observateur a la liste
     */
    public void enregistrerObservateur(Observateur o) {
        this.observateurs.add(o);
    }

    /**
     * Supprime un observateur a la liste
     */
    public void supprimerObservateur(Observateur o) {
        int i = this.observateurs.indexOf(o);
        if (i >= 0) {
            this.observateurs.remove(i);
        }
    }

    /**
     * Informe tous les observateurs de la liste des
     * modifications des mesures meteo en appelant leurs methodes actualiser
     */
    public void notifierObservateurs() {
        for (int i = 0; i < this.observateurs.size(); i++) {
            Observateur observer = this.observateurs.get(i);
            observer.actualiser(this);
        }
    }
}
