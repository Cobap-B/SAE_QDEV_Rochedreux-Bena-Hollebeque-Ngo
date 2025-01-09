package Classes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class FichierComposite extends File {
    public List<Dossier> files =new ArrayList<>();
    public List<Classe> file2s =new ArrayList<>();
    public FichierComposite(String pathname) {
        super(pathname);
    }

    public abstract String getFile();
}
