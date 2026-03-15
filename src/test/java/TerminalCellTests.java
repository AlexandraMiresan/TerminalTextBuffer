import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TerminalCellTests {

    // Verifies the default constructor initializes a space character and default attributes.
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

    // Tests that the parameterized constructor correctly stores character and attributes.
    @Test
    void testConstructorWithParameters() {
        char character = 'a';
        Attributes attributes = new Attributes();

        TerminalCell terminalCell = new TerminalCell(character, attributes);

        assertEquals(character, terminalCell.getCharacter());
        assertEquals(attributes, terminalCell.getAttributes());
    }

    // Ensures the character setter correctly updates the stored character.
    @Test
    void testIndividualSetters() {
        TerminalCell terminalCell = new TerminalCell();
        char character = 'a';

        terminalCell.setCharacter(character);
        assertEquals(character, terminalCell.getCharacter());
    }

    // Tests setting attributes both via an Attributes object and individual parameters.
    @Test
    void testGroupSetters() {
        TerminalCell terminalCell = new TerminalCell();
        Attributes attributes = new Attributes(Color.WHITE, Color.CYAN, true, false, false);

        terminalCell.setCellAttributes(attributes);
        assertEquals(Color.WHITE, terminalCell.getAttributes().getForeground());
        assertEquals(Color.CYAN, terminalCell.getAttributes().getBackground());

        assertTrue(terminalCell.getAttributes().isBold());
        assertFalse(terminalCell.getAttributes().isItalic());
        assertFalse(terminalCell.getAttributes().isUnderline());

        Color foreground = Color.BLUE;
        Color background = Color.MAGENTA;

        boolean bold = true;
        boolean italic = true;
        boolean underline = true;

        terminalCell.setCellAttributes(foreground, background, bold, italic, underline);

        assertEquals(foreground, terminalCell.getAttributes().getForeground());
        assertEquals(background, terminalCell.getAttributes().getBackground());
        assertEquals(bold, terminalCell.getAttributes().isBold());
        assertEquals(italic, terminalCell.getAttributes().isItalic());
        assertEquals(underline, terminalCell.getAttributes().isUnderline());
    }

    // Verifies the string representation returns the stored character.
    @Test
    void testToString() {
        Attributes attributes = new Attributes();
        TerminalCell terminalCell = new TerminalCell('a', attributes);

        String terminalCellString = terminalCell.toString();

        assertEquals("a", terminalCellString);
    }
}
