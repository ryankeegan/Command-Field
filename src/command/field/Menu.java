package command.field;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

public class Menu {
    public enum Type {
        MAIN,UNIT_SELECTION,UNIT_INFO,OPTIONS
    }
    private static Graphics2D g = CommandField.g;
    private static Type menuType = Type.MAIN;
    
    Menu() {
        
    }
    
    public static void Draw() {
        switch(menuType) {
            case MAIN:
                MainMenu();
            case OPTIONS:
                OptionsMenu();
            case UNIT_SELECTION:
                UnitSelectionMenu();
            case UNIT_INFO:
                UnitInfoMenu();
        }
    }
    
    public static void MainMenu() {
        Font ringbearerHeader = LoadFont(40);
        Font ringbearerBody = LoadFont(20);
        g.setColor(Color.black);
        g.setFont(ringbearerHeader);
        g.drawString("Command Field", 30, 90);
        g.setFont(ringbearerBody);
        g.drawString("Start", 30, Window.getYNormal(150));
        g.drawString("Exit", 30, Window.getYNormal(100));
    }
    
    public static void OptionsMenu() {
        
    }
    
    public static void UnitSelectionMenu() {
        
    }
    
    public static void UnitInfoMenu() {
        
    }
    
    public static Font LoadFont(float _size) {
        try {
            //Returned font is of pt size 1
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("ringbearer.ttf"));

            //Derive and return a 12 pt version:
            //Need to use float otherwise
            //it would be interpreted as style

            return font.deriveFont(_size);

        } catch (IOException|FontFormatException e) {
             // Handle exception
        }
        return null;
    }
}
