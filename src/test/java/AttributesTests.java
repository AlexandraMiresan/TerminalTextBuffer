import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AttributesTest {

    @Test
    void testDefaultConstructor()
    {
        Attributes attributes = new Attributes();
        assertEquals(Color.DEFAULT, attributes.getForeground());
        assertEquals(Color.DEFAULT, attributes.getBackground());

        assertFalse(attributes.isBold());
        assertFalse(attributes.isItalic());
        assertFalse(attributes.isUnderline());
    }

}