package Classes;

public class Classe extends FichierComposite{
    public Classe(String pathname) {
        super(pathname);
    }

    @Override
    public String getFile() {
        return '>'+getName()+"("+length()+")"+'\n';
    }
}