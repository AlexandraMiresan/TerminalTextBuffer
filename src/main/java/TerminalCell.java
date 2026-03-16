public class TerminalCell {

    private int codePoint;
    private Attributes attributes;

    public TerminalCell(char character, Attributes attributes) {
        this.character = character;
    private boolean wideContinuation;

    public TerminalCell(int codePoint, Attributes attributes) {
        this.codePoint = codePoint;
        this.attributes = attributes;
        this.wideContinuation = false;
    }

    public TerminalCell(){
        this.character = ' ';
        this.codePoint = ' ';
        this.attributes = new Attributes();
        this.wideContinuation = false;
    }

    public boolean isWideCharacter() {
        return wideContinuation;
    }

    public void setWideCharacter(boolean wideCharacter) {
        this.wideContinuation = wideCharacter;
    }

    public int getCodePoint() {
        return codePoint;
    }

    public void setCharacter(int codePoint) {
        this.codePoint = codePoint;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setCellAttributes(Attributes attributes) {
        this.attributes.setAttributes(attributes);
    }

    public void setCellAttributes(Color foreground, Color background, boolean bold, boolean italic, boolean underline){
        this.attributes.setAttributes(foreground, background, bold, italic, underline);
    }

    @Override
    public String toString(){
        return String.valueOf(character);
    }

}
