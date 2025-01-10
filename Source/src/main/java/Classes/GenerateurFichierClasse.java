package Classes;

import java.io.*;
import java.util.*;

public class GenerateurFichierClasse {

    /**
     * Permet de generer un squelette de classe java
     * @param nomClasse
     * @param type
     * @param repertoire
     * @throws IOException
     */
    public static void genererFichierClasse(String nomClasse, String type, String repertoire) throws IOException {
        if (!type.equals("class") && !type.equals("abstract") && !type.equals("interface")) {
            throw new IllegalArgumentException("Erreur : Utilisez 'class', 'abstract' ou 'interface' pour le typage");
        }

        File rep = new File(repertoire);
        if (!rep.exists()) {
            rep.mkdirs();
        }

        File file = new File(rep, nomClasse + ".java");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            if (type.equals("interface")) {
                writer.write("public interface " + nomClasse + " {\n");
            } else if (type.equals("abstract")) {
                writer.write("public abstract class " + nomClasse + " {\n");
            } else {
                writer.write("public class " + nomClasse + " {\n");
            }

            writer.write("\n    // Attributs\n\n");
            writer.write("    // Méthodes\n\n");
            writer.write("}\n");
        }
    }



    //Je travaille avec des sections associées aux 2 types d'ajouts (attributs et methodes)

    public static void ajouterAttribut(String nomClasse, String repertoire, String access, String type, String nom) throws IOException {
        File file = new File(repertoire, nomClasse + ".java");

        // Créer le répertoire si nécessaire
        File dir = new File(repertoire);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Créer le fichier si nécessaire
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("public class " + nomClasse + " {\n\n    // Attributs\n\n    // Constructeurs\n\n    // Méthodes\n\n}");
            }
        }

        // Lire le contenu existant du fichier
        List<String> lignes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                lignes.add(ligne);
            }
        }

        // Identifier la position où ajouter le nouvel attribut
        StringBuilder contenu = new StringBuilder();
        boolean sectionAttributsTrouvee = false;

        for (String ligne : lignes) {
            contenu.append(ligne).append("\n");

            // Ajouter l'attribut après la section "// Attributs"
            if (ligne.contains("// Attributs") && !sectionAttributsTrouvee) {
                sectionAttributsTrouvee = true;
                contenu.append("    " + access + " " + type + " " + nom + ";\n");
            }
        }

        // Si la section "// Attributs" n'existe pas, on l'ajoute au début de la classe
        if (!sectionAttributsTrouvee) {
            contenu.insert(contenu.indexOf("{") + 1, "\n    // Attributs\n    " + access + " " + type + " " + nom + ";\n");
        }

        // Réécrire tout le fichier avec le contenu mis à jour
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(contenu.toString());
        }
    }


    public static void ajouterMethode(String nomClasse, String repertoire, String access, String typeRetour, String nomMethode, ArrayList<Parametre> parametres) throws IOException {
        File file = new File(repertoire, nomClasse + ".java");

        // Créer le répertoire si il existe pas
        File dir = new File(repertoire);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Créer le fichier si nécessaire
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("public class " + nomClasse + " {" +
                        "\n\n    // Attributs" +
                        "\n\n    // Constructeurs" +
                        "\n\n    // Méthodes" +
                        "\n\n" +
                        "}");
            }
        }

        // Lire le contenu existant du fichier
        List<String> lignes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                lignes.add(ligne);
            }
        }

        // Construire la liste des paramètres pour la méthode
        StringBuilder arguments = new StringBuilder();
        for (Parametre param : parametres) {
            arguments.append(param.getType()).append(" ").append(param.getNom()).append(", ");
        }
        if (!arguments.isEmpty()) {
            // Sans la dernière virgule et le'space c'est mieux
            arguments.setLength(arguments.length() - 2);
        }

        // Identifier la section ou ajouter la méthode
        StringBuilder contenu = new StringBuilder();
        boolean sectionMethodesTrouvee = false;

        for (String ligne : lignes) {
            contenu.append(ligne).append("\n");

            // Ajouter la méthode après la section "// Méthodes"
            if (ligne.contains("// Méthodes") && !sectionMethodesTrouvee) {
                sectionMethodesTrouvee = true;
                contenu.append("    " + access + " " + typeRetour + " " + nomMethode + "(" + arguments + ") {\n");
                contenu.append("    }\n");
            }
        }

        // Si la section "// Méthodes" n'existe pas, l'ajouter au début de la classe
        if (!sectionMethodesTrouvee) {
            contenu.insert(contenu.indexOf("{") + 1,
                    "\n    // Méthodes\n    " + access + " " + typeRetour + " " + nomMethode + "(" + arguments + ") {\n        // TODO: Implémenter la méthode\n    }\n");
        }

        // Réécrire tout le fichier avec le contenu mis à jour
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(contenu.toString());
        }
    }

    public static String convertirAccessibilite(String access) {
        return switch (access) {
            case "+" -> "public";
            case "-" -> "private";
            case "#" -> "protected";
            default -> "";
        };
    }


}
