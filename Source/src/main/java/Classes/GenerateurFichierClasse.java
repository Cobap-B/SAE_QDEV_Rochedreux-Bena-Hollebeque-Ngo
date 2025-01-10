package Classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateurFichierClasse {

    /**
     * Permet de generer un squelette de classe java
     * @param nomClasse
     * @param type
     * @param repertoire
     * @throws IOException
     */
    public static void genererFichierClasse(String nomClasse, String type, String repertoire) throws IOException {

        //gérer les types de class
        if (!type.equals("class") && !type.equals("abstract") && !type.equals("interface")) {
            throw new IllegalArgumentException("erreur : Utilisez 'class', 'abstract class' ou 'interface' pour le typage");
        }
        File rep = new File(repertoire);
        if(!rep.exists()){
            rep.mkdirs();
        }

        File file = new File(rep, nomClasse + ".java");

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){

            if(type.equals("interface")) {
                writer.write("public interface " + nomClasse + " {\n");
            } else if(type.equals("abstract")) {
                writer.write("public abstract class " + nomClasse + " {\n");
            } else {
                writer.write("public class " + nomClasse + " {\n");
            }

            writer.write("\n    // Attributs\n\n");
            writer.write("    // Méthodes\n\n");
            writer.write("}\n");
        }
    }
}
