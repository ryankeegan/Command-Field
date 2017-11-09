package command.field.units;

import command.field.Board;
import command.field.Player;
import command.field.Tile;

public class UnitGeneral extends Unit {
    Tile.TerrainType[] allowed;
    UnitGeneral(Player _owner) {
        super(_owner);
        unitCost = 20;
        hp = 75;
        damage = 40;
        unitType = Unit.UnitType.GENERAL;
        numAllowed=3;
        allowed = new Tile.TerrainType[numAllowed];
        allowed[0]=Tile.TerrainType.GRASS;
        allowed[1]=Tile.TerrainType.FOREST;
        allowed[2]=Tile.TerrainType.SAND;
        unitIcon = "«";

    }
    
    public void move(Tile movefrom,Tile moveto) {
        for(int i=0;i<numAllowed;i++){
            if(moveto.getType()==allowed[i]){
                if(getMoveCost(movefrom,moveto)<=move_pts){
                    movefrom.removeUnit();
                    moveto.addUnit(this);
                    move_pts-=getMoveCost(movefrom,moveto);
                }
            }
        }
    }
    public boolean moveValid(Tile movefrom,Tile moveto) {
        for(int i=0;i<numAllowed;i++){
            if(moveto.getType()==allowed[i]){
                if(getMoveCost(movefrom,moveto)<=move_pts){
                    return true;
                }
                
            }
        }
        return false;
    }
    public int getMoveCost(Tile movefrom, Tile moveto) {
       int cost=0;
       double multiplier=0;
       int xmove=Math.abs(moveto.getCol()-movefrom.getCol());
       int ymove=Math.abs(moveto.getRow()-movefrom.getRow());
       for(int i=1;i<=xmove;i++){
           switch(Board.getTileOf(movefrom.getRow(), i).getType()){
            case WATER:
                multiplier=0;
            case SAND:
                multiplier=1.8;
            case GRASS:
                multiplier=1;
            case FOREST:
               multiplier=1.5;
            case ROCK:
                multiplier=0;
            case BARE_ROCK:
                multiplier=0;
        }
           cost+=10*multiplier;
       }
       for(int i=1;i<=ymove;i++){
           switch(Board.getTileOf(i, movefrom.getCol()).getType()){
            case WATER:
                multiplier=0;
            case SAND:
                multiplier=1.8;
            case GRASS:
                multiplier=1;
            case FOREST:
               multiplier=1.5;
            case ROCK:
                multiplier=0;
            case BARE_ROCK:
                multiplier=0;
        }
           cost+=10*multiplier;
       }
       
      return(cost);  
    }
    public String getUnitIcon(){
        return (unitIcon);
    }
    public boolean attackValid(Tile attackfrom, Tile attackto){
        if(moveValid(attackfrom,attackto)){
            if(attackto.getUnit()!=null && attackto.getUnit().getOwner()!=owner) {
                return (true);
            }
        }
        return false;
    }
    public  void attack(Tile attackfrom, Tile attackto){
        if(attackValid(attackfrom, attackto)){
                attackto.getUnit().dealDamage(damage);
                attackfrom.removeUnit();
                if(attackto.getUnit().getHP()<=0){
                   attackto.removeUnit();
                   move(attackfrom,attackto);
                }
                else
                move(attackfrom,Board.getTileOf(attackto.getRow()+1,attackto.getCol() ));
        }
        
    }
}
