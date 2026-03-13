import java.util.ArrayList;
import java.util.List;

public class TerminalBuffer {
    private int width;
    private int height;

    private int cursorX = 0;
    private int cursorY = 0;

    private int scrollbackMaxSize;

    private List<TerminalLine> screen;
    private List<TerminalLine> scrollback;

    private Attributes currentAttributes;

    public TerminalBuffer(int width, int height, int scrollbackMaxSize) {
        this.width = width;
        this.height = height;
        this.scrollbackMaxSize = scrollbackMaxSize;

        this.screen = new ArrayList<>();
        this.scrollback = new ArrayList<>();

        for(int i = 0; i < height; i++){
            screen.add(new TerminalLine(width));
        }
    }

    public int getCursorX() {
        return cursorX;
    }

    public int getCursorY() {
        return cursorY;
    }

    public void setCursor(int cursorX, int cursorY) {
        this.cursorX = Math.max(0, Math.min(width - 1, cursorX));
        this.cursorY = Math.max(0, Math.min(height - 1, cursorY));
    }

    public void moveCursorUp(int nCells){
        cursorY = Math.max(0, cursorY - nCells);
    }

    public void moveCursorDown(int nCells){
        cursorY = Math.min(height - 1, cursorY + nCells);
    }

    public void moveCursorLeft(int nCells){
        cursorX = Math.max(0, cursorX - nCells);
    }

    public void moveCursorRight(int nCells){
        cursorX = Math.min(width - 1, cursorX + nCells);
    }

    public void write(String text){
        for(char character : text.toCharArray()){
            TerminalLine line = screen.get(cursorY);
            TerminalCell cell = line.getCell(cursorX);

            cell.setCharacter(character);
            cell.setCellAttributes(currentAttributes);

            cursorX++;

            if(cursorX >= width){
                cursorX = 0;
                cursorY++;

                if(cursorY >= height){
                    scroll();
                    cursorY = height - 1;
                }
            }
        }
    }

    private void scroll(){
        TerminalLine removed = screen.removeFirst();

        scrollback.add(removed);

        if(scrollback.size() > scrollbackMaxSize){
            scrollback.removeFirst();
        }

        screen.add(new TerminalLine(width));
    }

}
