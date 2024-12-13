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
        ClasseComplete c = (new Classe("Source/src/main/java/MVC/Sujet").getClasseComplete());

        assertEquals("Sujet", c.getNom());
        assertEquals("classe", c.getType());
    }
}
