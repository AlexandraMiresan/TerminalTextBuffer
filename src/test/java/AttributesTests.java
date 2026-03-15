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

    @Test
    void testConstructorWithParameters()
    {
        Color foreground = Color.MAGENTA;
        Color background = Color.YELLOW;
        boolean bold = false;
        boolean italic = true;
        boolean underline = true;

        Attributes attributes = new Attributes(foreground, background, bold, italic, underline);

        assertEquals(foreground, attributes.getForeground());
        assertEquals(background, attributes.getBackground());

        assertFalse(attributes.isBold());
        assertTrue(attributes.isItalic());
        assertTrue(attributes.isUnderline());

    }
}