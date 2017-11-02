package command.field;

import java.awt.Graphics2D;

public class Button {
    private static Graphics2D g = CommandField.g;
    private String contents;
    private int xPos;
    private int yPos;
    
    Button(int _xPos, int _yPos, String _contents) {
        contents = _contents;
        xPos = _xPos;
        yPos = _yPos;
    }
    
    public void draw() {
        g.drawString(contents, xPos, yPos);
    }
}
