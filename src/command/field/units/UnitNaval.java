package command.field.units;

import command.field.Board;
import command.field.Player;
import command.field.Tile;

public class UnitNaval extends Unit {
    Tile.TerrainType[] allowed;
    UnitNaval(Player _owner) {
        super(_owner);
        unitCost = 10;
        hp = 90;
        damage = 25;
        unitType = UnitType.NAVAL;
        numAllowed=1;
        allowed = new Tile.TerrainType[numAllowed];
        allowed[0]=Tile.TerrainType.WATER;
        unitIcon = "+";
    }
    
    public void move(Tile movefrom,Tile moveto) {
        for(int i=0;i<numAllowed;i++){
            if(moveto.getType()== allowed[i]){
                if(getMoveCost(movefrom,moveto)<=move_pts){
                    movefrom.removeUnit();
                    moveto.addUnit(this);
                }
            }
        }
    }
    public int getMoveCost(Tile movefrom, Tile moveto) {
       int cost=0;
       double multiplier=0;
       int xmove=Math.abs(moveto.getCol()-movefrom.getCol());
       int ymove=Math.abs(moveto.getRow()-movefrom.getRow());
       for(int i=1;i<=xmove;i++){
           switch(Board.getTileOf(movefrom.getRow(), i).getType()){
            case WATER:
                multiplier=0.7;
            case SAND:
                multiplier=0;
            case GRASS:
                multiplier=0;
            case FOREST:
               multiplier=0;
            case ROCK:
                multiplier=0;
            case BARE_ROCK:
                multiplier=0;
        }
           cost+=1*multiplier;
       }
       
      return(cost);  
    }
    public String getUnitIcon(){
        return (unitIcon);
    }
}
