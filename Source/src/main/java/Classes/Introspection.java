package Classes;

import java.lang.reflect.*;

public class Introspection {

    public static ClasseComplete creerClasseComplete(Classe c){

    }

    private void displayInterface(Class[] interfaces){
        System.out.print("Interface : ");
        for(Class c:interfaces){
            System.out.print(c.getSimpleName()+" ");
        }
        System.out.println();
    }

    private void displayParmeter(Parameter[] parameters){
        System.out.print(" (");
        for (int i = 0; i < parameters.length; i++) {
            System.out.print(parameters[i].getType().getSimpleName());
            if (i<parameters.length-1) System.out.print(", ");
        }

        System.out.print(") ");
    }


    private void displayMethod(Method[] methods){

        for (Method m:methods){
            int acces = m.getModifiers();
            if (Modifier.isPublic(acces))
                System.out.print("public ");
            if (Modifier.isPrivate(acces))
                System.out.print("private ");
            System.out.print(m.getName());
            displayParmeter(m.getParameters());
            System.out.println(" : "+m.getReturnType().getSimpleName());
        }
    }
    private void displauyConstructeur(Constructor[] constructors){
        for(Constructor c:constructors){
            System.out.print(getType(c.getName()));
            displayParmeter(c.getParameters());
            System.out.println(" ");
        }
    }

    private void displayField(Field[] fields){
        for (Field f: fields){
            int acces = f.getModifiers();
            if (Modifier.isPublic(acces))
                System.out.print("public ");
            if (Modifier.isPrivate(acces))
                System.out.print("private ");
            if (Modifier.isProtected(acces))
                System.out.print("protected ");
            System.out.println(f.getType().getSimpleName()+" "+f.getName());
        }
    }
    private String getType(String s){
        String[] s2 = s.split("\\.");
        if (s2.length>0)
            return s2[s2.length-1];
        return "";
    }
}
