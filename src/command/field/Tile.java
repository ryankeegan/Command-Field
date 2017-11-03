package command.field;

import java.awt.Color;
import java.awt.Graphics2D;

public class Tile {
    private enum TerrainType {
        WATER,SAND,GRASS,FOREST,ROCK,BARE_ROCK
    }
    private int xPos; //Far Left
    private int yPos; //Top
    private int xPos2;//Far Right
    private int yPos2;//Bottom
    private TerrainType terrain;
    Tile(int _xPos, int _yPos, int _terrain, int _xDelta, int _yDelta) {
        xPos = _xPos;
        yPos = _yPos;
        xPos2 = xPos + _xDelta;
        yPos2 = yPos + _yDelta;
        //
        terrain = TerrainType.WATER;
        //
    }
    
    public void draw(Graphics2D g) {
        g.setColor(Color.cyan);
        g.fillRect(xPos, yPos, xPos2, yPos2);
    }
}
