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

