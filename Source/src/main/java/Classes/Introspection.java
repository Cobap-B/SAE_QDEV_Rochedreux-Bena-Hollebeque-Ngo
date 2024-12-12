package Classes;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class Introspection {

    public static ClasseComplete creerClasseComplete(Classe c){
        Class<?> classee = c.getClass();
        return new ClasseComplete(c.getName(), getTypeClasse(classee), displayField(classee.getFields()), displayMethod(classee.getMethods()), getDependances(classee));
    }

    private void displayInterface(Class[] interfaces){
        for(Class c:interfaces){
            System.out.print(c.getSimpleName()+" ");
        }
    }

    private static String getTypeClasse(Class c){
        if(c.isInterface()){
            return "Interface";
        }
        if(c.getModifiers() == Modifier.ABSTRACT){
            return "Abstract";
        }
        return "Class";
    }

    private void displayParameter(Parameter[] parameters){
        System.out.print(" (");
        for (int i = 0; i < parameters.length; i++) {
            System.out.print(parameters[i].getType().getSimpleName());
            if (i<parameters.length-1) System.out.print(", ");
        }

        System.out.print(") ");
    }

    private static ArrayList<Dependance> getDependances(Class c){
        ArrayList<Dependance> dependances = new ArrayList<>();
        Dependance d = null;

        if (c.getSuperclass() != null){
            System.out.println("Extend : "+c.getSuperclass().getSimpleName());
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
    private void displauyConstructeur(Constructor[] constructors){
        for(Constructor c:constructors){
            System.out.print(getType(c.getName()));
            displayParmeter(c.getParameters());
            System.out.println(" ");
        }
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

    private String getType(String s){
        String[] s2 = s.split("\\.");
        if (s2.length>0)
            return s2[s2.length-1];
        return "";
    }
}
