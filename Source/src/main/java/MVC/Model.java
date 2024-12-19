package MVC;

import Classes.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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
        writer.write("@startuml \n");
        for(ClasseComplete c : diagramme){
            writer.write(c.getUml());
        }
        writer.write("@enduml \n");
        writer.close();
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
                System.err.println("Impossible de créer le dossier de sortie.");
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
                    System.out.println("Image générée : " + fichierDestination.getAbsolutePath());
                } else {
                    System.err.println("Impossible de déplacer le fichier généré : " + fichierImage.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la génération de l'image UML : " + e.getMessage());
        }
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
