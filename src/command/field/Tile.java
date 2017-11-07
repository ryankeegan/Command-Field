package command.field;

import command.field.units.Unit;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class Tile {
    private static enum TerrainType {
        WATER,SAND,GRASS,FOREST,ROCK,BARE_ROCK
    }
    private int xPos;
    private int yPos;
    private TerrainType terrain;
    Tile(int _xPos, int _yPos, int _terrain) {
        xPos = _xPos;
        yPos = _yPos;
        terrain = ResolveTerrain(_terrain);
    }
    private Unit unit;
    private boolean shaded = false;
    private Color shadedColour;
    
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
        if(shaded) {
            g.setColor(shadedColour);
            g.fillRect(xPos, yPos, Board.xdelta, Board.ydelta);
        }
        
        if(unit != null) {
            g.setColor(unit.getOwner().getPlayerColor());
            drawCenteredString(g, unit.getIcon(), new Rectangle(xPos, yPos, Board.xdelta, Board.ydelta), new Font("Arial", Font.PLAIN, (Board.xdelta-5)));
        }
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
    
    public Unit getUnit() {
        return(unit);
    }
    
    public void addUnit(Unit _unit) {
        if(unit == null) {
            unit = _unit;
        } else {
            System.out.println("Unit already on tile");
        }
    }
    
    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        if(text == "<" | text == "«") {
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.rotate(Math.toRadians(90), 0, 0);
            Font rotatedFont = font.deriveFont(affineTransform);
            g.setFont(rotatedFont);
            g.drawString(text, x, y-Board.ydelta/2);
        } else if(text == "*") {
            g.setFont(font);
            g.drawString(text, x, y+Board.ydelta/4);
        } else {
            g.setFont(font);
            g.drawString(text, x, y);
        }
    }
    
    public void setShaded(boolean _shaded, Color _shadedColour) {
        shaded = _shaded;
        shadedColour = _shadedColour;
    }
    
    public void setShaded(boolean _shaded) {
        shaded = _shaded;
    }
}
