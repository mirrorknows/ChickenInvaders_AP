package ui;

import javax.swing.*;

//this class creates the game window
public class GameFrame extends JFrame {

    //create game window
    public GameFrame(){

        setTitle("Chicken Invaders");

        setSize(800,600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new GamePanel());

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
