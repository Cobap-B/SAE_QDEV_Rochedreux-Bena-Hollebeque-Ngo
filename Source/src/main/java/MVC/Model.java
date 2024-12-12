package MVC;

import Classes.*;

import java.util.ArrayList;

public class Model implements Sujet{
    /**
     * Liste des observateurs
     */
    private ArrayList<Observateur> observateurs;
    private ArrayList<String> logs;
    private ArrayList<Classe> classes;
    private ArrayList<Dossier> arbre;
    public void ajoutAttribut(Classe c,  Attribut a) {

    }
    public void ajoutMethode(Classe c, Methode m){

    }
    public void ajouter_Classe_D(Classe c){

    }
    public void ajouter_Log(String s){

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
