package command.field;

import java.util.ArrayList;
import command.field.units.*;
import java.awt.Color;

public class Player {
    private static Player currentTurn;
    private static Player players[] = new Player[2];
    private ArrayList<Unit> units = new ArrayList<Unit>();
    private ArrayList<Unit> winUnits = new ArrayList<Unit>();
    private static Player winner;
    private int playerNumber;
    private static int numPlayers = 1;
    private Color playerColor;
    private int troopPoints; //Points to allocate to troops at beginning of round
    
    Player() {
        playerNumber = numPlayers;
        numPlayers++;
        troopPoints = 100;
    }
    
    public static void PlayersInit() {
        if(players[0] == null) {
            players[0] = new Player();  //Add player 1
            players[1] = new Player();  //Add player 2
        }
        
        currentTurn = players[0];
        
        for(int i = 0; i<players.length; i++) {
            if(players[i] != null) {
                players[i].units.clear();
                players[i].troopPoints = 100;
            }
        }
        
        players[0].playerColor = Color.black;
        players[1].playerColor = Color.red;
    }
    
    public static void SwitchTurn() {
        if(GetTurn()+1 < players.length && players[GetTurn()+1] != null) {
            currentTurn = players[GetTurn()+1];
        } else {
            currentTurn = players[0];
        }
    }
    
    public static int GetTurn() {
        for(int i = 0; i<players.length; i++) {
            if(players[i] == currentTurn) {
                return i;
            }
        }
        return 0;
    }
    
    public static Player GetPlayer(int _playerID) {
        return(players[_playerID]);
    }
    
    public static int GetNumPlayers() {
        return(players.length-1);
    }
    
    public int getTroopPoints() {
        return(troopPoints);
    }
    
    public void addUnit(Unit _unit) {
        troopPoints -= _unit.getCost();
        units.add(_unit);
    }
    
    public void removeUnit(Unit _unit) {
        units.remove(_unit);
    }
    
    public void addWinUnit(Unit _unit) {
        winUnits.add(_unit);
    }
    
    public int getPlayerNumber() {
        return(playerNumber);
    }
    
    public int getPlayerNumberRaw() {
        return(playerNumber-1);
    }
    
    public Color getPlayerColor() {
        return(playerColor);
    }
    
    public ArrayList<Unit> getUnits() {
        return(units);
    }
    
    public ArrayList<Unit> getWinUnits() {
        return(winUnits);
    }
    
    public static int GetNextTurnRaw() {
        if(GetTurn()+1 < players.length && players[GetTurn()+1] != null) {
            return(players[GetTurn()+1].getPlayerNumberRaw());
        } else {
            return(players[0].getPlayerNumberRaw());
        }
    }
    
    public int getNextTurnRelative() {
        if(playerNumber+1 < players.length && players[GetTurn()+1] != null) {
            return(players[GetTurn()+1].getPlayerNumberRaw());
        } else {
            return(players[0].getPlayerNumberRaw());
        }
    }
    
    public int getNumUnits() {
        return(units.size());
    }
    
    private boolean checkWinUnitsCross(){
      if(winUnits.size() == 3) {
          return true;
      } 
      return false;
    }
    
    private boolean checkWinGensCross(){
        for(Unit gen : winUnits ) {
            if(gen.getType() == Unit.UnitType.GENERAL) {
                return true;
            }
        }
        return false;
    }
    
    public static Player checkUnitsDestroyed() {
        for(Player player : players) {
            if(player.units.isEmpty()) {
                return(player);
            }
        }
        return(null);
    }
    
    public static Player checkGeneralsDestroyed() {
        for(Player player : players) {
            if(player.checkGeneralsDestroyedSelf()) {
                return(player);
            }
        }
        
        return(null);
    }
    
    public boolean checkGeneralsDestroyedSelf() {
        for(Unit gen : units) {
            if(gen.getType() == Unit.UnitType.GENERAL) {
                return(false);
            }
        }
        
        for(Unit gen : winUnits) {
            if(gen.getType() == Unit.UnitType.GENERAL) {
                return(false);
            }
        }
        
        return(true);
    }
    
    public static void CheckGameOver() {
        for(Player player : players) {
            if ((checkUnitsDestroyed() != null || player.checkWinGensCross() || player.checkWinUnitsCross()) && CommandField.started && !CommandField.gameOver) {
                currentTurn = player;
                if(checkUnitsDestroyed().playerNumber == 1) {
                    SwitchTurn();
                }
                
                winner = GetPlayer(GetTurn());
                Menu.SetMenuType(Menu.MenuType.GAME_OVER);
                CommandField.gameOver = true;
            }
        }
    }
    
    public static Player GetWinner() {
        return winner;
    }
}