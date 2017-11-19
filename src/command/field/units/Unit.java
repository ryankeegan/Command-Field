package command.field.units;

import command.field.Board;
import command.field.Player;
import command.field.Tile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Unit {
    protected int hp;
    protected int damage;
    protected String unitIcon;
    public static enum UnitType {
        SCOUT,MOUNTAINEER,NAVAL,GENERAL
    }
    protected UnitType unitType;
    protected int unitCost;
    private static final int totalMovePoints = 50;
    protected int move_pts = totalMovePoints;
    private static ArrayList<Unit> units = new ArrayList<Unit>();
    protected ArrayList<Tile.TerrainType> allowed = new ArrayList<Tile.TerrainType>();
    protected Map<Tile.TerrainType, Double> terrainCost = new HashMap<Tile.TerrainType, Double>();
    protected Player owner;
    private static final double attackCostMult = 1.3;
    
    Unit(Player _owner) {
        owner = _owner;
        units.add(this);
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
    
    public void dealDamage(int _damage) {
        hp -= _damage;
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
    
    public static int ResolveUnitHP(UnitType _unit) {
        Player _owner = null;
        switch(_unit) {
            case SCOUT:
                return((new UnitScout(_owner)).getHP());
            case MOUNTAINEER:
                return((new UnitMountaineer(_owner)).getHP());
            case NAVAL:
                return((new UnitNaval(_owner)).getHP());
            case GENERAL:
                return((new UnitGeneral(_owner)).getHP());
        }
        return(0);
    }
    
    public static boolean ResolveUnitTileCheck(UnitType _unit, Tile _tile) {
        Player _owner = null;
        switch(_unit) {
            case SCOUT:
                return((new UnitScout(_owner)).tileValid(_tile));
            case MOUNTAINEER:
                return((new UnitMountaineer(_owner)).tileValid(_tile));
            case NAVAL:
                return((new UnitNaval(_owner)).tileValid(_tile));
            case GENERAL:
                return((new UnitGeneral(_owner)).tileValid(_tile));
        }
        return(false);
    }
    
    public Player getOwner() {
        return owner;
    }
    
    public int getMovePoints() {
        return move_pts;
    }
    
    public void decMovePoints(int _points) {
        move_pts -= _points;
    }
    
    public void move(Tile _moveFrom, Tile _moveTo) {
        if(tileValid(_moveTo) && moveValid(_moveFrom, _moveTo)) {
            _moveFrom.removeUnit();
            _moveTo.addUnit(this);
            move_pts -= getMoveCost(_moveFrom, _moveTo);
        }
    }
    
    public boolean moveValid(Tile _moveFrom, Tile _moveTo) {
        return (getMoveCost(_moveFrom, _moveTo) <= move_pts);
    }

    public boolean tileValid(Tile _tile) {
        return (allowed.contains(_tile.getType()));
    }
    
    public String getUnitIcon() {
        return (unitIcon);
    }
    
    public boolean attackValid(Tile _attackFrom, Tile _attackTo){
        if(moveValid(_attackFrom, _attackTo)) {
            if(_attackTo.getUnit() != null && _attackTo.getUnit().getOwner() != owner) {
                return true;
            }
        }
        return false;
    }
    
    public void attack(Tile _attackFrom, Tile _attackTo) {
        if(attackValid(_attackFrom, _attackTo)) {
            _attackFrom.getUnit().decMovePoints(_attackFrom.getUnit().getAttackCost(_attackFrom, _attackTo));
            _attackTo.getUnit().dealDamage(damage);
            if(_attackTo.getUnit().getHP() <= 0) {
                _attackTo.getUnit().getOwner().removeUnit(_attackTo.getUnit());
                _attackTo.removeUnit();
                if(_attackFrom.getUnit().tileValid(_attackTo)) {
                    move(_attackFrom, _attackTo);
                }
            }
        }
    }
    
    public int getAttackCost(Tile _moveFrom, Tile _moveTo) {
        return (_moveFrom.getUnit().getMoveCost(_moveFrom, _moveTo)*(int)attackCostMult);
    }
    
    public int getMoveCost(Tile _moveFrom, Tile _moveTo) {
        if(_moveTo == null) {
            return 0;
        }
        
        int cost = 0;

        for(int col=0; col<Board.GetNumColumns(); col++) {
            if((col > _moveFrom.getCol() && col <= _moveTo.getCol()) || (col < _moveFrom.getCol() && col >= _moveTo.getCol())) {
                if(terrainCost.get(Board.GetTileOf(_moveFrom.getRow(), col).getType()) != null) {
                    cost += 10*terrainCost.get(Board.GetTileOf(_moveFrom.getRow(), col).getType());
                } else {
                    return(500);
                }
            }
        }
        
        for(int row=0; row<Board.GetNumRows(); row++) {
            if((row > _moveFrom.getRow() && row <= _moveTo.getRow()) || (row < _moveFrom.getRow() && row >= _moveTo.getRow())) {
                if(terrainCost.get(Board.GetTileOf(row, _moveFrom.getCol()).getType()) != null) {
                    cost += 10*terrainCost.get(Board.GetTileOf(row, _moveFrom.getCol()).getType());
                } else {
                    return(500);
                }
            }
        }

        return cost;
    }
    
    public static void ReplenishMovePoints() {
        for(Unit unit : units) {
            unit.move_pts = totalMovePoints;
        }
    }
}
