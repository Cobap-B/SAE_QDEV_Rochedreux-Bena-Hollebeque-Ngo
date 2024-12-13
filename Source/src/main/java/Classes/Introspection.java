package Classes;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class Introspection {

    public static ClasseComplete creerClasseComplete(Classe c){
        Class classee = null;
        try {
            classee = Class.forName(c.getClass().getPackageName()+"."+getType(c.getName()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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
            dependances.add(d);
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
            if (Modifier.isPublic(acces)){
                ArrayList<Parametre> param = new ArrayList<>();
                for (Parameter p : m.getParameters()) {
                    param.add(new Parametre(p.getName(), p.getType().getSimpleName()));
                }
                mtd = new Methode(m.getName(), "+", m.getReturnType().getSimpleName(), param);
            }
            if (Modifier.isPrivate(acces)){
                ArrayList<Parametre> param = new ArrayList<>();
                for(Parameter p:m.getParameters()){
                    param.add(new Parametre(p.getName(), p.getType().getSimpleName()));
                }
                mtd = new Methode(m.getName(), "-", m.getReturnType().getSimpleName(), param);
            }
            if (Modifier.isProtected(acces)){
                ArrayList<Parametre> param = new ArrayList<>();
                for(Parameter p:m.getParameters()){
                    param.add(new Parametre(p.getName(), p.getType().getSimpleName()));
                }
                mtd = new Methode(m.getName(), "#", m.getReturnType().getSimpleName(), param);
            }
            methodes.add(mtd);
        }
        return methodes;

    }

    private static ArrayList<Attribut> displayField(Field[] fields){
        ArrayList<Attribut> attributs = new ArrayList<>();
        Attribut att = null;

        for (Field f: fields){
            int acces = f.getModifiers();
            if (Modifier.isPublic(acces)){
                att = new Attribut(f.getName(), "+",f.getType().getSimpleName());
            }
            if (Modifier.isPrivate(acces)){
                att = new Attribut(f.getName(), "-",f.getType().getSimpleName());
            }
            if (Modifier.isProtected(acces)){
                att = new Attribut(f.getName(), "#",f.getType().getSimpleName());
            }
            attributs.add(att);
        }
        return attributs;
    }

    private static String getType(String s){
        String[] s2 = s.split("\\.");
        if (s2.length>1)
            return s2[s2.length-2];
        return "";
    }
}
