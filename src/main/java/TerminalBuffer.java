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

        currentAttributes = new Attributes();
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
        for(int i = 0; i < text.length();){

            int codePoint = text.codePointAt(i);
            boolean wide = isWideCharacter(codePoint);

            TerminalLine line = screen.get(cursorY);
            TerminalCell cell = line.getCell(cursorX);


           if(wide){
               if(cursorX + 1 >= width){
                   line.setWrapped(true);

                   cursorX = 0;
                   cursorY++;

                   if(cursorY >= height){
                       scroll();
                       cursorY = height - 1;
                   }

                   line = screen.get(cursorY);
                   cell = line.getCell(cursorX);
               }

               cell.setCharacter(codePoint);
               cell.setCellAttributes(currentAttributes);
               cell.setWideCharacter(false);

               TerminalCell continuation = line.getCell(cursorX + 1);
               continuation.setCharacter(' ');
               continuation.setCellAttributes(currentAttributes);
               continuation.setWideCharacter(true);

               cursorX += 2;
           }else{
               cell.setCharacter(codePoint);
               cell.setCellAttributes(currentAttributes);
               cell.setWideCharacter(false);
               cursorX += 1;
           }

           if(cursorX >= width){
               if(i < text.length() - Character.charCount(codePoint)){
                   line.setWrapped(true);
               }

               cursorX = 0;
               cursorY++;

               if(cursorY >= height){
                   scroll();
                   cursorY = height - 1;
               }
           }

           i += Character.charCount(codePoint);
        }
    }

    private boolean isWideCharacter(int codePoint){
        Character.UnicodeBlock block = Character.UnicodeBlock.of(codePoint);

        return block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || block == Character.UnicodeBlock.HANGUL_SYLLABLES
                || block == Character.UnicodeBlock.HIRAGANA
                || block == Character.UnicodeBlock.KATAKANA
                || Character.getType(codePoint) == Character.OTHER_SYMBOL;
    }

    private void scroll(){
        TerminalLine removed = screen.removeFirst();

        scrollback.add(removed);

        if(scrollback.size() > scrollbackMaxSize){
            scrollback.removeFirst();
        }

        screen.add(new TerminalLine(width));
    }

    public void insertText(String text){
        for(int i = 0; i < text.length();){
            int codePoint = text.codePointAt(i);

            insertCharacter(codePoint);

            i += Character.charCount(codePoint);
        }
    }

    private void insertCharacter(char character){
        TerminalLine line = screen.get(cursorY);

        for(int i = width - 1; i > cursorX; i--){
            line.setCell(i, line.getCell(i - 1));
        }

        line.setCell(cursorX, new TerminalCell(character, currentAttributes));
    }

    public void fillLine(char character){
        TerminalLine line = screen.get(cursorY);

        for(int i = 0; i < width; i++){
            TerminalCell cell = line.getCell(i);

            cell.setCharacter(character);
            cell.setCellAttributes(currentAttributes);
        }
    }

    public void insertEmptyLineAtBottom(){
        TerminalLine top = screen.removeFirst();

        scrollback.add(top);

        if(scrollback.size() > scrollbackMaxSize){
            scrollback.removeFirst();
        }

        screen.add(new TerminalLine(width));
    }

    public void clearScreen(){
        screen.clear();

        for(int i = 0; i < height; i++){
            screen.add(new TerminalLine(width));
        }

        cursorX = 0;
        cursorY = 0;
    }

    public void clearScrollback(){
        scrollback.clear();
    }

    public void clearScreenAndScrollback(){
        clearScreen();
        clearScrollback();
    }

    public char getCharacterAtPosition(int row, int column){
        TerminalLine line;

        if(row < scrollback.size()){
            line = scrollback.get(row);
        }else{
            int screenRow = row - scrollback.size();
            line = screen.get(screenRow);
        }

        return line.getCell(column).getCharacter();
    }

    public Attributes getAttributesAtPosition(int row, int column){
        TerminalLine line;

        if(row < scrollback.size()){
            line = scrollback.get(row);
        }else{
            int screenRow = row - scrollback.size();
            line = screen.get(screenRow);
        }

        return line.getCell(column).getAttributes();
    }

    public String getLineAsString(int row){
        TerminalLine line;

        if(row < scrollback.size()){
            line = scrollback.get(row);
        }else{
            int screenRow = row - scrollback.size();
            line = screen.get(screenRow);
        }

        return line.toString();
    }

    public String getScreenAsString(){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < height; i++){
            sb.append(getLineAsString(i));
        }

        return sb.toString();
    }

    public String getScreenAndScrollbackAsString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getScreenAsString());

        for(var item : scrollback){
            sb.append(item);
        }

        return sb.toString();
    }

    public void resize(int newWidth, int newHeight){
        List<TerminalLine> newScreen = new ArrayList<>();
        List<TerminalCell> buffer = new ArrayList<>();

        for(TerminalLine line : screen){
            for(int i = 0; i < width; i++){
                buffer.add(line.getCell(i));
            }

            if(!line.isWrapped()){
                flushBuffer(buffer, newScreen, newWidth);
            }
        }

        flushBuffer(buffer, newScreen, newWidth);

        while(newScreen.size() < newHeight){
            newScreen.add(new TerminalLine(newWidth));
        }

        while(newScreen.size() > newHeight){
            TerminalLine removed = newScreen.removeFirst();

            scrollback.add(removed);

            if(scrollback.size() > scrollbackMaxSize){
                scrollback.removeFirst();
            }
        }

        screen = newScreen;

        width = newWidth;
        height = newHeight;

        cursorX = Math.min(cursorX, width - 1);
        cursorY = Math.min(cursorY, height - 1);
    }

    private void flushBuffer(List<TerminalCell> buffer, List<TerminalLine> newScreen, int newWidth){
        int index = 0;
        while(index < buffer.size()){
            TerminalLine newLine = new TerminalLine(newWidth);

            for(int i = 0; i < newWidth && index < buffer.size(); i++){
                newLine.setCell(i, buffer.get(index++));
            }

            if(index < buffer.size()){
                newLine.setWrapped(true);
            }

            newScreen.add(newLine);
        }

        buffer.clear();
    }

}
