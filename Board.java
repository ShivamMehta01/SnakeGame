package SnakeGame;

import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    public void actionPerformed(ActionEvent ae)
    {
        if(inGame)
        {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g)
    {
        if(inGame)
        {
            g.drawImage(apple,appleX,appleY,this);
            g.drawImage(head,X[0],Y[0],this);
            for(int z=1;z<dots;z++)
            {
                g.drawImage(dot,X[z],Y[z],this);
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else
            gameOver(g);
    }
    private Image apple;
    private Image dot;
    private Image head;
    private int dots;
    private final int DOT_SIZE=10;
    private final int ALL_DOTS=900;
    private final int[] X=new int[ALL_DOTS];
    private final int[] Y=new int[ALL_DOTS];
    private final int RANDOM_POSITION=30-1;
    private int appleX;
    private int appleY;
    private Timer timer;
    private boolean leftDirection=false;
    private boolean rightDirection=true;
    private boolean upDirection=false;
    private boolean downDirection=false;
    private boolean inGame=true;
    private class TAdaptar extends KeyAdapter{
        public void keyPressed(KeyEvent e)
        {
            int key=e.getKeyCode();
            if(key==KeyEvent.VK_LEFT&&!rightDirection)
            {
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_RIGHT&&!leftDirection)
            {
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_UP&&!downDirection)
            {
                leftDirection=false;
                upDirection=true;
                rightDirection=false;
            }
            if(key==KeyEvent.VK_DOWN&&!upDirection)
            {
                leftDirection=false;
                rightDirection=false;
                downDirection=true;
            }
        }
    }
    Board()
    {
        setPreferredSize(new Dimension(300,300));
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new TAdaptar());
        setFocusable(true);
    }
    public void loadImages()
    {
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("SnakeGame/Icons/apple.png"));
        ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("SnakeGame/Icons/dot.png"));
        ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("SnakeGame/Icons/head.png"));
        apple=i1.getImage();
        dot=i2.getImage();
        head=i3.getImage();
    }
    public void initGame()
    {
        dots=3;
        for(int z=0;z<dots;z++)
        {
            X[z]=50-DOT_SIZE*z;
            Y[z]=50;
        }
        locateApple();
        timer=new Timer(150-2*dots,this);
        timer.start();
    }
    public void locateApple()
    {
        int r=(int)(Math.random()*RANDOM_POSITION);
        appleX=r*DOT_SIZE;
        r=(int)(Math.random()*RANDOM_POSITION);
        appleY=r*DOT_SIZE;
    }
    public void checkApple()
    {
        if(X[0]==appleX&&Y[0]==appleY)
        {
            dots++;
            locateApple();
        }
    }
    public void checkCollision()
    {
        if(X[0]>=300||Y[0]>=300||X[0]<0||Y[0]<0)
            inGame=false;
        for(int z=dots;z>0;z--)
        {
            if(z>4)
            {
                if(X[0]==X[z]&&Y[0]==Y[z])
                    inGame=false;
            }
        }
        if(!inGame)
            timer.stop();
    }
    public void move()
    {
        for(int z=dots;z>0;z--)
        {
            X[z]=X[z-1];
            Y[z]=Y[z-1];
        }
        if(leftDirection)
        {
            X[0]-=DOT_SIZE;
        }
        if(rightDirection)
        {
            X[0]+=DOT_SIZE;
        }
        if(upDirection)
        {
            Y[0]-=DOT_SIZE;
        }
        if(downDirection)
        {
            Y[0]+=DOT_SIZE;
        }
    }
    public void gameOver(Graphics g)
    {
        String msg="Score "+String.valueOf(dots-3);
        Font font=new Font("SAN_SERIF",Font.BOLD,14);
        FontMetrics metrics=getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg,(300-metrics.stringWidth(msg))/2,300/2);
    }
}
