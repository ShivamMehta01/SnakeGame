package SnakeGame;
import javax.swing.*;
public class Snake extends JFrame{
    Snake()
    {
        Board b=new Board();
        add(b);
        pack();
        setLocationRelativeTo(null);
        setTitle("SnakeGame");
        setResizable(false);

    }
    public static void main(String[] args)
    {
        new Snake().setVisible(true);
    }
}
