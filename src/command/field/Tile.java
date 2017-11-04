package command.field;

import java.awt.Color;
import java.awt.Graphics2D;

public class Tile {
    private enum TerrainType {
        WATER,SAND,GRASS,FOREST,ROCK,BARE_ROCK
    }
    private int xPos; //Left
    private int yPos; //Top
    private int xPos2;//Right
    private int yPos2;//Bottom
    private TerrainType terrain;
    Tile(int _xPos, int _yPos, int _terrain) {
        xPos = _xPos;
        yPos = _yPos;
        xPos2 = xPos + Board.xdelta;
        yPos2 = yPos + Board.ydelta;
        terrain = ResolveTerrain(_terrain);
    }
    
    public static TerrainType ResolveTerrain(int _height) {
        if(_height < 100) {
            return TerrainType.WATER;
        } else if(_height >= 100 && _height < 115) {
            return TerrainType.SAND;
        } else if((_height >= 115 && _height < 150)) {
            return TerrainType.GRASS;
        } else if(_height >= 150 && _height < 200) {
            return TerrainType.FOREST;
        } else if(_height >= 200 && _height < 205) {
            return TerrainType.ROCK;
        } else if(_height >= 205) {
            return TerrainType.BARE_ROCK;
        }
        
        return TerrainType.WATER;
    }
    
    private static Color ResolveTerrainColor(TerrainType _terrain) {
        switch(_terrain){
            case WATER:
                return(Color.blue);
            case SAND:
                return(new Color(242,225,49));
            case GRASS:
                return(Color.green);
            case FOREST:
                return(new Color(73,173,50));
            case ROCK:
                return(new Color(139,69,19));
            case BARE_ROCK:
                return(Color.gray);
        }
        
        return(Color.blue);
    }
    
    public void draw(Graphics2D g) {
        g.setColor(ResolveTerrainColor(terrain));
        g.fillRect(xPos, yPos, Board.xdelta, Board.ydelta);
    }
    
    public int getRow() {
        return((yPos-Window.getY(0))/Board.ydelta)-2;
    }
    
    public int getCol() {
        return((xPos-Window.getX(0))/Board.xdelta);
    }
    
    public TerrainType getType() {
        return(terrain);
    }
}
