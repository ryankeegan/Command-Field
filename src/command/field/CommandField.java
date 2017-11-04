package command.field;

import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class CommandField extends JFrame implements Runnable {
    static boolean animateFirstTime = true;
    static boolean inGame = false;
    static boolean started = false;
    static Graphics2D g;
    Thread relaxer;
    Image image;
    static Timer t = new Timer();
    
    CommandField() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.BUTTON1 == e.getButton() ) {
                    try {
                        Button.CheckCollision(e.getX(),e.getY());
                        if(inGame) {
                            Board.CheckCollision(e.getX(), e.getY());
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(CommandField.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.VK_INSERT == e.getKeyCode()) {
                    Board.BoardInit();
                } else if(e.VK_ESCAPE == e.getKeyCode()) {
                    if(started) {
                        inGame = false;
                        Menu.SetMenuType(Menu.MenuType.MAIN);
                    }
                }
                repaint();
            }
        });

        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Board.BoardInit();
            }
        }, 0, 1500);
        
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
        
        //Fill border
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, Window.xsize, Window.ysize);

        int x[] = {Window.getX(0), Window.getX(Window.getWidth2()), Window.getX(Window.getWidth2()), Window.getX(0), Window.getX(0)};
        int y[] = {Window.getY(0), Window.getY(0), Window.getY(Window.getHeight2()), Window.getY(Window.getHeight2()), Window.getY(0)};
        
        //Fill background
        if(!inGame) {
            g.setColor(Color.white);
            g.fillPolygon(x, y, 4);
            Board.Draw(g);
            g.setColor(new Color(255, 255, 255, 200));
            g.fillPolygon(x, y, 4);
        } else {
            t.cancel();
            g.setColor(Color.white);
            g.fillPolygon(x, y, 4);
            Board.Draw(g);
        }
        
        Menu.Draw(g);
        
        gOld.drawImage(image, 0, 0, null);
    }
    
    public void animate() {
        if (animateFirstTime) {
            animateFirstTime = false;
            if (Window.xsize != getSize().width || Window.ysize != getSize().height) {
                Window.xsize = getSize().width;
                Window.ysize = getSize().height;
            }
            
            Board.BoardInit();
            reset();
        }
    }
    
    public static void reset() {
        Player.PlayersInit();
        Menu.SetMenuType(Menu.MenuType.MAIN);
        Board.BoardInit();
        inGame = false;
        started = false;
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
