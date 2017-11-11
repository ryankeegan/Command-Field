package command.field.units;

import command.field.Board;
import command.field.Player;
import command.field.Tile;

public class UnitGeneral extends Unit {
    UnitGeneral(Player _owner) {
        super(_owner);
        unitCost = 20;
        hp = 75;
        damage = 40;
        unitType = Unit.UnitType.GENERAL;
        
        allowed.add(Tile.TerrainType.GRASS);
        allowed.add(Tile.TerrainType.FOREST);
        allowed.add(Tile.TerrainType.SAND);
        terrainCost.put(Tile.TerrainType.GRASS, 1.0);
        terrainCost.put(Tile.TerrainType.FOREST, 1.5);
        terrainCost.put(Tile.TerrainType.SAND, 1.8);
        
        unitIcon = "«";
    }
}