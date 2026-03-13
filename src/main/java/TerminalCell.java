public class TerminalCell {
    private char character;

    private Attributes attributes;

    public TerminalCell(char character, Attributes attributes) {
        this.character = character;
        this.attributes = attributes;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }
    
}
