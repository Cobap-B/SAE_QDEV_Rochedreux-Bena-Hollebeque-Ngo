package MVC;

import Classes.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    }

    public void ouvrirDossier(String path){
        Dossier d = new Dossier(path);
        System.out.println(d.getFile());
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
