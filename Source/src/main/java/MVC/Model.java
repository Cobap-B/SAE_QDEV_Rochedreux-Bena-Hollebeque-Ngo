package MVC;

import Classes.*;


import java.io.*;
import java.util.ArrayList;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import net.sourceforge.plantuml.GeneratedImage;
import net.sourceforge.plantuml.SourceFileReader;

import javax.imageio.ImageIO;
import javax.swing.*;

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
        save();
        if(!diagramme.isEmpty()){diagramme = new ArrayList<>();}
        logs.add("Diagramme effacé");
        load();
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

    public void saveDiagramme(VueDiagramme v) throws IOException{
        File dir = new File("diagramme");
        dir.mkdirs();
        WritableImage image = v.snapshot(null, null);
        File file = new File("diagramme/output.png");
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        logs.add("Le diagramme a été exporté en PNG");
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

    public void save(){
        try{
            File dir = new File("diagramme");
            dir.mkdirs();
            FileOutputStream fileOutputStream
                    = new FileOutputStream("diagramme/save.pipotam");
            ObjectOutputStream objectOutputStream
                    = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(diagramme);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load(){
        try{
            FileInputStream fileInputStream
                    = new FileInputStream("diagramme/save.pipotam");
            ObjectInputStream objectInputStream
                    = new ObjectInputStream(fileInputStream);
            diagramme = (ArrayList<ClasseComplete>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<DependanceFleche> getDependances(ClasseComplete c){
        ArrayList<DependanceFleche> dep = new ArrayList<>();

        //Ajout des dependance de base Heritage et Implementation
        for (Dependance dependance : c.getDependances()) {
            if (dependance.isVisibilite()){
                for (ClasseComplete classeComplete : diagramme) {
                    if (dependance.getDepend().equals(classeComplete.getNom())){
                        dep.add(new DependanceFleche(classeComplete, dependance.getType()));
                    }
                }
            }
        }
        //Ajout des dependance d'attribut
        for (Attribut att : c.getAttributs()) {
            if (att.isVisibilite()){
                for (ClasseComplete classeComplete : diagramme) {
                    if (att.getType().equals(classeComplete.getNom())){
                        dep.add(new DependanceFleche(classeComplete, "Base"));
                    }
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
