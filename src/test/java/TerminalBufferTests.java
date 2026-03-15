public class TerminalBufferTests {
}
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TerminalBufferTest {

    @Test
    void testDefaultConstructor() {
        TerminalBuffer buffer = new TerminalBuffer(4, 3, 5);

        assertEquals(0, buffer.getCursorX());
        assertEquals(0, buffer.getCursorY());

        assertEquals(' ', buffer.getCharacterAtPosition(0,0));
    }

    @Test
    void testCursorSettersClampValues() {
        TerminalBuffer buffer = new TerminalBuffer(4,3,5);

        buffer.setCursor(100,100);

        assertEquals(3, buffer.getCursorX());
        assertEquals(2, buffer.getCursorY());

        buffer.setCursor(-5,-5);

        assertEquals(0, buffer.getCursorX());
        assertEquals(0, buffer.getCursorY());
    }

    @Test
    void testCursorMovement() {
        TerminalBuffer buffer = new TerminalBuffer(5,3,5);

        buffer.moveCursorRight(3);
        assertEquals(3, buffer.getCursorX());

        buffer.moveCursorLeft(2);
        assertEquals(1, buffer.getCursorX());

        buffer.moveCursorDown(2);
        assertEquals(2, buffer.getCursorY());

        buffer.moveCursorUp(1);
        assertEquals(1, buffer.getCursorY());
    }

    @Test
    void testWriteSimple() {
        TerminalBuffer buffer = new TerminalBuffer(5,3,5);

        buffer.write("Hi");

        assertEquals('H', buffer.getCharacterAtPosition(0,0));
        assertEquals('i', buffer.getCharacterAtPosition(0,1));
    }

    @Test
    void testWriteWrapToNextLine() {
        TerminalBuffer buffer = new TerminalBuffer(5,3,5);

        buffer.write("ABCDEF");
        buffer.write("F");

        assertEquals('F', buffer.getCharacterAtPosition(1,0));
    }

    @Test
    void testWriteScroll() {
        TerminalBuffer buffer = new TerminalBuffer(3,2,5);

        buffer.write("ABCDEF");

        assertEquals('A', buffer.getCharacterAtPosition(0,0));
        assertEquals('B', buffer.getCharacterAtPosition(0,1));
        assertEquals('C', buffer.getCharacterAtPosition(0,2));
    }

    @Test
    void testInsertTextNoWrap() {

        TerminalBuffer buffer = new TerminalBuffer(5, 3, 5);

        buffer.insertText("ABC");

        assertEquals('A', buffer.getCharacterAtPosition(0,0));
        assertEquals('B', buffer.getCharacterAtPosition(0,1));
        assertEquals('C', buffer.getCharacterAtPosition(0,2));

        assertEquals(3, buffer.getCursorX());
        assertEquals(0, buffer.getCursorY());
    }

    @Test
    void testInsertTextWrapToNextLine() {

        TerminalBuffer buffer = new TerminalBuffer(3, 3, 5);

        buffer.insertText("ABCD");

        assertEquals('A', buffer.getCharacterAtPosition(0,0));
        assertEquals('B', buffer.getCharacterAtPosition(0,1));
        assertEquals('C', buffer.getCharacterAtPosition(0,2));

        assertEquals('D', buffer.getCharacterAtPosition(1,0));

        assertEquals(1, buffer.getCursorX());
        assertEquals(1, buffer.getCursorY());
    }

    @Test
    void testInsertTextScroll() {

        TerminalBuffer buffer = new TerminalBuffer(3, 2, 5);

        buffer.insertText("ABCDEFG");

        assertEquals('A', buffer.getCharacterAtPosition(0,0));
        assertEquals('B', buffer.getCharacterAtPosition(0,1));
        assertEquals('C', buffer.getCharacterAtPosition(0,2));

        assertEquals('D', buffer.getCharacterAtPosition(1,0));
    }


    @Test
    void testFillLine() {
        TerminalBuffer buffer = new TerminalBuffer(4,2,5);

        buffer.fillLine('*');

        for(int i = 0; i < 4; i++) {
            assertEquals('*', buffer.getCharacterAtPosition(0,i));
        }
    }

    @Test
    void testScrollbackOverflowRemovesOldestLine() {

        TerminalBuffer buffer = new TerminalBuffer(3, 2, 1);
        buffer.write("ABCDEF");

        buffer.write("GHI");

        char firstScrollbackChar = buffer.getCharacterAtPosition(0,0);

        assertEquals('D', firstScrollbackChar);
    }

    @Test
    void testInsertEmptyLineAtBottom() {
        TerminalBuffer buffer = new TerminalBuffer(4,2,5);

        buffer.write("AAAA");
        buffer.setCursor(0,1);
        buffer.write("BBBB");

        buffer.insertEmptyLineAtBottom();

        assertEquals('A', buffer.getCharacterAtPosition(0,0));
        assertEquals('B', buffer.getCharacterAtPosition(1,0));
        assertEquals(' ',  buffer.getCharacterAtPosition(2,0));
    }

    @Test
    void testInsertEmptyLineAtBottomRemovesScrollbackLine(){
        TerminalBuffer buffer = new TerminalBuffer(4, 1, 1);

        buffer.write("AAAA");
        buffer.write("BBBB");

        buffer.insertEmptyLineAtBottom();

        assertEquals(' ', buffer.getCharacterAtPosition(0,0));
        assertEquals(' ',  buffer.getCharacterAtPosition(1,0));

    }

    void testClearScreen() {
        TerminalBuffer buffer = new TerminalBuffer(4,2,5);

        buffer.write("Test");

        buffer.clearScreen();

        for(int r=0;r<2;r++){
            for(int c=0;c<4;c++){
                assertEquals(' ', buffer.getCharacterAtPosition(r,c));
            }
        }

        assertEquals(0, buffer.getCursorX());
        assertEquals(0, buffer.getCursorY());
    }

    @Test
    void testClearScreenAndScrollback() {
        TerminalBuffer buffer = new TerminalBuffer(4,2,5);

        buffer.write("Test");
        buffer.insertEmptyLineAtBottom();

        buffer.clearScreenAndScrollback();

        for(int r=0;r<2;r++){
            for(int c=0;c<4;c++){
                assertEquals(' ', buffer.getCharacterAtPosition(r,c));
            }
        }
    }

    @Test
    void testGetAttributesAtPositionFromScreen() {
        TerminalBuffer buffer = new TerminalBuffer(4,2,5);

        buffer.write("A");

        Attributes attributes = buffer.getAttributesAtPosition(0,0);

        assertNotNull(attributes);
    }

    @Test
    void testGetAttributesAtPositionFromScrollback(){
        TerminalBuffer buffer = new TerminalBuffer(4,2,5);

        for(int i = 0; i < 3; i++) {
            buffer.write("AAAA");
        }

        Attributes attributes = buffer.getAttributesAtPosition(0,0);

        assertNotNull(attributes);
    }

    @Test
    void testGetLineAsString() {
        TerminalBuffer buffer = new TerminalBuffer(5,2,5);

        buffer.write("Hello");

        String line = buffer.getLineAsString(0);

        assertTrue(line.startsWith("Hello"));
    }

    @Test
    void testGetScreenAsString() {
        TerminalBuffer buffer = new TerminalBuffer(5,2,5);

        buffer.write("Hi");

        String screen = buffer.getScreenAsString();

        assertTrue(screen.contains("Hi"));
    }

