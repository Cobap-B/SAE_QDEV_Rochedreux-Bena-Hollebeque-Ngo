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

    /**
     *
     * @param c
     * @return la classe completement chargee de la classe c
     */
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

            URLClassLoader classLoader = new URLClassLoader(new URL[]{repertoireRacine.toURI().toURL()});
            classee = classLoader.loadClass(cheminRelatif);

        } catch (ClassNotFoundException | MalformedURLException e) {
            throw new RuntimeException(e);
        }

        return new ClasseComplete(classee.getSimpleName(), getTypeClasse(classee), displayField(classee.getDeclaredFields()), displayMethod(classee.getDeclaredMethods(), classee.getDeclaredConstructors()), getDependances(classee));
    }

    private static String getTypeClasse(Class c){
        if(c.isInterface()){
            return "interface";
        }
        if(Modifier.isAbstract(c.getModifiers())){
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


    private static ArrayList<Methode> displayMethod(Method[] methods, Constructor[] constructors){
        ArrayList<Methode> methodes = new ArrayList<>();
        for (Method m : methods) {
            methodes.add(getMethod(m));
        }
        for (Constructor c : constructors){
            methodes.add(getContructeur(c));
        }
        return methodes;
    }
    private static Methode getMethod(Method m){
        int acces = m.getModifiers();
        String ac = "+";
        if (Modifier.isPrivate(acces)) {
            ac = "-";
        } else if (Modifier.isProtected(acces)) {
            ac = "#";
        }

        ArrayList<Parametre> param = new ArrayList<>();
        for (Parameter p : m.getParameters()) {
            String paramType = p.getType().getSimpleName(); // Type de base
            Type genericParamType = p.getParameterizedType();

            if (genericParamType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericParamType;
                String typeString = paramType + "<";
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                for (int i = 0; i < typeArguments.length; i++) {
                    String typeName = typeArguments[i].getTypeName();
                    typeString += typeName.substring(typeName.lastIndexOf('.') + 1);
                    if (i < typeArguments.length - 1) {
                        typeString += ", ";
                    }
                }
                typeString += ">";
                paramType = typeString; // Mettre à jour le type du paramètre
            }
            param.add(new Parametre(p.getName(), paramType));
        }

        //Vérifier si l'attribut est une collection
        String typeRetour = m.getReturnType().getSimpleName();
        Type returnType = m.getGenericReturnType();
        if (returnType instanceof ParameterizedType) {
            //On le force a être un ParameterizedType donc un attribut qui possède des Class en attributs : collection
            ParameterizedType parameterizedType = (ParameterizedType) returnType;
            String typeString = typeRetour + "<";
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            //Ici on obtient la liste de ses attributs tout simplement
            for (int i = 0; i < typeArguments.length ; i++) {
                String typeName = typeArguments[i].getTypeName();
                typeString += typeName.substring(typeName.lastIndexOf('.') + 1);
                if (i < typeArguments.length - 1) {
                    typeString += ", ";
                }
            }
            typeString += ">";
            typeRetour = typeString;
        }
        //On creer notre attribut avec un * car c'est une collection
        Methode mtd = new Methode(m.getName(), ac, typeRetour, param);
        return mtd;
    }

    private static Methode getContructeur(Constructor m){
        int acces = m.getModifiers();
        String ac = "+";
        if (Modifier.isPrivate(acces)) {
            ac = "-";
        } else if (Modifier.isProtected(acces)) {
            ac = "#";
        }
        ArrayList<Parametre> param = new ArrayList<>();
        for (Parameter p : m.getParameters()) {
            String paramType = p.getType().getSimpleName(); // Type de base
            Type genericParamType = p.getParameterizedType();

            if (genericParamType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericParamType;
                String typeString = paramType + "<";
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                for (int i = 0; i < typeArguments.length; i++) {
                    String typeName = typeArguments[i].getTypeName();
                    typeString += typeName.substring(typeName.lastIndexOf('.') + 1);
                    if (i < typeArguments.length - 1) {
                        typeString += ", ";
                    }
                }
                typeString += ">";
                paramType = typeString; // Mettre à jour le type du paramètre
            }
            param.add(new Parametre(p.getName(), paramType));
        }

        //On creer notre attribut avec un * car c'est une collection
        Methode mtd = new Methode(getSimpleName(m.getName()), ac, "void", param);
        return mtd;
    }

    /**
     *
     * @param fields liste des attributs
     * @return liste des Attributs
     */
    private static ArrayList<Attribut> displayField(Field[] fields){
        ArrayList<Attribut> attributs = new ArrayList<>();
        //Boucle dans les fields
        for (Field f: fields){
            int acces = f.getModifiers();
            //acces : public/private/protected
            String tp = f.getType().getSimpleName();
            String ac = "+"; //public si rien
            if (Modifier.isPrivate(acces)){
                ac = "-";
            }else if (Modifier.isProtected(acces)){
                ac = "#";
            }

            //Ici la vérification si l'attribut est une collection
            Type genericType = f.getGenericType(); //On prend le type generic de l'attribut
            if (genericType instanceof ParameterizedType) {
                //Si collection
                String fielType = f.getType().getSimpleName();
                //On le force a être un ParameterizedType donc un attribut qui possède des Class en attributs : collection
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                Type[] typeArguments = parameterizedType.getActualTypeArguments();

                String typeString = fielType + "<";

                //Ici on obtient la liste de ses attributs tout simplement
                for (int i = 0; i < typeArguments.length; i++) {
                    //On prend seulement le nom car c'est ce que l'on veut
                    //On creer notre attribut avec un * car c'est une collection
                    String typeName = typeArguments[i].getTypeName();
                    typeString += typeName.substring(typeName.lastIndexOf('.') + 1);
                    if (i < typeArguments.length - 1) {
                        typeString += ", ";
                    }
                    typeString += ">";
                }
                attributs.add(new Attribut(f.getName(), ac, typeString, "*"));


            }else{ //Si non collection
                //On creer notre attribut de base
                attributs.add(new Attribut(f.getName(), ac,tp ,"1"));
            }
        }
        return attributs;
    }

    public static String getSimpleName(String s){
        String[] s2 = s.split("\\.");
        if (s2.length>0)
            return s2[s2.length-1];
        return "";
    }
}
