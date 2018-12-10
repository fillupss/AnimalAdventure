package main.gui;

import main.GameWorld;
import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {
    public Window(int width, int height, String title, GameWorld game){
        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
    }
}
