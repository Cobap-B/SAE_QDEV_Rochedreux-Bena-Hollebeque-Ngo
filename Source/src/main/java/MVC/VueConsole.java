package MVC;

import java.io.Serializable;

public class VueConsole implements Observateur{
    public VueConsole(){}

    @Override
    public void actualiser(Sujet s){
//        System.out.println(((Model)s).getArbre().getFile());
    }
}
