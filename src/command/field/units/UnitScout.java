package command.field.units;

import command.field.Player;
import command.field.Tile;

public class UnitScout extends Unit {
    UnitScout(Player _owner) {
        super(_owner);
        unitCost = 5;
        hp = 50;
        damage = 15;
        unitType = UnitType.SCOUT;
        
        allowed.add(Tile.TerrainType.GRASS);
        allowed.add(Tile.TerrainType.FOREST);
        allowed.add(Tile.TerrainType.SAND);
        allowed.add(Tile.TerrainType.ROCK);
        terrainCost.put(Tile.TerrainType.GRASS, 0.3);
        terrainCost.put(Tile.TerrainType.FOREST, 0.7);
        terrainCost.put(Tile.TerrainType.SAND, 1.0);
        terrainCost.put(Tile.TerrainType.ROCK, 1.8);
        
        unitIcon = "‹";
    }
}