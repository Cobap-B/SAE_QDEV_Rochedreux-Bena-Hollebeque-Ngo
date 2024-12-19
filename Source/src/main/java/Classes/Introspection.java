package Classes;

import java.io.File;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Introspection {

    public static ClasseComplete creerClasseComplete(Classe c){
        Class <?> classee = null;
        try {
            // Étape 1 : Chemin vers le fichier .class


            // Vérifier que le fichier existe
            if (!c.exists() || !c.isFile()) {
                System.err.println("Fichier .class introuvable !");
                return null;
            }

            // Étape 2 : Identifier la racine du répertoire en fonction du chemin des packages
            // Exemple : fichierClass = chemin/vers/monfichier/mon/package/Exemple.class
            String cheminAbsolu = c.getAbsolutePath();
            String nomFichier = c.getName(); // Exemple.class

            // Retirer le ".class" pour obtenir la classe sans extension
            String cheminSansExtension = cheminAbsolu.substring(0, cheminAbsolu.length() - nomFichier.length());
            // Identifier le chemin racine (supprime la structure des packages à partir du chemin complet)
            File repertoireRacine = new File(cheminSansExtension);
            while (repertoireRacine.getParentFile() != null &&
                    !repertoireRacine.getParentFile().getName().equals("target") && !repertoireRacine.getParentFile().getName().equals("production")) {
                String s = repertoireRacine.getName();
                repertoireRacine = repertoireRacine.getParentFile();
            }

            if (repertoireRacine == null) {
                System.err.println("Impossible de trouver le répertoire racine !");
                return null;
            }

            // Étape 3 : Déterminer le nom complet de la classe depuis le fichier .class
            String cheminRelatif = c.getAbsolutePath()
                    .substring(repertoireRacine.getAbsolutePath().length() + 1) // Chemin relatif à partir de la racine
                    .replace(File.separator, ".") // Remplace "/" par "."
                    .replace(".class", ""); // Supprime l'extension .class
            // Étape 4 : Charger la classe dynamiquement
            System.out.println(c.getAbsolutePath()
                    .substring(repertoireRacine.getAbsolutePath().length() + 1));
            System.out.println(cheminRelatif);
            System.out.println(repertoireRacine.getName());
            System.out.println(repertoireRacine.getAbsolutePath());
            System.out.println(c.getAbsolutePath());

            URLClassLoader classLoader = new URLClassLoader(new URL[]{repertoireRacine.toURI().toURL()});
            classee = classLoader.loadClass(cheminRelatif);

        } catch (ClassNotFoundException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
        for (Field declaredField : classee.getDeclaredFields()) {
            System.out.println(declaredField.getName());
        }
        return new ClasseComplete(classee.getSimpleName(), getTypeClasse(classee), displayField(classee.getDeclaredFields()), displayMethod(classee.getDeclaredMethods()), getDependances(classee));
    }

    private static String getTypeClasse(Class c){
        if(c.isInterface()){
            return "interface";
        }
        if(c.getModifiers() == Modifier.ABSTRACT){
            return "abstract";
        }
        return "class";
    }

    private static ArrayList<Dependance> getDependances(Class c){
        ArrayList<Dependance> dependances = new ArrayList<>();
        Dependance d = null;

        if (c.getSuperclass() != null){
            d = new Dependance(c.getSuperclass().getSimpleName(), "Extend");
            if (!Objects.equals(d.getDepend(), "Object")){
                dependances.add(d);

            }
        }
        if (c.getInterfaces().length>0){
            for (Class i : c.getInterfaces()){
                d = new Dependance(i.getSimpleName(), "Implement");
                dependances.add(d);
            }
        }
        return dependances;
    }


    private static ArrayList<Methode> displayMethod(Method[] methods){
        ArrayList<Methode> methodes = new ArrayList<Methode>();
        Methode mtd = null;
        for (Method m:methods){
            int acces = m.getModifiers();
            String ac = "+";
            if (Modifier.isPrivate(acces)){
                ac = "-";
            }else if (Modifier.isProtected(acces)){
               ac = "#";
            }

            ArrayList<Parametre> param = new ArrayList<>();
            for (Parameter p : m.getParameters()) {
                param.add(new Parametre(p.getName(), p.getType().getSimpleName()));
            }
            mtd = new Methode(m.getName(), "+", m.getReturnType().getSimpleName(), param);
            methodes.add(mtd);



        }
        return methodes;

    }

    private static ArrayList<Attribut> displayField(Field[] fields){
        ArrayList<Attribut> attributs = new ArrayList<>();

        for (Field f: fields){
            int acces = f.getModifiers();
            String tp = f.getType().getSimpleName();
            String ac = "+";

            if (Modifier.isPrivate(acces)){
                ac = "-";
            }else if (Modifier.isProtected(acces)){
                ac = "#";
            }

            Type genericType = f.getGenericType();
            if (genericType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericType;

                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                for (Type typeArgument : typeArguments) {
                    tp = typeArgument.getTypeName().substring(typeArgument.getTypeName().indexOf('.')+1);
                    System.out.println("    - "+tp+"    "+f.getName());
                    attributs.add(new Attribut(f.getName(), ac, tp, "*"));
                }


            }else{
                attributs.add(new Attribut(f.getName(), ac,tp ,"1"));
            }
        }
        return attributs;
    }


}
