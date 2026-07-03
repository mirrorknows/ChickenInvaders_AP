package ui;

import services.LoggedUser;

import javax.swing.*;
import java.awt.*;

//main menu of the game
// including : new game, high scores,
// settings , how to play , exit

public class MainMenu extends JFrame {

    //panel
    private JPanel panel;

    //label
    private JLabel welcomeLabel;
    private JLabel titleLabel;

    //button
    private JButton newGameButton;
    private JButton highScoreButton;
    private JButton settingsButton;
    private JButton howToPlayButton;
    private JButton exitButton;

    public MainMenu(){
        setTitle("Main Menu");
        setSize(550,550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setBackground(new Color(20,20,20));
        add(panel);

        panel.setBorder(
                BorderFactory.createEmptyBorder(30,70,30,70)
        );

        GridLayout layout = new GridLayout();
        layout.setRows(7);
        layout.setColumns(1);
        layout.setHgap(10);
        layout.setVgap(15);

        panel.setLayout(layout);

        // Game title
        titleLabel = new JLabel("CHICKEN INVADERS");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 33));
        titleLabel.setForeground(new Color(255,210,60));

        panel.add(titleLabel);

        //welcome
        welcomeLabel = new JLabel("WELCOME, " +
                LoggedUser.getUser().getUsername() + "!");


        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Georgia", Font.PLAIN, 25));
        welcomeLabel.setForeground(Color.WHITE);

        panel.add(welcomeLabel);

        //buttons :
        //new game button
        newGameButton = new JButton("New Game");

        buttonStyle(newGameButton);

        newGameButton.setBorder(
                BorderFactory.createLineBorder(
                        new Color(207, 255, 4),
                        2
                )
        );

        panel.add(newGameButton);

        //new game action listener
        newGameButton.addActionListener(e-> {

            //will open game frame (coming soon)

        });

        //high scores
        highScoreButton = new JButton("High Scores");

        buttonStyle(highScoreButton);

        highScoreButton.setBorder(
                BorderFactory.createLineBorder(
                        new Color(207, 255, 4),
                        2
                )
        );

        panel.add(highScoreButton);

        //high scores action listener
        highScoreButton.addActionListener(e->{

            //will open high score frame (coming soon)

        });

        //settings
        settingsButton = new JButton("Settings");

        buttonStyle(settingsButton);

        settingsButton.setBorder(
                BorderFactory.createLineBorder(
                        new Color(207, 255, 4),
                        2
                )
        );

        panel.add(settingsButton);

        //setting action listener
        settingsButton.addActionListener(e->{

            new SettingsFrame();

            dispose();
        });

        //how to play : guide

        howToPlayButton = new JButton("How To Play");
        buttonStyle(howToPlayButton);

        howToPlayButton.setBorder(
                BorderFactory.createLineBorder(
                        new Color(207, 255, 4),
                        2
                )
        );

        panel.add(howToPlayButton);

        //how to play action listener
        howToPlayButton.addActionListener(e ->{
            new HowToPlayFrame();

            dispose();
        });

        // EXIT
        exitButton = new JButton("EXIT");
        buttonStyle(exitButton);

        exitButton.setBorder(
                BorderFactory.createLineBorder(
                        new Color(255, 0, 0),
                        2
                )
        );

        panel.add(exitButton);

        //exit action listener
        exitButton.addActionListener(e->{

            JOptionPane.showMessageDialog(this,
                    "Thanks for playing! See you next Mission Pilot!",
                    "Goodbye",
                    JOptionPane.INFORMATION_MESSAGE
            );
            System.exit(0);
        });
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //the style of buttons: including background color, font,...

    private void buttonStyle(JButton button){

        button.setFont(new Font("Trebuchet MS", Font.BOLD, 18));

        button.setForeground(Color.WHITE);
        button.setBackground(new Color(40, 40, 40));

        button.setFocusPainted(false);

    }
}
