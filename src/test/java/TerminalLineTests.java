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
}
