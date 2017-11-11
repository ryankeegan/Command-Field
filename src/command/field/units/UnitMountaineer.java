package command.field.units;

import command.field.Player;
import command.field.Tile;

public class UnitMountaineer extends Unit {
    UnitMountaineer(Player _owner) {
        super(_owner);
        unitCost = 10;
        hp = 100;
        damage = 20;
        unitType = UnitType.MOUNTAINEER;
        
        allowed.add(Tile.TerrainType.GRASS);
        allowed.add(Tile.TerrainType.FOREST);
        allowed.add(Tile.TerrainType.SAND);
        allowed.add(Tile.TerrainType.ROCK);
        allowed.add(Tile.TerrainType.BARE_ROCK);
        terrainCost.put(Tile.TerrainType.GRASS, 1.0);
        terrainCost.put(Tile.TerrainType.FOREST, 1.8);
        terrainCost.put(Tile.TerrainType.SAND, 2.4);
        terrainCost.put(Tile.TerrainType.ROCK, 1.0);
        terrainCost.put(Tile.TerrainType.BARE_ROCK, 1.6);
        
        unitIcon = "*";
    }
}