package MVC;

import Classes.*;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import net.sourceforge.plantuml.GeneratedImage;
import net.sourceforge.plantuml.SourceFileReader;
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
        logs.add("Ouverture du dossier " + path);

        notifierObservateurs();
    }


    public void ajoutAttribut(ClasseComplete c,  Attribut a) {
        //Rien
    }
    public void ajoutMethode(ClasseComplete c, Methode m){
        //Rien
    }
    public void ajouter_Classe_D(ClasseComplete c, double x, double y){
        if (!diagramme.contains(c)){
            c.setCo(x, y, 10000, 10000);
            diagramme.add(c);
            logs.add("Ajout de la classe " + c.getNom());
        }else{
            logs.add("Déjà dans le diagramme " + c.getNom());
        }
        notifierObservateurs();

    }

    public void effacer_D(){
        if(!diagramme.isEmpty()){diagramme = new ArrayList<>();}
        logs.add("Diagramme effacé");
        notifierObservateurs();
    }

    public void ajouter_Log(String s){
        logs.add(s);
    }

    public void saveUML() throws IOException{
        File dir = new File("diagramme");
        dir.mkdirs();
        BufferedWriter writer = new BufferedWriter(new FileWriter("diagramme/diagramme.txt"));
        writer.write("@startuml \n");
        for(ClasseComplete c : diagramme){
            writer.write(c.getUml());
        }
        writer.write("@enduml \n");
        writer.close();
        logs.add("Le diagramme a été exporté en format source PlantUML");
        notifierObservateurs();
    }


    public void savePNG(){
        // Chemin vers le fichier texte contenant le diagramme UML
        File fichierUml = new File("diagramme/diagramme.txt");

        // Dossier où enregistrer le fichier PNG
        File dossierSortie = new File("diagramme");
        if (!dossierSortie.exists()) {

            // Crée le dossier si nécessaire
            boolean dossierCree = dossierSortie.mkdirs();
            if (!dossierCree) {
                logs.add("Impossible de créer le dossier de sortie.");
                return;
            }
        }

        try {
            // Utilisation de PlantUML pour convertir le fichier en PNG
            SourceFileReader reader = new SourceFileReader(fichierUml);



            // Parcourt les fichiers générés
            for (GeneratedImage image : reader.getGeneratedImages()) {
                File fichierImage = image.getPngFile();

                // Définit le chemin de destination
                File fichierDestination = new File(dossierSortie, fichierImage.getName());

                // Déplace le fichier vers le dossier de sortie
                if (fichierImage.renameTo(fichierDestination)) {
                    logs.add("\n Le diagramme a été exporté en format PNG\n" + "Image générée : " + fichierDestination.getAbsolutePath());
                } else {
                    logs.add("Impossible de déplacer le fichier généré : " + fichierImage.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            logs.add("Erreur lors de la génération de l'image UML : " + e.getMessage());
        }
        notifierObservateurs();
    }

    public ArrayList<ClasseComplete> getDependances(ClasseComplete c){
        ArrayList<ClasseComplete> dep = new ArrayList<>();

        for (Dependance dependance : c.getDependances()) {
            for (ClasseComplete classeComplete : diagramme) {
                if (dependance.getDepend().equals(classeComplete.getNom())){
                    dep.add(classeComplete);
                }
            }
        }


        return dep;
    }

    public Dossier getArbre(){
        return this.arbre;
    }


    public ArrayList<String> getLogs() {
        return logs;
    }
    public ArrayList<ClasseComplete> getDiagramme() {
        return diagramme;
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
