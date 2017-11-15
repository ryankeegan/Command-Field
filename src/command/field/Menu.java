package command.field;

import command.field.units.Unit;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Menu {
    public static enum MenuType {
        MAIN,UNIT_SELECTION,UNIT_INFO,OPTIONS,BLANK,UNIT_MOVEMENT,UNIT_ATTACK,RULES,GAME_OVER
    }
    private static MenuType menuType = MenuType.MAIN;
    private static Tile selection;
    private static Tile movePiece;
    
    public static void Draw(Graphics2D g) {
        if(menuType != MenuType.MAIN && menuType != MenuType.UNIT_SELECTION && menuType != MenuType.GAME_OVER && CommandField.inGame) {
            Button.ClearButtons();
            Font ringbearerBody = LoadFont(20);
            g.setColor(Color.black);
            g.setFont(ringbearerBody);
            g.drawString("Player " + (Player.GetTurn()+1) + "'s Turn", Window.getX(15), Window.getY(20));
            Button nextTurn = new Button(Window.getX(270), Window.getY(20), "Next Turn", ringbearerBody, "NextTurn");
            nextTurn.draw();
        }
        
        switch(menuType) {
            case MAIN:
                MainMenu(g);
                break;
            case OPTIONS:
                OptionsMenu(g);
                break;
            case UNIT_SELECTION:
                UnitSelectionMenu(g);
                break;
            case UNIT_INFO:
                UnitInfoMenu(g);
                break;
            case UNIT_MOVEMENT:
                UnitMovementMenu(g);
                break;
            case UNIT_ATTACK:
                UnitAttackMenu(g);
                break;
            case GAME_OVER:
                GameOverMenu(g);
                break;
            case BLANK:
                BlankMenu(g);
                break;
        }
    }
    
    private static void MainMenu(Graphics2D g) {
        Font ringbearerHeader = LoadFont(40);
        Font ringbearerBody = LoadFont(20);
        g.setColor(Color.black);
        g.setFont(ringbearerHeader);
        g.drawString("Command Field", Window.getX(30), Window.getY(36));
        g.setFont(ringbearerBody);
        if(!CommandField.started) {
            Button start = new Button(Window.getX(Window.getWidth2()-270), Window.getYNormal(50), "Start", ringbearerBody, "StartGame");
            start.draw();
        } else {
            Button reset = new Button(Window.getX(Window.getWidth2()-270), Window.getYNormal(90), "Reset", ringbearerBody, "ResetGame");
            Button resume = new Button(Window.getX(Window.getWidth2()-270), Window.getYNormal(50), "Resume", ringbearerBody, "ResumeGame");
            resume.draw();
            reset.draw();
        }
        Button exit = new Button(Window.getX(Window.getWidth2()-100), Window.getYNormal(50), "Exit", ringbearerBody, "Exit");
        exit.draw();
    }
    
    private static void OptionsMenu(Graphics2D g) {
        
    }
    
    private static void UnitSelectionMenu(Graphics2D g) {
        Board.ShadeTilesTurn(Player.GetPlayer(Player.GetTurn()));
        
        Font ringbearerBody = LoadFont(20);
        g.setColor(Color.black);
        g.setFont(ringbearerBody);
        g.drawString("Player " + (Player.GetTurn()+1) + " Unit Selection", Window.getX(15), Window.getY(20));
        g.drawString("Points: " + Player.GetPlayer(Player.GetTurn()).getTroopPoints(), Window.getX(355), Window.getY(20));
        if(Player.GetPlayer(Player.GetTurn()).getNumUnits() > 0) {
            Button done = new Button(Window.getX(Window.getWidth2()-100), Window.getYNormal(50), "Done", ringbearerBody, "ConfirmUnitPlacement");
            done.draw();
        }
        
        if(selection != null && Player.GetPlayer(Player.GetTurn()).getTroopPoints() > 0) {
            g.drawString("Tile:   " + (selection.getRow()+1) + ", " + selection.getCol(), Window.getX(Board.GetBoardWidth()+20), Window.getY(60));
            g.drawString("Type:   " + String.valueOf(selection.getType()).toLowerCase(), Window.getX(Board.GetBoardWidth()+20), Window.getY(100));
            if(selection.getUnit() != null) {
                g.drawString("Unit Type:   " + String.valueOf(selection.getUnit().getType()).toLowerCase(), Window.getX(Board.GetBoardWidth()+20), Window.getY(140));
                g.drawString("Unit Owner:   Player " + selection.getUnit().getOwner().getPlayerNumber(), Window.getX(Board.GetBoardWidth()+20), Window.getY(180));
            } else if(selection.getUnit() == null  && Board.AllowedPlacementTiles(Player.GetPlayer(Player.GetTurn()), selection.getRow(), selection.getCol())) {
                g.drawString("Unit Type:   Free", Window.getX(Board.GetBoardWidth()+20), Window.getY(140));
                
                Map<String, Button> buttonMap = new HashMap<String, Button>();
                int i = 0;
                for(Unit.UnitType unit : Unit.UnitType.values()) {
                    if(Player.GetPlayer(Player.GetTurn()).getTroopPoints()-Unit.ResolveUnitTypeCost(unit) >= 0 && Unit.ResolveUnitTileCheck(unit, selection)) { //Insert tile type check here
                        buttonMap.put(unit.name(), new Button(Window.getX(Board.GetBoardWidth()+20), Window.getYNormal(100+40*i), unit.name(), ringbearerBody, "MenuAddUnit", unit.toString()));
                        i++;
                    }
                }
                
                for(Map.Entry<String, Button> button : buttonMap.entrySet()) {
                    button.getValue().draw();
                }
            }
        }
    }
    
    private static void UnitInfoMenu(Graphics2D g) {
        g.setColor(new Color(255,255,255));
        g.fillRect(Window.getX(Board.GetBoardWidth()+5), Window.getY(0), Window.getWidth2()-975, Window.getY(Window.getYNormal(100)));
        
        Font ringbearerBody = LoadFont(20);
        g.setColor(Color.black);
        g.setFont(ringbearerBody);
        g.drawString("Tile:   " + (selection.getRow()+1) + ", " + selection.getCol(), Window.getX(Board.GetBoardWidth()+20), Window.getY(60));
        g.drawString("Type:   " + String.valueOf(selection.getType()).toLowerCase(), Window.getX(Board.GetBoardWidth()+20), Window.getY(100));
        if(selection.getUnit() != null) {
            g.drawString("Unit Owner:   Player " + selection.getUnit().getOwner().getPlayerNumber(), Window.getX(Board.GetBoardWidth()+20), Window.getY(140));
            g.drawString("Unit Type:   " + String.valueOf(selection.getUnit().getType()).toLowerCase(), Window.getX(Board.GetBoardWidth()+20), Window.getY(180));
            g.drawString("Unit HP:   " + selection.getUnit().getHP() + "/" + Unit.ResolveUnitHP(selection.getUnit().getType()), Window.getX(Board.GetBoardWidth()+20), Window.getY(220));
            g.drawString("Unit Damage:   " + selection.getUnit().getDamage(), Window.getX(Board.GetBoardWidth()+20), Window.getY(260));
            if(selection.getUnit().getOwner() == Player.GetPlayer(Player.GetTurn())) {
                g.drawString("Unit Action Points:   " + selection.getUnit().getMovePoints(), Window.getX(Board.GetBoardWidth()+20), Window.getY(300));
                Button move = new Button(Window.getX(Window.getWidth2()-270), Window.getYNormal(50), "Move", ringbearerBody, "OpenMoveUnitMenu");
                move.draw();
                Button attack = new Button(Window.getX(Window.getWidth2()-100), Window.getYNormal(50), "Attack", ringbearerBody, "OpenAttackUnitMenu");
                attack.draw();
            }
        }
    }
    
    private static void BlankMenu(Graphics2D g) {
        g.setColor(new Color(255,255,255));
        g.fillRect(Window.getX(Board.GetBoardWidth()+5), Window.getY(0), Window.getWidth2()-975, Window.getY(Window.getYNormal(100)));
    }
    
    private static void UnitMovementMenu(Graphics2D g) {
        if(CommandField.inGame) {
            Font ringbearerBody = LoadFont(20);
            g.setColor(Color.black);
            g.setFont(ringbearerBody);
            g.drawString("Unit Tile:   " + (movePiece.getRow()+1) + ", " + movePiece.getCol(), Window.getX(Board.GetBoardWidth()+20), Window.getY(60));
            g.drawString("Selected Tile:   " + (selection.getRow()+1) + ", " + selection.getCol(), Window.getX(Board.GetBoardWidth()+20), Window.getY(100));
            g.drawString("Unit Action Points:   " + movePiece.getUnit().getMovePoints(), Window.getX(Board.GetBoardWidth()+20), Window.getY(140));
            g.drawString("Post Action:   " + movePiece.getUnit().getMovePoints() + "-" + movePiece.getUnit().getMoveCost(movePiece, selection) + "=" + (movePiece.getUnit().getMovePoints() - movePiece.getUnit().getMoveCost(movePiece, selection)), Window.getX(Board.GetBoardWidth()+20), Window.getY(180));

            //System.out.println(movePiece);
               for(int col=1; col<Board.GetNumColumns(); col++) {
                    for(int row=1; row<Board.GetNumRows()-1; row++) {
                       // System.out.println(Board.GetTileOf(row, col));
                        if(Board.GetTileOf(row, col).getUnit()==null && movePiece.getUnit().moveValid(movePiece, Board.GetTileOf(row, col))){
                            Board.GetTileOf(row, col).setShaded(true, new Color(255,0,0,125));
                        }
                    }
                }

            
            if(movePiece.getUnit().tileValid(selection) && movePiece.getUnit().moveValid(movePiece, selection) && selection.getUnit() == null) {
                g.drawString("Movement Cost:   " + movePiece.getUnit().getMoveCost(movePiece, selection), Window.getX(Board.GetBoardWidth()+20), Window.getY(220));
                Button confirm = new Button(Window.getX(Window.getWidth2()-270), Window.getYNormal(50), "Confirm", ringbearerBody, "ConfirmMoveUnit");
                confirm.draw();
            } else {
                g.drawString("Invalid Move", Window.getX(Board.GetBoardWidth()+20), Window.getY(220));
            }
            
            Button cancel = new Button(Window.getX(Window.getWidth2()-100), Window.getYNormal(50), "Cancel", ringbearerBody, "Cancel");
            cancel.draw();
        }
    }
    
    private static void UnitAttackMenu(Graphics2D g) {
        if(CommandField.inGame) {
            Font ringbearerBody = LoadFont(20);
            g.setColor(Color.black);
            g.setFont(ringbearerBody);
            g.drawString("Unit Tile:   " + (movePiece.getRow()+1) + ", " + movePiece.getCol(), Window.getX(Board.GetBoardWidth()+20), Window.getY(60));
            g.drawString("Selected Tile:   " + (selection.getRow()+1) + ", " + selection.getCol(), Window.getX(Board.GetBoardWidth()+20), Window.getY(100));
            g.drawString("Unit Action Points:   " + movePiece.getUnit().getMovePoints(), Window.getX(Board.GetBoardWidth()+20), Window.getY(140));
            g.drawString("Post Action:   " + movePiece.getUnit().getMovePoints() + "-" + (movePiece.getUnit().getAttackCost(movePiece, selection)) + "=" + (movePiece.getUnit().getMovePoints() - movePiece.getUnit().getAttackCost(movePiece, selection)), Window.getX(Board.GetBoardWidth()+20), Window.getY(180));
            
            //System.out.println(movePiece);
               for(int col=1; col<Board.GetNumColumns(); col++) {
                    for(int row=1; row<Board.GetNumRows()-1; row++) {
                       // System.out.println(Board.GetTileOf(row, col));
                        if( movePiece.getUnit().attackValid(movePiece, Board.GetTileOf(row, col))){
                            Board.GetTileOf(row, col).setShaded(true, new Color(255,0,0,125));
                        }
                    }
                }
            if(movePiece.getUnit().attackValid(movePiece, selection) && selection.getUnit() != null) {
                g.drawString("Attack Cost:   " + movePiece.getUnit().getAttackCost(movePiece, selection), Window.getX(Board.GetBoardWidth()+20), Window.getY(220));
                g.drawString("Selected Unit HP:   " + selection.getUnit().getHP() + "/" + Unit.ResolveUnitHP(selection.getUnit().getType()), Window.getX(Board.GetBoardWidth()+20), Window.getY(260));
                Button confirm = new Button(Window.getX(Window.getWidth2()-270), Window.getYNormal(50), "Confirm", ringbearerBody, "ConfirmAttackUnit");
                confirm.draw();
            } else {
                g.drawString("Invalid Attack", Window.getX(Board.GetBoardWidth()+20), Window.getY(220));
            }
            
            Button cancel = new Button(Window.getX(Window.getWidth2()-100), Window.getYNormal(50), "Cancel", ringbearerBody, "Cancel");
            cancel.draw();
        }
    }
    
    private static void GameOverMenu(Graphics2D g) {
        Font ringbearerBody = LoadFont(20);
        g.setColor(Player.GetWinner().getPlayerColor());
        g.setFont(ringbearerBody);
        g.drawString("Game Over", Window.getX(Board.GetBoardWidth()+20), Window.getY(60));
        g.drawString("Player " + Player.GetWinner().getPlayerNumber() + " has won", Window.getX(Board.GetBoardWidth()+20), Window.getY(100));
    }
    
    private static Font LoadFont(float _size) {
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
    
    public static void ResumeGame() {
        if(!CommandField.gameOver) {
            menuType = MenuType.BLANK;
        } else {
            menuType = MenuType.GAME_OVER;
        }
        CommandField.inGame = true;
    }
    
    public static void ResetGame() {
        CommandField.reset();
    }
    
    public static void OpenMoveUnitMenu() {
        menuType = MenuType.UNIT_MOVEMENT;
        movePiece = selection;
    }
    
    public static void OpenAttackUnitMenu() {
        menuType = MenuType.UNIT_ATTACK;
        movePiece = selection;
    }
    
    public static void ConfirmUnitPlacement() {
        if(Player.GetTurn() >= Player.GetNumPlayers()) {
            menuType = MenuType.BLANK;
            CommandField.started = true;
            Board.ClearShadedTiles();
            Player.SwitchTurn();
        } else {
            Player.SwitchTurn();
        }
    }
    
    public static void SetSelection(Tile _selection) {
        selection = _selection;
    }
    
    public static void SetMenuType(MenuType _type) {
        menuType = _type;
    }
    
    public static MenuType GetMenuType() {
        return menuType;
    }
    
    public static void OpenBlankMenu() {
        menuType = MenuType.BLANK;
    }
    
    public static void MenuAddUnit(Unit.UnitType _unit) {
        Unit newUnit = Unit.ResolveUnitType(_unit, Player.GetPlayer(Player.GetTurn()));
        Player.GetPlayer(Player.GetTurn()).addUnit(newUnit);
        selection.addUnit(newUnit);
    }
    
    public static Tile GetSelectedTile() {
        return(selection);
    }
    
    public static void ConfirmMoveUnit() {
        movePiece.getUnit().move(movePiece, selection);
        Button.ClearButtons();
        menuType = MenuType.UNIT_INFO;
        Board.checkWINunit();
        Board.ClearShadedTiles();
    }
    
    public static void ConfirmAttackUnit() {
        movePiece.getUnit().attack(movePiece, selection);
        Button.ClearButtons();
        menuType = MenuType.UNIT_INFO;
        Board.checkWINunit();
        Board.ClearShadedTiles();
    }
    
    public static void NextTurn() {
        Player.SwitchTurn();
        Unit.ReplenishMovePoints();
        menuType = MenuType.UNIT_INFO;
    }
    
    public static void Cancel() {
        selection = movePiece;
        menuType = MenuType.UNIT_INFO;
        for(int col=1; col<Board.GetNumColumns(); col++) {
            for(int row=1; row<Board.GetNumRows()-1; row++) {
                Board.GetTileOf(row, col).setShaded(false);
            }
        }
    }
}
