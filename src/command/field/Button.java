package command.field;

import command.field.units.*;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Button {
    private static ArrayList<Button> buttons = new ArrayList<Button>();
    private static ArrayList<String> buttonNames = new ArrayList<String>();
    private static Graphics2D g = CommandField.g;
    private String contents;
    private int xPos;
    private int yPos;
    private int width;
    private int height;
    private String method;
    private String param;
    
    Button(int _xPos, int _yPos, String _contents, Font _font, String _method) {
        contents = _contents;
        xPos = _xPos;
        yPos = _yPos;
        method = _method;
        AffineTransform affinetransform = new AffineTransform();   
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        width = (int)_font.getStringBounds(_contents, frc).getWidth();
        height = (int)_font.getStringBounds(_contents, frc).getHeight();
        if(!buttonNames.contains(contents)) {
            buttonNames.add(contents);
            buttons.add(this);
        }
    }
    
    Button(int _xPos, int _yPos, String _contents, Font _font, String _method, String _param) {
        contents = _contents;
        xPos = _xPos;
        yPos = _yPos;
        method = _method;
        param = _param;
        AffineTransform affinetransform = new AffineTransform();   
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        width = (int)_font.getStringBounds(_contents, frc).getWidth();
        height = (int)_font.getStringBounds(_contents, frc).getHeight();
        if(!buttonNames.contains(contents)) {
            buttonNames.add(contents);
            buttons.add(this);
        }
    }
    
    public void draw() {
        g.drawString(contents, xPos, yPos);
    }
    
    public static void CheckCollision(int _xPos, int _yPos) throws Exception {
        for(Button button: buttons) {
            if(button.collision(_xPos, _yPos)) {
                button.callMethod();
                ClearButtons();
                break;
            }
        }
    }
    
    public boolean collision(int _xPos, int _yPos) {
        if(_xPos >= xPos && _xPos <= (xPos+width) && _yPos >= (yPos-height/2) && _yPos <= (yPos+height/2)) {
            return(true);
        } else {
            return(false);
        }
    }
    
    public void callMethod() throws Exception {
        if(param != null) {
            Menu.class.getDeclaredMethod(method, Unit.UnitType.class).invoke(null, Unit.UnitType.valueOf(param));
        } else {
            Menu.class.getDeclaredMethod(method).invoke(null);
        }
    }
    
    public static void ClearButtons() {
        buttons.clear();
        buttonNames.clear();
    }
}
