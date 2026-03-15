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

