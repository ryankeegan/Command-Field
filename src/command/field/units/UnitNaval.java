package command.field.units;

import command.field.Board;
import command.field.Player;
import command.field.Tile;

public class UnitNaval extends Unit {
    int range;
    UnitNaval(Player _owner) {
        super(_owner);
        unitCost = 10;
        hp = 90;
        damage = 25;
        unitType = UnitType.NAVAL;
        range = 10;
        
        allowed.add(Tile.TerrainType.WATER);
        terrainCost.put(Tile.TerrainType.WATER, 0.7);
        
        unitIcon = "+";
    }
}