package ui;

import javax.swing.*;

//this class creates the game window
public class GameFrame extends JFrame {

    //create game window
    public GameFrame(String username){

        setTitle("Chicken Invaders");

        setSize(800, 600);

        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new GamePanel(username));

        setLocationRelativeTo(null);

        setVisible(true);
    }
}
