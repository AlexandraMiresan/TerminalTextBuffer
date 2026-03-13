import java.util.ArrayList;
import java.util.List;

public class TerminalLine {
    private List<TerminalCell> cells;

    public TerminalLine(int width){
        this.cells = new ArrayList<>();

        for(int i = 0; i < width; i++){
            this.cells.add(new TerminalCell());
        }
    }

    public List<TerminalCell> getLine() {
        return cells;
    }

    public void setLine(List<TerminalCell> line) {
        this.cells = line;
    }

    public TerminalCell getCell(int index){
        return this.cells.get(index);
    }

    public void setCell(int index, TerminalCell cell){
        this.cells.set(index, cell);
    }

}
