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
        g.drawString("Command Field", Window.getX(30), Window.getY(60));
        g.setFont(ringbearerBody);
        Button start = new Button(Window.getX(30), Window.getYNormal(150), "Start", ringbearerBody, "StartGame");
        Button exit = new Button(Window.getX(30), Window.getYNormal(100), "Exit", ringbearerBody, "Exit");
        start.draw();
        exit.draw();
    }
    
    public static void OptionsMenu() {
        
    }
    
    public static void UnitSelectionMenu() {
        
    }
    
    public static void UnitInfoMenu() {
        
    }
    
    public static Font LoadFont(float _size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("ringbearer.ttf"));
            return font.deriveFont(_size);
        } catch (IOException|FontFormatException e) {
        }
        return null;
    }
    
    public static void Exit() {
        System.exit(0);
    }
    
    public static void StartGame() {
        menuType = Type.UNIT_SELECTION;
        CommandField.inGame = true;
    }
}
