import Classes.Dossier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TestComposite {
    Dossier dossier;

    @BeforeEach
    public void setup(){
        dossier = new Dossier("src/main/java/Classes");
    }

    @Test
    public void testOuvrirPremierFichier(){
        String res = dossier.getFile();
        String compare = "Parametre.java";

        int pos1 = res.indexOf('>');
        int pos2 = res.indexOf('(');

        assertEquals(res.substring((pos1+1), pos2), compare);
    }
}
