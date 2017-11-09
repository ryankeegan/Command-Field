package command.field.units;

import command.field.Player;
import command.field.Tile;
import java.util.ArrayList;

public abstract class Unit {
    protected int hp;
    protected int damage;
    protected String unitIcon;
    public static enum UnitType {
        SCOUT,MOUNTAINEER,NAVAL,GENERAL
    }
    protected UnitType unitType;
    protected int unitCost;
    protected int move_pts=50;
    //protected Tile.TerrainType[] allowed;
    protected int numAllowed;
    
    

    protected Player owner;
    
    Unit(Player _owner) {
        owner = _owner;
        //allowed = new Tile.TerrainType[numAllowed];
    }
    
    public int getHP() {
        return hp;
    }
    
    public int getDamage() {
        return damage;
    }
    
    public int getCost() {
        return unitCost;
    }
    
    public void dealDamage(int _damage){
        hp-=_damage;
    }
    
    public UnitType getType() {
        return unitType;
    }
    
    public static Unit ResolveUnitType(UnitType _unit, Player _owner) {
        switch(_unit) {
            case SCOUT:
                return(new UnitScout(_owner));
            case MOUNTAINEER:
                return(new UnitMountaineer(_owner));
            case NAVAL:
                return(new UnitNaval(_owner));
            case GENERAL:
                return(new UnitGeneral(_owner));
        }
        return(null);
    }
    
    public static int ResolveUnitTypeCost(UnitType _unit) {
        Player _owner = null;
        switch(_unit) {
            case SCOUT:
                return((new UnitScout(_owner)).getCost());
            case MOUNTAINEER:
                return((new UnitMountaineer(_owner)).getCost());
            case NAVAL:
                return((new UnitNaval(_owner)).getCost());
            case GENERAL:
                return((new UnitGeneral(_owner)).getCost());
        }
        return(0);
    }
    
    public Player getOwner() {
        return owner;
    }
    
    public abstract void move(Tile movefrom, Tile moveto);
    public abstract boolean moveValid(Tile movefrom, Tile moveto);
    public abstract int getMoveCost(Tile movefrom, Tile moveto);
    public abstract String getUnitIcon();
    public abstract void attack(Tile attackfrom, Tile attackto);
    //public abstract void attack();
}
