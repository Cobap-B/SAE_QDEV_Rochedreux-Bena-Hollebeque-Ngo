package Classes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dossier extends FichierComposite{

    public List<FichierComposite> files =new ArrayList<>();

    /**
     *
     * @param file
     * @return l'extension du fichier
     */
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }

    /**
     *
     * @param pathname
     */
    public Dossier(String pathname) {
        super(pathname);

        for(File f: Objects.requireNonNull(listFiles())){
            if (f.isDirectory()) files.addLast(new Dossier(f.getPath()));
            else if (getFileExtension(f).equals(".class") && f.isFile()) files.addFirst(new Classe(f.getPath()));
        }
    }

    @Override
    public String getFile() {
        String res = '-'+getName()+'\n';
        for (FichierComposite f:files){
            for (String s:f.getFile().split("\n")){
                res += "| "+ s+'\n';
            }
        }
        return res;
        //return getPath();
    }
}
