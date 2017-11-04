package command.field;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

public class Menu {
    public enum MenuType {
        MAIN,UNIT_SELECTION,UNIT_INFO,OPTIONS
    }
    private static Graphics2D g = CommandField.g;
    private static MenuType menuType = MenuType.MAIN;
    private static Tile selection;
    
    public static void Draw() {
        switch(menuType) {
            case MAIN:
                MainMenu();
                break;
            case OPTIONS:
                OptionsMenu();
                break;
            case UNIT_SELECTION:
                UnitSelectionMenu();
                break;
            case UNIT_INFO:
                UnitInfoMenu();
                break;
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
        g.setColor(new Color(235,235,235));
        g.fillRect(Window.getX(Board.GetBoardWidth()), Window.getY(0), Window.getWidth2()-960, Window.getY(Window.getYNormal(100)));
        
        Font ringbearerBody = LoadFont(20);
        g.setColor(Color.black);
        g.setFont(ringbearerBody);
        g.drawString("Tile:   " + (selection.getRow()+1) + ", " + selection.getCol(), Window.getX(Board.GetBoardWidth()+20), Window.getY(60));
        g.drawString("Type:   " + String.valueOf(selection.getType()).toLowerCase(), Window.getX(Board.GetBoardWidth()+20), Window.getY(100));
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
        menuType = MenuType.UNIT_SELECTION;
        CommandField.inGame = true;
    }
    
    public static void SetSelection(Tile _selection) {
        selection = _selection;
    }
    
    public static void SetMenuType(MenuType _type) {
        menuType = _type;
    }
}
