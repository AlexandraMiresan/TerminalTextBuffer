import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



class TerminalLineTests {

    public int getLineSize(TerminalLine line){
        return line.getLine().size();
    }

    @Test
    void testDefaultConstructor(){
        TerminalLine line = new TerminalLine(5);
        assertEquals(5, getLineSize(line));

        for(int i = 0; i < getLineSize(line); i++){
            assertEquals(' ', line.getCell(i).getCharacter());
        }
    }

    @Test
    void testIndividualSetters(){
        TerminalLine line = new TerminalLine(5);
        List<TerminalCell> cells = new ArrayList<>();
        TerminalCell cell = new TerminalCell();

        for(int i = 0; i < 6; i++){
            cells.add(cell);
        }

        line.setLine(cells);
        assertEquals(cells.size(), getLineSize(line));

        assertEquals(cells, line.getLine());

        Attributes attributes = new Attributes();
        TerminalCell cell2 = new TerminalCell('a', attributes);
        line.setCell(0, cell2);
        assertEquals('a', line.getCell(0).getCharacter());
        assertEquals(attributes, line.getCell(0).getAttributes());

    }
}
