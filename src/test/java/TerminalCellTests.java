public class TerminalCellTests {
}import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class TerminalCellTests {

    @Test
    void testDefaultConstructor() {
        TerminalCell terminalCell = new TerminalCell();
        Attributes attributes = new Attributes();

        assertEquals(' ', terminalCell.getCharacter());
        assertEquals(Color.DEFAULT, attributes.getForeground());
        assertEquals(Color.DEFAULT, attributes.getBackground());

        assertFalse(attributes.isBold());
        assertFalse(attributes.isItalic());
        assertFalse(attributes.isUnderline());
    }

    @Test
    void testConstructorWithParameters() {
        char character = 'a';
        Attributes attributes = new Attributes();

        TerminalCell terminalCell = new TerminalCell(character, attributes);

        assertEquals(character, terminalCell.getCharacter());
        assertEquals(attributes, terminalCell.getAttributes());
    }

    @Test
    void testIndividualSetters() {
        TerminalCell terminalCell = new TerminalCell();
        char character = 'a';

        terminalCell.setCharacter(character);
        assertEquals(character, terminalCell.getCharacter());
    }

