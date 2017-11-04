package command.field.units;

import command.field.Player;

public abstract class Unit {
    protected int hp;
    protected int damage;
    public static enum UnitType {
        SCOUT,MOUNTAINEER,NAVAL,GENERAL
    }
    protected UnitType unitType;
    protected int unitCost;
    protected Player owner;
    
    Unit(Player _owner) {
        owner = _owner;
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
    
    public abstract void move();
}
