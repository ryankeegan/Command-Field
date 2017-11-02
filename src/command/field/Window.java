package command.field;

public class Window {
    private static final int XBORDER = 20;
    private static final int YBORDER = 20;
    private static final int TOP_BORDER = 20;
    private static final int BOTTOM_BORDER = 20;
    private static final int YTITLE = 30;
    private static final int WINDOW_BORDER = 8;
    private static final int ACTUAL_WINDO_WIDTH=1280;
    private static final int ACTUAL_WINDO_HEIGHT=720;
    
    static final int WINDOW_WIDTH = 2*(WINDOW_BORDER + XBORDER) + ACTUAL_WINDO_WIDTH;
    static final int WINDOW_HEIGHT = YTITLE + WINDOW_BORDER + ACTUAL_WINDO_HEIGHT;
    static int xsize = -1;
    static int ysize = -1;

    public static int getX(int x) {
        return (x + XBORDER + WINDOW_BORDER);
    }

    public static int getY(int y) {
        return (y + TOP_BORDER + YTITLE );
        
    }
    public static int getActualWindowWidth() {
        return (ACTUAL_WINDO_WIDTH );
        
    }
        public static int getActualWindowHeight() {
        return (ACTUAL_WINDO_HEIGHT );
        
    }

    public static int getYNormal(int y) {
      return (-y + TOP_BORDER + YTITLE + getHeight2());
        
    }
    
    public static int getWidth2() {
        return (xsize - 2 * (XBORDER + WINDOW_BORDER));
    }

    public static int getHeight2() {
        return (ysize - (BOTTOM_BORDER + TOP_BORDER) - WINDOW_BORDER - YTITLE);
    }    
    
    public static int getWindowBorder() {
        return WINDOW_BORDER;
    }
}
