package MVC;

import Classes.FichierComposite;
import javafx.scene.control.TreeItem;

public class TreeItemFile extends TreeItem {
    private FichierComposite f;
    public TreeItemFile(FichierComposite f) {
        super();
        this.f = f;
        this.setValue(f.getName());
    }

    public FichierComposite getF() {return f;}
}
