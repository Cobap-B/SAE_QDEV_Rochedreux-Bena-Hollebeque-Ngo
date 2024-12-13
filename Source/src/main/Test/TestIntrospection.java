import Classes.Classe;
import Classes.ClasseComplete;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIntrospection {

    @BeforeEach
    public void setUp() {}

    @Test
    public void testClasseComplete(){
        ClasseComplete c = (new Classe("java/Classes/Parametre.java").getClasseComplete());

        assertEquals("Parametre", c.getNom());
        assertEquals("class", c.getType());
    }
}
