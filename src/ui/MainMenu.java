package ui;

import services.LoggedUser;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

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
        setSize(800,600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load background image from resources
        URL backgroundUrl = getClass().getResource(
                        "/images/backgrounds/login_background.png");

        if(backgroundUrl == null) {
            throw new IllegalStateException("background image not found");
        }
        ImageIcon backgroundIcon = new ImageIcon(backgroundUrl);
        Image backgroundImage = backgroundIcon.getImage();

        // Draw the image as the panel background
        panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(
                        backgroundImage,
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        this
                );
            }
        };

        add(panel);

        panel.setLayout(
                new BoxLayout(panel, BoxLayout.Y_AXIS)
        );

        panel.add(Box.createVerticalStrut(70));

        // Game title
        titleLabel = new JLabel("CHICKEN INVADERS");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 40));
        titleLabel.setForeground(new Color(255,210,60));

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));

        //welcome
        welcomeLabel = new JLabel("WELCOME, " +
                LoggedUser.getUser().getUsername() + "!");



        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("Georgia", Font.PLAIN, 25));
        welcomeLabel.setForeground(Color.WHITE);

        panel.add(welcomeLabel);
        panel.add(Box.createVerticalStrut(30));

        //buttons :
        //new game button
        newGameButton = new JButton("New Game");

        buttonStyle(newGameButton);

        panel.add(newGameButton);
        panel.add(Box.createVerticalStrut(12));

        //new game action listener
        newGameButton.addActionListener(e-> {

            new GameFrame();
            dispose();

        });

        //high scores
        highScoreButton = new JButton("High Scores");

        buttonStyle(highScoreButton);


        panel.add(highScoreButton);
        panel.add(Box.createVerticalStrut(12));

        //high scores action listener
        highScoreButton.addActionListener(e->{

            new HighScoreFrame();
            dispose();

        });

        //settings
        settingsButton = new JButton("Settings");

        buttonStyle(settingsButton);


        panel.add(settingsButton);
        panel.add(Box.createVerticalStrut(12));

        //setting action listener
        settingsButton.addActionListener(e->{

            new SettingsFrame();

            dispose();
        });

        //how to play : guide

        howToPlayButton = new JButton("How To Play");
        buttonStyle(howToPlayButton);

        panel.add(howToPlayButton);
        panel.add(Box.createVerticalStrut(12));

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
        panel.add(Box.createVerticalGlue());

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

        button.setPreferredSize(new Dimension(240, 45));
        button.setMaximumSize(new Dimension(240, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setFocusPainted(false);

        button.setBorder(BorderFactory.createLineBorder(
                        new Color(207, 255, 4),
                        2
                )
        );

    }
}
