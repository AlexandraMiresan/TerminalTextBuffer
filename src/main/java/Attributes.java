public class Attributes {
    private Color foreground;
    private Color background;

    private boolean Bold;
    private boolean Italic;
    private boolean Underline;

    public Attributes(Color foreground, Color background, boolean bold, boolean italic, boolean underline) {
        this.foreground = foreground;
        this.background = background;
        this.Bold = bold;
        this.Italic = italic;
        this.Underline = underline;
    }

    public Attributes() {
        this.foreground = Color.DEFAULT;
        this.background = Color.DEFAULT;
        this.Bold = false;
        this.Italic = false;
        this.Underline = false;
    }

    public Color getForeground() {
        return this.foreground;
    }

    public Color getBackground() {
        return this.background;
    }

    public boolean isBold() {
        return this.Bold;
    }

    public boolean isItalic() {
        return this.Italic;
    }

    public boolean isUnderline() {
        return this.Underline;
    }

    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public void setBold(boolean bold) {
        this.Bold = bold;
    }

    public void setItalic(boolean italic) {
        this.Italic = italic;
    }

    public void setUnderline(boolean underline) {
        this.Underline = underline;
    }

    public void setAttributes(Color foreground, Color background, boolean bold, boolean italic, boolean underline) {
        this.foreground = foreground;
        this.background = background;
        this.Bold = bold;
        this.Italic = italic;
        this.Underline = underline;
    }

    public void setAttributes(Attributes newAttributes) {
        this.foreground = newAttributes.getForeground();
        this.background = newAttributes.getBackground();
        this.Bold = newAttributes.isBold();
        this.Italic = newAttributes.isItalic();
        this.Underline = newAttributes.isUnderline();
    }

}
