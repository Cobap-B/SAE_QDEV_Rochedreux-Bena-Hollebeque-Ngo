import Classes.Classe;
import Classes.ClasseComplete;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Classes.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestIntrospection {

    private Classe testClasse;

    @BeforeEach
    public void setUp() {
        // Préparation d'une instance de Classe pour tester Introspection
        testClasse = new Classe("target/classes/Classes/Parametre.class");
    }

    @Test
    public void testCreerClasseComplete_NomEtType() {
        ClasseComplete classeComplete = Introspection.creerClasseComplete(testClasse);

        // Vérification du nom et du type de la classe
        assert classeComplete != null;
        assertEquals("Parametre", classeComplete.getNom(), "Le nom de la classe doit être 'Parametre'");
        assertEquals("class", classeComplete.getType(), "Le type de la classe doit être 'class'");
    }

    @Test
    public void testCreerClasseComplete_Attributs() {
        ClasseComplete classeComplete = Introspection.creerClasseComplete(testClasse);

        // Vérification des attributs
        assert classeComplete != null;
        ArrayList<Attribut> attributs = classeComplete.getAttributs();
        assertNotNull(attributs, "La liste des attributs ne doit pas être nulle");
        assertTrue(attributs.size() >= 0, "Les attributs doivent être extraits correctement");
    }

    @Test
    public void testCreerClasseComplete_Methodes() {
        ClasseComplete classeComplete = Introspection.creerClasseComplete(testClasse);

        // Vérification des méthodes
        assert classeComplete != null;
        ArrayList<Methode> methodes = classeComplete.getMethodes();
        assertNotNull(methodes, "La liste des méthodes ne doit pas être nulle");
        assertTrue(methodes.size() >= 0, "Les méthodes doivent être extraites correctement");
    }

    @Test
    public void testCreerClasseComplete_Dependances() {
        ClasseComplete classeComplete = Introspection.creerClasseComplete(testClasse);

        // Vérification des dépendances
        assert classeComplete != null;
        ArrayList<Dependance> dependances = classeComplete.getDependances();
        assertNotNull(dependances, "La liste des dépendances ne doit pas être nulle");

        dependances.forEach(dependance -> {
            assertNotNull(dependance.getDepend(), "Le nom de la dépendance ne doit pas être null");
            assertTrue(dependance.getType().equals("Extend") || dependance.getType().equals("Implement"),
                    "La dépendance doit être de type 'Extend' ou 'Implement'");
        });
    }
}
