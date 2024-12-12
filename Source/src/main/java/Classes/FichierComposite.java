package Classes;

import java.io.File;

public abstract class FichierComposite extends File {
    public FichierComposite(String pathname) {
        super(pathname);
    }

    public abstract String getFile();
}
