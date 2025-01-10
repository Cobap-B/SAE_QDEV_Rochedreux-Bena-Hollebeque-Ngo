package MVC;

import Classes.*;


import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import net.sourceforge.plantuml.GeneratedImage;
import net.sourceforge.plantuml.SourceFileReader;

import javax.imageio.ImageIO;

public class Model implements Sujet{
    /**
     * Liste des observateurs
     */
    private ArrayList<Observateur> observateurs;
    private ArrayList<String> logs;
    private ArrayList<ClasseComplete> diagramme;
    private Dossier arbre;
    private Color couleur;

    private final Stack<byte[]> ctrlZ;

    private final int TailleCtrlZ = 20;

    public Model(){
        logs = new ArrayList<>();
        diagramme = new ArrayList<>();
        observateurs = new ArrayList<>();
        arbre = null;
        couleur = new Color(204,255,204);
        ctrlZ = new Stack<>();
    }

    public void ouvrirDossier(String path){
        arbre = new Dossier(path);
        ajouter_Log("Ouverture du dossier " + path);

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
            retour_save();
            c.setCo(x, y, 10000, 10000);
            c.setColor(couleur);
            diagramme.add(c);
            ajouter_Log("Ajout de la classe " + c.getNom());
        }else{
            ajouter_Log("Déjà dans le diagramme " + c.getNom());
        }
        notifierObservateurs();

    }

    public void effacer_D(){
        retour_save();
        if(!diagramme.isEmpty()){diagramme = new ArrayList<>();}
        ajouter_Log("Diagramme effacé");
        notifierObservateurs();
    }

    public void ajouter_Log(String s){
        logs.add(s);
    }

    public void retour_save(){


        try {
            ArrayList<ClasseComplete> clone = new ArrayList<>(diagramme);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(clone);
            objectStream.flush();

            byte[] serializedData = byteStream.toByteArray();
            ctrlZ.add(serializedData);
            if(ctrlZ.size()>TailleCtrlZ){
                ctrlZ.removeFirst();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void retour_arriere(){
        try {

            if (!ctrlZ.isEmpty()){
                byte[] serializedData = ctrlZ.pop();

                ByteArrayInputStream byteInputStream = new ByteArrayInputStream(serializedData);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);

                ArrayList<ClasseComplete> deserialized = (ArrayList<ClasseComplete>) objectInputStream.readObject();

                diagramme = deserialized;
                notifierObservateurs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


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
        ajouter_Log("Le diagramme a été exporté en format source PlantUML");
        notifierObservateurs();
    }

    public void saveDiagramme(VueDiagramme v) throws IOException{
        File dir = new File("diagramme");
        dir.mkdirs();
        WritableImage image = v.snapshot(null, null);
        File file = new File("diagramme/output.png");
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        ajouter_Log("Le diagramme a été exporté en PNG");
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
                ajouter_Log("Impossible de créer le dossier de sortie.");
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
                    ajouter_Log("\n Le diagramme a été exporté en format PNG plantUML\n" + "Image générée : " + fichierDestination.getAbsolutePath());
                } else {
                    ajouter_Log("Impossible de déplacer le fichier généré : " + fichierImage.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            ajouter_Log("Erreur lors de la génération de l'image UML : " + e.getMessage());
        }
        notifierObservateurs();
    }



    public void ajouter_squelette_Classe(String nom, String type, ArrayList<Attribut> attributs, ArrayList<Methode> methodes, ArrayList<Dependance> dependances, double x, double y) {
        ClasseComplete classe = new ClasseComplete(nom, type, attributs, methodes, dependances);
        if (!diagramme.contains(classe)) {
            retour_save();
            classe.setCo(x, y, 10000, 10000); // Définir les coordonnées et tailles par défaut
            diagramme.add(classe);
            ajouter_Log("Classe " + nom + " ajoutée au diagramme.");
        } else {
            ajouter_Log("La classe " + nom + " existe déjà dans le diagramme.");
        }
        notifierObservateurs(); // Met à jour les observateurs
    }


    public void save(String dir){
        try{
            FileOutputStream fileOutputStream
                    = new FileOutputStream(dir);
            ObjectOutputStream objectOutputStream
                    = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(diagramme);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        notifierObservateurs();
    }

    public void load(String path){
        try{
            FileInputStream fileInputStream
                    = new FileInputStream(path);
            ObjectInputStream objectInputStream
                    = new ObjectInputStream(fileInputStream);
            diagramme = (ArrayList<ClasseComplete>) objectInputStream.readObject();
            ctrlZ.clear();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        notifierObservateurs();
    }

    public void changerColor(double r, double g, double b){
        couleur = new Color((float)r, (float)g, (float)b);
        for(ClasseComplete c : diagramme){
            c.setColor(couleur);
        }
        notifierObservateurs();
    }

    public Color getCouleur(){
        return couleur;
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
                        dep.add(new DependanceFleche(classeComplete, "Base", "1",att.getNombre(),att.getNom()));
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
