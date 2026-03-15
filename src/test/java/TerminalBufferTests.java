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

