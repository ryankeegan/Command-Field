package command.field;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CommandField extends JFrame implements Runnable {
    static boolean animateFirstTime = true;
    static Graphics2D g;
    Thread relaxer;
    Image image;
    
    CommandField() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.BUTTON1 == e.getButton() ) {
                    //PerlinNoise.CheckBorderPixels(e.getX(),e.getY());
                }
            }
        });
        init();
        start();
    }
    
    public static void main(String[] args) {
        CommandField frame = new CommandField();
        frame.setSize(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }
    
    public void paint(Graphics gOld) {
        if (image == null || Window.xsize != getSize().width || Window.ysize != getSize().height) {
            Window.xsize = getSize().width;
            Window.ysize = getSize().height;
            image = createImage(Window.xsize, Window.ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        //fill background
        
        g.setColor(Color.cyan);
        g.fillRect(0, 0, Window.xsize, Window.ysize);

        int x[] = {Window.getX(0), Window.getX(Window.getWidth2()), Window.getX(Window.getWidth2()), Window.getX(0), Window.getX(0)};
        int y[] = {Window.getY(0), Window.getY(0), Window.getY(Window.getHeight2()), Window.getY(Window.getHeight2()), Window.getY(0)};
//fill border
        g.setColor(Color.white);
        g.fillPolygon(x, y, 4);
// draw border
        g.setColor(Color.red);
        g.drawPolyline(x, y, 5);
        Menu.Draw();
        Board.Draw(g);
        
        gOld.drawImage(image, 0, 0, null);
    }
    
    public void animate() {
        if (animateFirstTime) {
            animateFirstTime = false;
            if (Window.xsize != getSize().width || Window.ysize != getSize().height) {
                Window.xsize = getSize().width;
                Window.ysize = getSize().height;
            }
            
            reset();
        }
    }
    
    public void reset() {
        
    }
    
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = .1;
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
    
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
    
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }
    
    public void init() {
        requestFocus();
    }
    
    public void destroy() {
    }
}
