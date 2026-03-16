import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;



class TerminalLineTests {

    public int getLineSize(TerminalLine line){
        return line.getLine().size();
    }

    // Verifies the constructor creates a line with the specified length filled with spaces.
    @Test
    void testDefaultConstructor(){
        TerminalLine line = new TerminalLine(5);
        assertEquals(5, getLineSize(line));

        for(int i = 0; i < getLineSize(line); i++){
            assertEquals(' ', line.getCell(i).getCodePoint());
        }
    }

    //  Tests setting the entire line and individual cells updates the stored data correctly.
    @Test
    void testIndividualSetters(){
        TerminalLine line = new TerminalLine(5);
        List<TerminalCell> cells = new ArrayList<>();
        TerminalCell cell = new TerminalCell();

        for(int i = 0; i < 6; i++){
            cells.add(cell);
        }

        line.setWrapped(true);
        line.setLine(cells);
        assertEquals(cells.size(), getLineSize(line));

        assertEquals(cells, line.getLine());
        assertTrue(line.isWrapped());

        Attributes attributes = new Attributes();
        TerminalCell cell2 = new TerminalCell('a', attributes);
        line.setCell(0, cell2);
        assertEquals('a', line.getCell(0).getCodePoint());
        assertEquals(attributes, line.getCell(0).getAttributes());

    }

    // Ensures the string representation of the line contains the characters of all cells.
    @Test
    void testToString(){
        TerminalLine line = new TerminalLine(5);

        Attributes attributes = new Attributes();
        TerminalCell cell = new TerminalCell('a', attributes);

        for(int i = 0; i < getLineSize(line); i++){
            line.setCell(i, cell);
        }

        String lineToString = line.toString();

        assertEquals("aaaaa", lineToString);
    }
}
