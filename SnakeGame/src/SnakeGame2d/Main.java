package SnakeGame2d;
import java.awt.Color;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args)
    {
        JFrame frame = new JFrame ("Snake Game");
        frame.setBounds(10,10,905,700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Gamepanel panel = new Gamepanel();
        panel.setBackground(Color.LIGHT_GRAY);
        frame.add(panel);
        
        frame.setVisible(true);
    }
}
