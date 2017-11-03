package command.field;

import java.awt.*;

public class Board {
    private final static int BoardWidth = Window.getActualWindowWidth()-Window.getActualWindowWidth()/4;
    private final static int BoardHeight = Window.getActualWindowHeight()-Window.getActualWindowHeight()/22;
    private final static int NUM_ROWS = 36;
    private final static int NUM_COLUMNS = 48;
    private static Tile board[][] = new Tile[NUM_ROWS][NUM_COLUMNS];
    private final static int ydelta = Window.getHeight2()/NUM_ROWS;
    private final static int xdelta = BoardWidth/NUM_COLUMNS;

    public static void Reset() {
        for (int zi = 0; zi<NUM_ROWS; zi++) {
            for (int zx = 0; zx<NUM_COLUMNS; zx++) {
                board[zi][zx] = null;
            }
        }
    }

//    public static void AddPiecePixel(int xpixel,int ypixel) {
//        
//        int ydelta = Window.getHeight2()/NUM_ROWS;
//        int xdelta = Window.getWidth2()/NUM_COLUMNS;
//
//        int zcol = 0;
//        int zrow = 0;
//        
//        if (xpixel-Window.getX(0) > 0 &&
//            ypixel-Window.getY(0) > 0 &&
//            xpixel-Window.getX(0) < xdelta*NUM_COLUMNS &&
//            ypixel-Window.getY(0) < ydelta*NUM_ROWS)
//        {
//            zcol = (xpixel-Window.getX(0))/xdelta;
//            zrow = (ypixel-Window.getY(0))/ydelta;
//           if( board[zrow][zcol] != null){
//               if(Player.getCurrentTurn().getColor()!=board[zrow][zcol].getColor()){
//               board[zrow][zcol]=null;
//               Player.changeTurn();
////              int therow=zrow;
//               for(int i=zrow;i>-1;i--){
//                   if( board[i][zcol]!=null){
//                      for(int z=zrow;z>-1;z--){
//                          if( board[z][zcol]==null){
//                              board[z][zcol]=board[i][zcol];
//                              board[i][zcol]=null;
//                              break;
//                          }
//                      }
//                  }
//        //          therow--;
//               }
//               return;}
//           }
//           
//            int i= NUM_ROWS-1;
//            for (;i>-1 && board[i][zcol] != null;i--);
//           
//            if (i >= 0) {
//           
//                board[i][zcol] = new Piece(Player.getCurrentTurn().getColor());
//               
//                System.out.println(Player.getCurrentTurn().getColor());
//                Player.changeTurn();
//                System.out.println(Player.getCurrentTurn().getColor());
//            }
//        }}        
        
    

    public static void Draw(Graphics2D g) {
        //Draw grid
        g.setColor(Color.red);
        for (int zi = 0;zi<NUM_ROWS;zi++) {
            g.drawLine(Window.getX(0), Window.getY(zi*ydelta)+(Window.getActualWindowHeight()-BoardHeight), Window.getX(BoardWidth), Window.getY(zi*ydelta)+(Window.getActualWindowHeight()-BoardHeight));
        }
        
        for (int zi = 1;zi<NUM_COLUMNS+1 && zi*xdelta<=BoardWidth;zi++) {
            g.drawLine(Window.getX(zi*xdelta), Window.getY((Window.getActualWindowHeight()-BoardHeight)), Window.getX(zi*xdelta), Window.getY(Window.getHeight2()));
            
        }
        
        for (int i=0; i<NUM_COLUMNS; i++) {
            g.setColor(Color.black);
            g.setFont(new Font("Arial", Font.PLAIN, 5));
            g.drawString(""+i, Window.getX(i*xdelta)+xdelta/2, Window.getY((Window.getActualWindowHeight()-BoardHeight))+ydelta/2);
        }
        
        for (int i=0; i<NUM_ROWS; i++) {
            g.setColor(Color.black);
            g.setFont(new Font("Arial", Font.PLAIN, 5));
            g.drawString(""+i, Window.getX(0)+xdelta/2,Window.getY(i*ydelta)+ydelta/2+(Window.getActualWindowHeight()-BoardHeight));
        }
        
        for (int row=0; row<NUM_ROWS; row++) {
            for (int col=0; col<NUM_COLUMNS; col++) {
                if(board[row][col] != null) {
                    board[3][3].draw(g);
                }
            }
        }
    }
    
    public static void BoardInit() {
        for (int row=0; row<NUM_ROWS; row++) {
            for (int col=0; col<NUM_COLUMNS && col*xdelta<=BoardWidth; col++) {
                board[row][col] = new Tile(Window.getX(col*xdelta), Window.getY(row*ydelta), 0, xdelta, ydelta);
            }
        }
        System.out.println(board.length);
    }
}