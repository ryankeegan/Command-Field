package command.field;

import java.util.ArrayList;
import command.field.units.*;
import java.awt.Color;

public class Player {
    private static Player currentTurn;
    private static Player players[] = new Player[2];
    private ArrayList<Unit> units = new ArrayList<Unit>();
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
    
    public int getPlayerNumber() {
        return(playerNumber);
    }
    
    public int getPlayerNumberRaw() {
        return(playerNumber-1);
    }
    
    public Color getPlayerColor() {
        return(playerColor);
    }
    
    public static int GetNextTurnRaw() {
        if(GetTurn()+1 < players.length && players[GetTurn()+1] != null) {
            return(players[GetTurn()+1].getPlayerNumberRaw());
        } else {
            return(players[0].getPlayerNumberRaw());
        }
    }
}
